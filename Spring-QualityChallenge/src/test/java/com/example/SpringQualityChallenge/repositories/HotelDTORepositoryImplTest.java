package com.example.SpringQualityChallenge.repositories;

import com.example.SpringQualityChallenge.dtos.HotelDTO;
import com.example.SpringQualityChallenge.exceptions.HotelDTONotFoundException;
import com.example.SpringQualityChallenge.exceptions.NotExistedDestinyException;
import com.example.SpringQualityChallenge.exceptions.WrongDateFormatException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class HotelDTORepositoryImplTest {

    private HotelDTORepositoryImpl hotelDTORepository;

    private List<HotelDTO> hotelDTOList=new ArrayList<>();
    private List<HotelDTO> hotelDTOListWrong=new ArrayList<>();

    @BeforeEach
    void setUp(){
        hotelDTORepository = new HotelDTORepositoryImpl();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        HotelDTO hotelDTO1 = new HotelDTO("CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter), "NO");
        HotelDTO hotelDTO2 = new HotelDTO("CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter), "false");
        hotelDTOList.add(hotelDTO1);
        hotelDTOListWrong.add(hotelDTO2);
    }



    @Test
    void findHotelByDatesTest() throws HotelDTONotFoundException, WrongDateFormatException, IOException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","20/03/2021");

        List<HotelDTO> listaAux = hotelDTORepository.findHotelByDates(hotelDTOList,map);
        Assertions.assertEquals(hotelDTOList,listaAux);

    }

    @Test
    void findHotelByCodeTest(){
        List<HotelDTO> listaAux = hotelDTORepository.findHotelByCode("CH-0002");
        Assertions.assertEquals(hotelDTOList,listaAux);
    }

    @Test
    void findHotelByDatesAndDestinationTest() throws HotelDTONotFoundException, WrongDateFormatException, IOException, NotExistedDestinyException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","20/03/2021");
        map.put("destination","Puerto Iguazú");

        List<HotelDTO> listaAux = hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map);
        Assertions.assertEquals(hotelDTOList,listaAux);

    }

    @Test
    void findHotelByDatesAndWithWrongDestinationTest() throws HotelDTONotFoundException, WrongDateFormatException, IOException, NotExistedDestinyException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","20/03/2021");
        map.put("destination","Bogotá");

        Assertions.assertThrows(NotExistedDestinyException.class,()->{
            hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map);
        });

    }
    @Test
    void findHotelByDatesWithWrongDateFromFormatTest() throws HotelDTONotFoundException, WrongDateFormatException, IOException, NotExistedDestinyException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/21");
        map.put("dateTo","20/03/2021");
        map.put("destination","Bogotá");

       /* List<HotelDTO> listaAux = hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map);
        Assertions.assertEquals(hotelDTOList,listaAux);*/

        Assertions.assertThrows(WrongDateFormatException.class,()->{
            hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map);
        });

    }

    @Test
    void findHotelByDatesWithWrongDateToFormatTest() throws HotelDTONotFoundException, WrongDateFormatException, IOException, NotExistedDestinyException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","20/03/21");
        map.put("destination","Bogotá");

        Assertions.assertThrows(WrongDateFormatException.class,()->{
            hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map);
        });

    }

    @Test
    void findHotelNotFoundExceptionTest() throws HotelDTONotFoundException, WrongDateFormatException, IOException, NotExistedDestinyException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","25/03/2021");
        map.put("destination","Bogotá");

        Assertions.assertThrows(HotelDTONotFoundException.class,()->{
            hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map);
        });

    }

    /*@Test
    void findHotelByDatesMissingMatch() throws HotelDTONotFoundException, WrongDateFormatException, IOException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTo","20/03/2021");
        when(hotelDTORepository.findHotelByDates(hotelDTOListWrong,map)).thenReturn(hotelDTOListWrong);



        *//*List<HotelDTO> listaAux = hotelDTORepository.findHotelByDates(hotelDTOListWrong,map);
        Assertions.assertEquals(hotelDTOListWrong,listaAux);*//*
        Assertions.assertThrows(HotelDTONotFoundException.class,()->{
            hotelDTORepository.findHotelByDates(hotelDTOListWrong,map);
        });

    }*/

}