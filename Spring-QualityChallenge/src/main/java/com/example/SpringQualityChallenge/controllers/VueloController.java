package com.example.SpringQualityChallenge.controllers;

import com.example.SpringQualityChallenge.dtos.FlightReservationDTO;
import com.example.SpringQualityChallenge.dtos.PeopleDTO;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.services.VueloServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class VueloController {

    private VueloServiceImpl vueloService;

    public VueloController(VueloServiceImpl vueloService ){
        this.vueloService = vueloService;
    }

    /**
     * Allows to get the whole flights list without arguments or get filter by dates, or by a combination of two dates
     * origin and destination.
     * Example: localhost:8081/api/v1/flights?dateFrom=15/02/2021&dateTo=28/02/2021&origin=bogotá&destination=Buenos Aires
     * @param params
     * @return
     * @throws ExceededArgumentLimitException
     * @throws VueloDTONotFoundException
     * @throws NotExistedDestinyException
     * @throws WrongDateFormatException
     */
    @GetMapping("/flights")
    public ResponseEntity listarVuelos(@RequestParam Map<String,String> params) throws ExceededArgumentLimitException, VueloDTONotFoundException, NotExistedDestinyException, WrongDateFormatException{
        return new ResponseEntity(vueloService.listarVuelos(params), HttpStatus.OK);
    }

    /**
     * Allows a POST with a FlightReservationDTO object with a userName and a flightReservation inside. More info in README.md
     * @param flightReservationDTO
     * @return
     * @throws WrongRangeDateException
     * @throws NotMatchingRoomAndPeopleAmountException
     * @throws WrongEmailFormatException
     * @throws PaymentMethodNotAllowDuesException
     * @throws VueloNotAvailableException
     */
    @PostMapping("/flight-reservation")
    public ResponseEntity realizarReservaVuelo(@RequestBody FlightReservationDTO flightReservationDTO) throws WrongRangeDateException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, VueloNotAvailableException {
        flightReservationDTO= validateData(flightReservationDTO);
        return new ResponseEntity(vueloService.procesarReserva(flightReservationDTO), HttpStatus.OK);
    }

    public FlightReservationDTO validateData(FlightReservationDTO flightReservationDTO) throws WrongRangeDateException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException {
        if(flightReservationDTO.getFlightReservation().getDateFrom()
                .compareTo(flightReservationDTO.getFlightReservation().getDateTo())>0){
            throw new WrongRangeDateException("DateFrom: "+flightReservationDTO.getFlightReservation().getDateFrom()
                    + " and dateTo: "+flightReservationDTO.getFlightReservation().getDateTo());
        }
        validateMail(flightReservationDTO);
        validatePaymentMethod(flightReservationDTO);
        return flightReservationDTO;
    }
    private void validateMail(FlightReservationDTO flightReservationDTO) throws WrongEmailFormatException {
        List<PeopleDTO> peopleDTOList = flightReservationDTO.getFlightReservation().getPeople();

        for(PeopleDTO peopleDTO: peopleDTOList){
            if(!peopleDTO.getMail().contains("@")|| !peopleDTO.getMail().contains(".com")){
                throw new WrongEmailFormatException(peopleDTO.getMail());
            }
        }
    }
    private void validatePaymentMethod(FlightReservationDTO flightReservationDTO) throws PaymentMethodNotAllowDuesException {
        if(!flightReservationDTO.getFlightReservation().getPaymentMethod().getType().equals("CREDIT")
                && flightReservationDTO.getFlightReservation().getPaymentMethod().getDues()>1){
            throw new PaymentMethodNotAllowDuesException(flightReservationDTO.
                    getFlightReservation().getPaymentMethod().getType());
        }
    }



}
