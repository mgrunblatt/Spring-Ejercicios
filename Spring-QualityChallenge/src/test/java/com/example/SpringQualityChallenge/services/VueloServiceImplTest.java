package com.example.SpringQualityChallenge.services;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.repositories.HotelDTORepositoryImpl;
import com.example.SpringQualityChallenge.repositories.VueloDTORepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class VueloServiceImplTest {

    @Mock
    private VueloDTORepositoryImpl vueloDTORepository;

    private VueloServiceImpl vueloService;

    private List<VueloDTO> vueloDTOList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        vueloService = new VueloServiceImpl(vueloDTORepository);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        VueloDTO vueloDTO = new VueloDTO("BAPI-1235","Buenos Aires","Puerto Iguazú","Economy",6500, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter));
        vueloDTOList.add(vueloDTO);
    }

    @Test
    void listarVuelosServiceTest() throws NotExistedDestinyException, WrongDateFormatException, VueloDTONotFoundException, ExceededArgumentLimitException {
        when(vueloDTORepository.loadDataBase()).thenReturn(vueloDTOList);

        List<VueloDTO> listaAux = vueloService.listarVuelos(new HashMap<>());
        Assertions.assertEquals(vueloDTOList,listaAux);
    }

    @Test
    public void listarVuelosServiceWithTwoArgumentsTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, VueloDTONotFoundException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTO","21/02/2021");

        when(vueloDTORepository.loadDataBase()).thenReturn(vueloDTOList);
        when(vueloDTORepository.findFlightByDates(vueloDTOList,map)).thenReturn(vueloDTOList);

        List<VueloDTO> listaAux = vueloService.listarVuelos(map);
        Assertions.assertEquals(vueloDTOList,listaAux);
    }

    @Test
    public void listarVuelosServiceWithFourArgumentsTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, VueloDTONotFoundException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTO","21/02/2021");
        map.put("destination","bogotá");
        map.put("origin","double");

        when(vueloDTORepository.loadDataBase()).thenReturn(vueloDTOList);
        when(vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOList,map)).thenReturn(vueloDTOList);

        List<VueloDTO> listaAux = vueloService.listarVuelos(map);
        Assertions.assertEquals(vueloDTOList,listaAux);
    }

    @Test
    public void listarVuelosServiceTooManyArgumentsException() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        when(vueloDTORepository.loadDataBase()).thenReturn(vueloDTOList);

        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTO","21/02/2021");
        map.put("destination","bogotá");
        map.put("origin","double");
        map.put("seatType","Business");

        Assertions.assertThrows(ExceededArgumentLimitException.class,()->{
            List<VueloDTO> listaAux = vueloService.listarVuelos(map);
        });
    }

    @Test
    public void procesarReservaVueloDebitServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException, VueloNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("DEBIT","1234-1234-1234-1234", 1);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),null,null,"BAPI-1235",2,null,null,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);

        when(vueloDTORepository.findFlightByCodeAndDates(flightReservationDTO)).thenReturn(vueloDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = vueloService.procesarReserva(flightReservationDTO);

        Assertions.assertEquals(vueloDTOList.get(0).getFlightNumber(),flightReservationResponseDTO.getFlightReservation().getFlightNumber());
    }

    @Test
    public void procesarReservaVueloThreeDuesServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException, VueloNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),null,null,"BAPI-1235",2,null,null,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);

        when(vueloDTORepository.findFlightByCodeAndDates(flightReservationDTO)).thenReturn(vueloDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = vueloService.procesarReserva(flightReservationDTO);

        Assertions.assertEquals(vueloDTOList.get(0).getFlightNumber(),flightReservationResponseDTO.getFlightReservation().getFlightNumber());
    }
    @Test
    public void procesarReservaVueloSixDuesServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException, VueloNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 6);
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),null,null,"BAPI-1235",2,null,null,paymentMethodDTO);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);

        when(vueloDTORepository.findFlightByCodeAndDates(flightReservationDTO)).thenReturn(vueloDTOList);

        FlightReservationResponseDTO flightReservationResponseDTO = vueloService.procesarReserva(flightReservationDTO);

        Assertions.assertEquals(vueloDTOList.get(0).getFlightNumber(),flightReservationResponseDTO.getFlightReservation().getFlightNumber());
    }

    @Test
    public void procesarReservaVueloServiceNotAvailableException() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),null,null,"BAPI-1235",2,null,null,null);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("User",flightReservation);

        Assertions.assertThrows(VueloNotAvailableException.class,()->{
            vueloService.procesarReserva(flightReservationDTO);
        });
    }

}