package com.example.SpringQualityChallenge.services;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.repositories.VueloDTORepositoryImpl;

import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class VueloServiceImpl implements VueloService {

    private VueloDTORepositoryImpl vueloDTORepository;

    public VueloServiceImpl(VueloDTORepositoryImpl vueloDTORepository) {
        this.vueloDTORepository = vueloDTORepository;
    }


    /**
     * Busca los distintos listados de vuelos en función de la cantidad de parámetros que lleguen del controller
     * @param params
     * @return
     * @throws VueloDTONotFoundException
     * @throws ExceededArgumentLimitException
     * @throws NotExistedDestinyException
     * @throws WrongDateFormatException
     */
    @Override
    public List<VueloDTO> listarVuelos(Map<String,String> params) throws VueloDTONotFoundException, ExceededArgumentLimitException, NotExistedDestinyException, WrongDateFormatException {
        List<VueloDTO> vueloDTOS=vueloDTORepository.loadDataBase();

        if(params.isEmpty())
            return vueloDTOS;
        else {
            switch (params.size()) {
                case 2: vueloDTOS = vueloDTORepository.findFlightByDates(vueloDTOS, params);
                    break;
                case 4: vueloDTOS = vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOS, params);
                    break;
                default:
                    throw new ExceededArgumentLimitException("too many arguments. Max: 4 -> (2 dates, 1 origin, 1 destination)");
            }
        }
        return vueloDTOS;
    }

    /**
     * Procesa la reserva de vuelo siempre que el vuelo exista
     * @param flightReservationDTO
     * @return
     * @throws VueloNotAvailableException
     */
    @Override
    public FlightReservationResponseDTO procesarReserva(FlightReservationDTO flightReservationDTO) throws VueloNotAvailableException {
        FlightReservationResponseDTO flightReservationResponseDTO= new FlightReservationResponseDTO();

        flightReservationResponseDTO.setUserName(flightReservationDTO.getUserName());
        int cantidadDias = flightReservationDTO.getFlightReservation().getDateTo().getDayOfMonth()
                -flightReservationDTO.getFlightReservation().getDateFrom().getDayOfMonth();
        List<VueloDTO> vueloDTOS= vueloDTORepository.findFlightByCodeAndDates(flightReservationDTO);
        if(vueloDTOS.isEmpty()){
            throw new VueloNotAvailableException(flightReservationDTO.getFlightReservation().getFlightNumber());
        }

        VueloDTO vueloDTO = vueloDTOS.get(0);
        double amount = vueloDTO.getPrice()*cantidadDias*flightReservationDTO.getFlightReservation().getSeats();
        flightReservationResponseDTO.setAmount(amount);

        FlightReservationResponse flightReservationResponse= new FlightReservationResponse();

        if(flightReservationDTO.getFlightReservation().getPaymentMethod().getType().equals("CREDIT")){
            switch (flightReservationDTO.getFlightReservation().getPaymentMethod().getDues()){
                case 1:
                case 2:
                case 3:
                    flightReservationResponseDTO.setInterest(5);
                    flightReservationResponseDTO.setTotal(amount*1.05);
                    break;
                case 4:
                case 5:
                case 6:
                    flightReservationResponseDTO.setInterest(10);
                    flightReservationResponseDTO.setTotal(amount*1.1);
                    break;
            }

        }else{
            flightReservationResponseDTO.setTotal(amount);
        }

        flightReservationResponse = flightReservationResponseSetData(flightReservationDTO,flightReservationResponse);

        flightReservationResponseDTO.setFlightReservation(flightReservationResponse);
        flightReservationResponseDTO.setStatusCode(new StatusCodeDTO(200,"El proceso terminó satisfactoriamente"));

        return flightReservationResponseDTO;
    }

    /**
     * Setea los datos del flightReservationResponse si es que la reserva se hizo correctamente
     * @param flightReservationDTO
     * @param flightReservationResponse
     * @return
     */
    public FlightReservationResponse flightReservationResponseSetData(FlightReservationDTO flightReservationDTO, FlightReservationResponse flightReservationResponse){
        flightReservationResponse.setDateFrom(flightReservationDTO.getFlightReservation().getDateFrom());
        flightReservationResponse.setDateTo(flightReservationDTO.getFlightReservation().getDateTo());
        flightReservationResponse.setOrigin(flightReservationDTO.getFlightReservation().getOrigin());
        flightReservationResponse.setDestination(flightReservationDTO.getFlightReservation().getDestination());
        flightReservationResponse.setFlightNumber(flightReservationDTO.getFlightReservation().getFlightNumber());
        flightReservationResponse.setSeats(flightReservationDTO.getFlightReservation().getSeats());
        flightReservationResponse.setSeatType(flightReservationDTO.getFlightReservation().getSeatType());
        flightReservationResponse.setPeople(flightReservationDTO.getFlightReservation().getPeople());

        return flightReservationResponse;
    }
}
