package com.example.SpringQualityChallenge.repositories;

import com.example.SpringQualityChallenge.dtos.FlightReservation;
import com.example.SpringQualityChallenge.dtos.FlightReservationDTO;
import com.example.SpringQualityChallenge.dtos.VueloDTO;
import com.example.SpringQualityChallenge.exceptions.NotExistedDestinyException;
import com.example.SpringQualityChallenge.exceptions.VueloDTONotFoundException;
import com.example.SpringQualityChallenge.exceptions.WrongDateFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class VueloDTORepositoryImplTest {

    private VueloDTORepositoryImpl vueloDTORepository;

    List<VueloDTO> vueloDTOList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        vueloDTORepository = new VueloDTORepositoryImpl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        VueloDTO vueloDTO = new VueloDTO("BAPI-1235","Buenos Aires","Puerto Iguazú","Economy",6500, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter));
        //HotelDTO hotelDTO2 = new HotelDTO("CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter));
        vueloDTOList.add(vueloDTO);
        //hotelDTOListWrong.add(hotelDTO2);
    }

    @Test
    void findFlightByCodeAndDatesTest() throws WrongDateFormatException, VueloDTONotFoundException {
        Map<String, String> map = new HashMap<>();

        map.put("flightNumber","BAPI-1235");
        map.put("dateFrom","10/02/2021");
        map.put("dateTo","20/03/2021");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        FlightReservation flightReservation = new FlightReservation(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),null,null,"BAPI-1235",2,null,null,null);
        FlightReservationDTO flightReservationDTO = new FlightReservationDTO("user",flightReservation);
        List<VueloDTO> listaAux = vueloDTORepository.findFlightByCodeAndDates(flightReservationDTO);
        Assertions.assertEquals(vueloDTOList,listaAux);

    }

    @Test
    void findFlightByDatesTest() throws WrongDateFormatException, VueloDTONotFoundException {
        Map<String, String> map = new HashMap<>();


        map.put("dateFrom","10/02/2021");
        map.put("dateTo","15/02/2021");

        List<VueloDTO> listaAux = vueloDTORepository.findFlightByDates(vueloDTOList,map);
        Assertions.assertEquals(vueloDTOList,listaAux);
    }

    @Test
    void findFlightByDatesOriginAndDestinationTest() throws NotExistedDestinyException, VueloDTONotFoundException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","15/02/2021");
        map.put("origin","Buenos Aires");
        map.put("destination","Puerto Iguazú");

        List<VueloDTO> listaAux = vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOList,map);
        Assertions.assertEquals(vueloDTOList,listaAux);
    }

    @Test
    void findFlightByDatesOriginAndWrongDestinationTest() throws NotExistedDestinyException, VueloDTONotFoundException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","15/02/2021");
        map.put("origin","Buenos Aires");
        map.put("destination","Iguazú");

        Assertions.assertThrows(NotExistedDestinyException.class,()->{
            vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOList,map);
        });

    }

    @Test
    void findFlightByDatesWithWrongDateFromTest() throws NotExistedDestinyException, VueloDTONotFoundException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/21");
        map.put("dateTo","15/02/2021");
        map.put("origin","Buenos Aires");
        map.put("destination","Puerto Iguazú");

        Assertions.assertThrows(WrongDateFormatException.class,()->{
            vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOList,map);
        });

    }

    @Test
    void findFlightByDatesWithWrongDateToTest() throws NotExistedDestinyException, VueloDTONotFoundException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","15/02/21");
        map.put("origin","Buenos Aires");
        map.put("destination","Puerto Iguazú");

        Assertions.assertThrows(WrongDateFormatException.class,()->{
            vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOList,map);
        });

    }

    @Test
    void findFlightByDatesNotFoundExceptionTest() throws NotExistedDestinyException, VueloDTONotFoundException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","15/02/2021");
        map.put("dateTo","28/02/2021");
        map.put("origin","Buenos Aires");
        map.put("destination","Puerto Iguazú");

        Assertions.assertThrows(VueloDTONotFoundException.class,()->{
            vueloDTORepository.findFlightByDatesOriginAndDestination(vueloDTOList,map);
        });

    }

}