package com.example.SpringQualityChallenge.repositories;

import com.example.SpringQualityChallenge.dtos.HotelDTO;
import com.example.SpringQualityChallenge.exceptions.HotelDTONotFoundException;
import com.example.SpringQualityChallenge.exceptions.NotExistedDestinyException;
import com.example.SpringQualityChallenge.exceptions.WrongDateFormatException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Repository
public class HotelDTORepositoryImpl implements HotelDTORepository {

    private List<HotelDTO> hotelDTOS;

    public HotelDTORepositoryImpl(){
        this.hotelDTOS=loadDataBase();
    }

    /**
     * Filtra el listado de hoteles según un rango de fechas de entrada y salida
     * @param hotelDTOS
     * @param params
     * @return
     * @throws IOException
     * @throws HotelDTONotFoundException
     * @throws WrongDateFormatException
     */
    @Override
    public List<HotelDTO> findHotelByDates(List<HotelDTO> hotelDTOS, Map<String, String> params) throws IOException, HotelDTONotFoundException, WrongDateFormatException {
        List<HotelDTO> hotelDTOList=new ArrayList<>();
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


        if (hotelDTOS != null){
            hotelDTOList = hotelDTOS.stream()
                    .filter(hotelDTO -> hotelDTO.getDateFrom().compareTo(dateFrom)<=0
                            && hotelDTO.getDateTo().compareTo(dateTo)>=0 && hotelDTO.getReserved().equalsIgnoreCase("NO"))
                    .collect(Collectors.toList());
        }
        if(!hotelDTOList.isEmpty()) {
            return hotelDTOList;
        }else throw new HotelDTONotFoundException(params.get("dateFrom") + " and " + params.get("dateTo"));
    }

    /**
     * Filtra hoteles según un código de hotel específico
     * @param code
     * @return
     */
    @Override
    public List<HotelDTO> findHotelByCode(String code) {
        List<HotelDTO> hotelDTOList=new ArrayList<>();

            hotelDTOList=this.hotelDTOS;

        if (hotelDTOList != null){
            hotelDTOList = hotelDTOList.stream()
                    .filter(hotelDTO -> hotelDTO.getCodeHotel().equalsIgnoreCase(code) && hotelDTO.getReserved().equalsIgnoreCase("NO"))
                    .collect(Collectors.toList());
        }

        return hotelDTOList;
    }

    /**
     * Filtra hoteles según un rango de fechas de entrada y salida con un destino específico
     * @param hotelDTOS
     * @param params
     * @return
     * @throws IOException
     * @throws HotelDTONotFoundException
     * @throws NotExistedDestinyException
     * @throws WrongDateFormatException
     */
    @Override
    public List<HotelDTO> findHotelByDatesAndDestination(List<HotelDTO> hotelDTOS, Map<String,String> params) throws IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        List<HotelDTO> hotelDTOList=findHotelByDates(hotelDTOS,params);
        boolean matchCity=false;
        for(HotelDTO hotelDTO: hotelDTOS){
            if(hotelDTO.getCity().equalsIgnoreCase(params.get("destination"))){
                matchCity=true;
            }
        }
        if(!matchCity){
            throw new NotExistedDestinyException(params.get("destination"));
        }

        String keys[]=new String[params.size()];
        int i=0;
        for(String param: params.keySet()){
            keys[i]=param;
            i++;
        }
        if (hotelDTOList != null){
            hotelDTOList = hotelDTOList.stream()
                        .filter(hotelDTO -> hotelDTO.getCity().equalsIgnoreCase(params.get("destination")))
                        .collect(Collectors.toList());
        }

        return hotelDTOList;
    }

    /**
     * Carga la base de datos.
     * @return
     */
    public List<HotelDTO> loadDataBase() {
        FileReader fileReader = null;
        List<HotelDTO> articles = new ArrayList<>();

        try {
            fileReader = new FileReader("src/main/resources/dbHotels.csv");

            BufferedReader csvReader = new BufferedReader(fileReader);

            String row;
            boolean firstTime = true;
            int idCounter=1;
            while ((row = csvReader.readLine()) != null) {
                if (firstTime) {
                    firstTime = false;
                } else {
                    String[] data = row.split(",");
                    articles.add(objectMapper(data,idCounter));
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

        return articles;
    }

    private HotelDTO objectMapper(String[] data, int idCounter) {

        String codeHotel, name, city, roomType, nightPrice, reserved;
        LocalDate dateFrom, dateTo;

        codeHotel = data[0];
        name = data[1];
        city = data[2];
        roomType = data[3];
        nightPrice = data[4];
        reserved=data[7];



        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        dateFrom= LocalDate.parse(data[5], formatter);
        dateTo=LocalDate.parse(data[6], formatter);

        return new HotelDTO(codeHotel,name, city, roomType,
                Integer.parseInt(nightPrice.replaceAll("[^0-9]","")),
                dateFrom,dateTo,
                reserved);

    }
}
