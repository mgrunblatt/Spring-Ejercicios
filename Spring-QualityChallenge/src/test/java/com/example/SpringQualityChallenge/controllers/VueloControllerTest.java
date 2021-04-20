package com.example.SpringQualityChallenge.controllers;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.services.HotelServiceImpl;
import com.example.SpringQualityChallenge.services.VueloServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class VueloControllerTest {

    @Mock
    private VueloServiceImpl service;

    private VueloController vueloController;

    private List<VueloDTO> vueloDTOList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        vueloController = new VueloController(service);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        VueloDTO vueloDTO = new VueloDTO("BAPI-1235","Buenos Aires","Puerto Iguazú","Economy",6500, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter));
        vueloDTOList.add(vueloDTO);
    }

    @Test
    void listarVuelos() throws NotExistedDestinyException, WrongDateFormatException, VueloDTONotFoundException, ExceededArgumentLimitException {
        when(service.listarVuelos(any())).thenReturn(vueloDTOList);

        List<HotelDTO> listaAux = (List<HotelDTO>) vueloController.listarVuelos(new HashMap<>()).getBody();
        Assertions.assertEquals(vueloDTOList,listaAux);
    }

    @Test
    void realizarReservaVuelo() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@gmail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);
        FlightReservationResponse flightReservationResponse = new FlightReservationResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = new FlightReservationResponseDTO("user",500.0,10,550,flightReservationResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(flightReservationDTO)).thenReturn(flightReservationResponseDTO);

        ResponseEntity responseEntity = vueloController.realizarReservaVuelo(flightReservationDTO);

        Assertions.assertEquals(vueloDTOList.get(0).getFlightNumber(),flightReservationResponseDTO.getFlightReservation().getFlightNumber());
    }

    @Test
    void realizarReservaVueloWrongRangeDateException() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@gmail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("09/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);
        FlightReservationResponse flightReservationResponse = new FlightReservationResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = new FlightReservationResponseDTO("user",500.0,10,550,flightReservationResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(flightReservationDTO)).thenReturn(flightReservationResponseDTO);

        Assertions.assertThrows(WrongRangeDateException.class,()->{
            vueloController.realizarReservaVuelo(flightReservationDTO);
        });
    }

    @Test
    void realizarReservaVueloWrongPeopleEmailException() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);
        FlightReservationResponse flightReservationResponse = new FlightReservationResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = new FlightReservationResponseDTO("user",500.0,10,550,flightReservationResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(flightReservationDTO)).thenReturn(flightReservationResponseDTO);

        Assertions.assertThrows(WrongEmailFormatException.class,()->{
            vueloController.realizarReservaVuelo(flightReservationDTO);
        });
    }

    @Test
    void realizarReservaVueloNotDuesAllowException() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("DEBIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@mail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);
        FlightReservationResponse flightReservationResponse = new FlightReservationResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Buenos Aires","Puerto Iguazú","BAPI-1235",2,null,peopleDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = new FlightReservationResponseDTO("user",500.0,10,550,flightReservationResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(flightReservationDTO)).thenReturn(flightReservationResponseDTO);

        Assertions.assertThrows(PaymentMethodNotAllowDuesException.class,()->{
            vueloController.realizarReservaVuelo(flightReservationDTO);
        });
    }
}