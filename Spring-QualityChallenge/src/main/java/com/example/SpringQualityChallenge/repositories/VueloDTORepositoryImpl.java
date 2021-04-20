package com.example.SpringQualityChallenge.repositories;

import com.example.SpringQualityChallenge.dtos.FlightReservationDTO;
import com.example.SpringQualityChallenge.dtos.VueloDTO;
import com.example.SpringQualityChallenge.exceptions.NotExistedDestinyException;
import com.example.SpringQualityChallenge.exceptions.VueloDTONotFoundException;
import com.example.SpringQualityChallenge.exceptions.WrongDateFormatException;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class VueloDTORepositoryImpl implements VueloDTORepository {

    private List<VueloDTO> vueloDTOS;

    public VueloDTORepositoryImpl(){
        this.vueloDTOS=loadDataBase();
    }

    /**
     * Filtra el listado de vuelos según un rango de fechas de entrada y salida
     * @param vueloDTOS
     * @param params
     * @return
     * @throws WrongDateFormatException
     * @throws VueloDTONotFoundException
     */
    @Override
    public List<VueloDTO> findFlightByDates(List<VueloDTO> vueloDTOS, Map<String, String> params) throws WrongDateFormatException, VueloDTONotFoundException {
        List<VueloDTO> vueloDTOList=new ArrayList<>();
        String keys[]=new String[params.size()];
        int i=0;
        for(String param: params.keySet()){
            keys[i]=param;
            i++;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateFrom;
        LocalDate dateTo;
        try {
            dateFrom = LocalDate.parse(params.get("dateFrom"), formatter);
        }catch (Exception e){
            throw new WrongDateFormatException("DateFrom: "+params.get("dateFrom"));
        }
        try {
            dateTo = LocalDate.parse(params.get("dateTo"), formatter);
        }catch (Exception e){
            throw new WrongDateFormatException("DateTo: "+params.get("dateTo"));
        }


        if (vueloDTOS != null){
            vueloDTOList = vueloDTOS.stream()
                    .filter(vueloDTO -> vueloDTO.getDateFrom().compareTo(dateFrom)==0
                            && vueloDTO.getDateTo().compareTo(dateTo)==0)
                    .collect(Collectors.toList());
        }
        if(!vueloDTOList.isEmpty()) {
            return vueloDTOList;
        }else throw new VueloDTONotFoundException(params.get(keys[0]) + " and " + params.get(keys[1]));
    }

    /**
     * Filtra vuelos según un código de hotel y fechas específicas
     * @param flightReservationDTO
     * @return
     */
    @Override
    public List<VueloDTO> findFlightByCodeAndDates(FlightReservationDTO flightReservationDTO) {
        List<VueloDTO> vueloDTOList=new ArrayList<>();

        vueloDTOList=this.vueloDTOS;

        if (vueloDTOList != null){
            vueloDTOList = vueloDTOList.stream()
                    .filter(vueloDTO -> vueloDTO.getFlightNumber()
                            .equalsIgnoreCase(flightReservationDTO.getFlightReservation().getFlightNumber())
                            && vueloDTO.getDateFrom().compareTo(flightReservationDTO.getFlightReservation().getDateFrom())==0
                            && vueloDTO.getDateTo().compareTo(flightReservationDTO.getFlightReservation().getDateTo())==0)
                    .collect(Collectors.toList());
        }

        return vueloDTOList;
    }

    /**
     * Filtra vuelos según un rango de fechas de ida y vuelta con un origen y destino específicos
     * @param vueloDTOS
     * @param params
     * @return
     * @throws VueloDTONotFoundException
     * @throws WrongDateFormatException
     * @throws NotExistedDestinyException
     */
    @Override
    public List<VueloDTO> findFlightByDatesOriginAndDestination(List<VueloDTO> vueloDTOS, Map<String,String> params) throws VueloDTONotFoundException, WrongDateFormatException, NotExistedDestinyException {
        List<VueloDTO> vueloDTOList=findFlightByDates(vueloDTOS,params);
        boolean matchCity=false;
        for(VueloDTO vueloDTO: vueloDTOS){
            if(vueloDTO.getOrigin().equalsIgnoreCase(params.get("origin")) && vueloDTO.getDestination().equalsIgnoreCase(params.get("destination"))){
                matchCity=true;
            }
        }
        if(!matchCity){
            throw new NotExistedDestinyException(params.get("origin") + " " + params.get("destination"));
        }

        String keys[]=new String[params.size()];
        int i=0;
        for(String param: params.keySet()){
            keys[i]=param;
            i++;
        }
        if (vueloDTOList != null){
            vueloDTOList = vueloDTOList.stream()
                    .filter(vueloDTO -> vueloDTO.getOrigin().equalsIgnoreCase(params.get("origin"))
                            && vueloDTO.getDestination().equalsIgnoreCase(params.get("destination")))
                    .collect(Collectors.toList());

        }

        return vueloDTOList;
    }

    /**
     * Carga la base de datos.
     * @return
     */
    public List<VueloDTO> loadDataBase() {
        FileReader fileReader = null;
        List<VueloDTO> flights = new ArrayList<>();

        try {
            fileReader = new FileReader("src/main/resources/dbFlights.csv");

            BufferedReader csvReader = new BufferedReader(fileReader);

            String row;
            boolean firstTime = true;
            int idCounter=1;
            while ((row = csvReader.readLine()) != null) {
                if (firstTime) {
                    firstTime = false;
                } else {
                    String[] data = row.split(",");
                    flights.add(objectMapper(data,idCounter));
                    idCounter++;
                }
            }
            csvReader.close();

        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            //throw FileNotFound
        } catch (IOException e) {
            e.printStackTrace();
        }

        return flights;
    }

    private VueloDTO objectMapper(String[] data, int idCounter) {

        String flightNumber, origin, destination, seatType, price;
        LocalDate dateFrom, dateTo;

        flightNumber = data[0];
        origin = data[1];
        destination = data[2];
        seatType = data[3];
        price = data[4];



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateFrom= LocalDate.parse(data[5], formatter);
        dateTo=LocalDate.parse(data[6], formatter);

        return new VueloDTO(flightNumber,origin, destination, seatType,
                Integer.parseInt(price.replaceAll("[^0-9]","")),
                dateFrom,dateTo);

    }
}
