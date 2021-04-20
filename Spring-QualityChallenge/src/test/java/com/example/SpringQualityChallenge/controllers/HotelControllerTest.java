package com.example.SpringQualityChallenge.controllers;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.services.HotelServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class HotelControllerTest {

    @Mock
    private HotelServiceImpl service;

    private HotelController hotelController;

    private List<HotelDTO> hotelDTOList=new ArrayList<>();

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        hotelController = new HotelController(service);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        HotelDTO hotelDTO1 = new HotelDTO("CH-0002","Cataratas Hotel","Puerto Iguazú","Doble",6300, LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter), "false");
        hotelDTOList.add(hotelDTO1);
    }

    @Test
    public void listarHoteles() throws Exception {
        when(service.listarHoteles(any())).thenReturn(hotelDTOList);

        List<HotelDTO> listaAux = (List<HotelDTO>) hotelController.listarHoteles(new HashMap<>()).getBody();
        Assertions.assertEquals(hotelDTOList,listaAux);
    }

    @Test
    void realizarReservaHotel() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@gmail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        ResponseEntity responseEntity = hotelController.realizarReserva(bookingDTO);

        Assertions.assertEquals(hotelDTOList.get(0).getCodeHotel(),bookingResponseDTO.getBooking().getHotelCode());
    }

    @Test
    void realizarReservaHotelWrongRangeDateException() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@gmail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("09/02/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(WrongRangeDateException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

    @Test
    void realizarReservaHotelWrongPeopleEmailException() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(WrongEmailFormatException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

    @Test
    void realizarReservaHotelNotDuesAllowException() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("DEBIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@mail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(PaymentMethodNotAllowDuesException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

    @Test
    void realizarReservaHotelWrongRoomTypeSingle() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@mail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Somewhere","CH-0002",2,"SINGLE",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(NotMatchingRoomAndPeopleAmountException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

    @Test
    void realizarReservaHotelWrongRoomTypeDouble() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@mail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Somewhere","CH-0002",3,"DOUBLE",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(NotMatchingRoomAndPeopleAmountException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

    @Test
    void realizarReservaHotelWrongRoomTypeTriple() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@mail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Somewhere","CH-0002",2,"TRIPLE",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(NotMatchingRoomAndPeopleAmountException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

    @Test
    void realizarReservaHotelWrongRoomTypeMultiple() throws VueloNotAvailableException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, WrongRangeDateException, HotelDTONotFoundException, HotelNotAvailableException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        PaymentMethodDTO paymentMethodDTO = new PaymentMethodDTO("CREDIT","1234-1234-1234-1234", 3);
        PeopleDTO peopleDTO = new PeopleDTO("13345678","Fulanito","Gomez",LocalDate.parse("10/02/2021",formatter),"fulanitogomez@mail.com");
        List<PeopleDTO> peopleDTOList = new ArrayList<>();
        peopleDTOList.add(peopleDTO);
        Booking booking = new Booking(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("15/02/2021",formatter),"Somewhere","CH-0002",2,"MULTIPLE",peopleDTOList,paymentMethodDTO);
        BookingDTO bookingDTO = new BookingDTO("User",booking);
        BookingResponse bookingResponse = new BookingResponse(LocalDate.parse("10/02/2021",formatter), LocalDate.parse("20/03/2021",formatter),"Somewhere","CH-0002",2,"Double",peopleDTOList);

        BookingResponseDTO bookingResponseDTO = new BookingResponseDTO("user",500.0,10,550,bookingResponse,new StatusCodeDTO(200,"Satisfactorio"));
        when(service.procesarReserva(bookingDTO)).thenReturn(bookingResponseDTO);

        Assertions.assertThrows(NotMatchingRoomAndPeopleAmountException.class,()->{
            hotelController.realizarReserva(bookingDTO);
        });
    }

}
