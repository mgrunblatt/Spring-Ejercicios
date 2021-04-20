package com.example.SpringQualityChallenge.services;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.repositories.HotelDTORepositoryImpl;
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

import static org.mockito.Mockito.when;

class HotelServiceImplTest {

    @Mock
    private HotelDTORepositoryImpl hotelDTORepository;

    private HotelServiceImpl hotelService;

    private List<HotelDTO> hotelDTOList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        hotelService = new HotelServiceImpl(hotelDTORepository);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        HotelDTO hotelDTO1 = new HotelDTO("CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter), "false");
        hotelDTOList.add(hotelDTO1);
    }

    @Test
    public void listarHotelsServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        when(hotelDTORepository.loadDataBase()).thenReturn(hotelDTOList);

        List<HotelDTO> listaAux = hotelService.listarHoteles(new HashMap<>());
        Assertions.assertEquals(hotelDTOList,listaAux);
    }

    @Test
    public void listarHotelsServiceWithTwoArguments() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTO","21/02/2021");
        when(hotelDTORepository.loadDataBase()).thenReturn(hotelDTOList);
        when(hotelDTORepository.findHotelByDates(hotelDTOList,map)).thenReturn(hotelDTOList);



        List<HotelDTO> listaAux = hotelService.listarHoteles(map);
        Assertions.assertEquals(hotelDTOList,listaAux);
    }

    @Test
    public void listarHotelsServiceWithThreeArguments() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTO","21/02/2021");
        map.put("destination","Puerto Iguazú");
        when(hotelDTORepository.loadDataBase()).thenReturn(hotelDTOList);
        when(hotelDTORepository.findHotelByDatesAndDestination(hotelDTOList,map)).thenReturn(hotelDTOList);



        List<HotelDTO> listaAux = hotelService.listarHoteles(map);
        Assertions.assertEquals(hotelDTOList,listaAux);
    }


    @Test
    public void listarHotelsServiceTooManyArgumentsException() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        when(hotelDTORepository.loadDataBase()).thenReturn(hotelDTOList);

        Map<String, String> map = new HashMap<>();

        map.put("dateFrom","10/02/2021");
        map.put("dateTO","21/02/2021");
        map.put("destination","bogotá");
        map.put("roomType","double");

        Assertions.assertThrows(ExceededArgumentLimitException.class,()->{
            List<HotelDTO> listaAux = hotelService.listarHoteles(map);
        });
    }

    @Test
    public void procesarReservaHotelDebitServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("DEBIT","1234-1234-1234-1234", 1);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",null,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);

        when(hotelDTORepository.findHotelByCode("CH-0002")).thenReturn(hotelDTOList);

        BookingResponseDTO bookingResponseDTO = hotelService.procesarReserva(bookingDTO);

        Assertions.assertEquals(hotelDTOList.get(0).getCodeHotel(),bookingResponseDTO.getBooking().getHotelCode());
        /*Assertions.assertThrows(HotelNotAvailableException.class,()->{
            hotelService.procesarReserva(bookingDTO);
        });*/
    }

    @Test
    public void procesarReservaHotelsThreeDuesServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",null,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);

        when(hotelDTORepository.findHotelByCode("CH-0002")).thenReturn(hotelDTOList);

        BookingResponseDTO bookingResponseDTO = hotelService.procesarReserva(bookingDTO);

        Assertions.assertEquals(hotelDTOList.get(0).getCodeHotel(),bookingResponseDTO.getBooking().getHotelCode());
        /*Assertions.assertThrows(HotelNotAvailableException.class,()->{
            hotelService.procesarReserva(bookingDTO);
        });*/
    }

    @Test
    public void procesarReservaHotelsSixDuesServiceTest() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 6);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",null,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);

        when(hotelDTORepository.findHotelByCode("CH-0002")).thenReturn(hotelDTOList);

        BookingResponseDTO bookingResponseDTO = hotelService.procesarReserva(bookingDTO);

        Assertions.assertEquals(hotelDTOList.get(0).getCodeHotel(),bookingResponseDTO.getBooking().getHotelCode());
        /*Assertions.assertThrows(HotelNotAvailableException.class,()->{
            hotelService.procesarReserva(bookingDTO);
        });*/
    }

    @Test
    public void procesarReservaHotelsServiceHotelNotAvailableException() throws ExceededArgumentLimitException, IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException, HotelNotAvailableException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH",2,"Double",null,null);
        BookingDTO bookingDTO = new BookingDTO("User",booking);

        Assertions.assertThrows(HotelNotAvailableException.class,()->{
            hotelService.procesarReserva(bookingDTO);
        });
    }

}
