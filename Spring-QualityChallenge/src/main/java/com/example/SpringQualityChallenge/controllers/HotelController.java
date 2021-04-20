package com.example.SpringQualityChallenge.controllers;

import com.example.SpringQualityChallenge.dtos.BookingDTO;
import com.example.SpringQualityChallenge.dtos.PeopleDTO;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.services.HotelServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class HotelController {

    private HotelServiceImpl hotelService;

    public HotelController(HotelServiceImpl hotelService){
        this.hotelService = hotelService;
    }

    /**
     * Allows to get the whole hotels list without arguments or get filter by dates, or by a combination of two dates
     * and also a destination.
     * Example: localhost:8081/api/v1/hotels?dateFrom=15/02/2021&dateTo=28/02/2021&destination=Buenos Aires
     * More info in README.md
     * @param params
     * @return
     * @throws IOException
     * @throws ExceededArgumentLimitException
     * @throws HotelDTONotFoundException
     * @throws NotExistedDestinyException
     * @throws WrongDateFormatException
     */
    @GetMapping("/hotels")
    public ResponseEntity listarHoteles(@RequestParam Map<String,String> params) throws IOException, ExceededArgumentLimitException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        return new ResponseEntity(hotelService.listarHoteles(params), HttpStatus.OK);
    }


    /**
     * Allows a POST with a BookingDTO object with a userName and a booking inside. More info in README.md
     * @param bookingDTO
     * @return
     * @throws HotelDTONotFoundException
     * @throws IOException
     * @throws WrongRangeDateException
     * @throws NotMatchingRoomAndPeopleAmountException
     * @throws WrongEmailFormatException
     * @throws PaymentMethodNotAllowDuesException
     * @throws HotelNotAvailableException
     */
    @PostMapping("/booking")
    public ResponseEntity realizarReserva(@RequestBody BookingDTO bookingDTO) throws HotelDTONotFoundException, IOException, WrongRangeDateException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException, HotelNotAvailableException {
        bookingDTO= validateData(bookingDTO);
        return new ResponseEntity(hotelService.procesarReserva(bookingDTO), HttpStatus.OK);
    }

    public BookingDTO validateData(BookingDTO bookingDTO) throws WrongRangeDateException, NotMatchingRoomAndPeopleAmountException, WrongEmailFormatException, PaymentMethodNotAllowDuesException {
        if(bookingDTO.getBooking().getDateFrom().compareTo(bookingDTO.getBooking().getDateTo())>0){
            throw new WrongRangeDateException("DateFrom: "+bookingDTO.getBooking().getDateFrom() + " and dateTo: "+bookingDTO.getBooking().getDateTo());
        }
        validateRoomTipe(bookingDTO);
        validateMail(bookingDTO);
        validatePaymentMethod(bookingDTO);
        return bookingDTO;
    }

    private void validateRoomTipe(BookingDTO bookingDTO) throws NotMatchingRoomAndPeopleAmountException {
        switch (bookingDTO.getBooking().getRoomType().toUpperCase(Locale.ROOT)){
            case "SINGLE":if(bookingDTO.getBooking().getPeopleAmount()!=1){
                throw new NotMatchingRoomAndPeopleAmountException("Single room allows 1 person but you requested: "+bookingDTO.getBooking().getPeopleAmount());
            }
                break;
            case "DOUBLE":if(bookingDTO.getBooking().getPeopleAmount()!=2){
                throw new NotMatchingRoomAndPeopleAmountException("Double room allows 2 persons but you requested: "+bookingDTO.getBooking().getPeopleAmount());
            }
                break;
            case "TRIPLE":if(bookingDTO.getBooking().getPeopleAmount()!=3){
                throw new NotMatchingRoomAndPeopleAmountException("Triple room allows 3 persons but you requested: "+bookingDTO.getBooking().getPeopleAmount());
            }
                break;
            case "MULTIPLE":if(bookingDTO.getBooking().getPeopleAmount()<=3){
                throw new NotMatchingRoomAndPeopleAmountException("Multiple room moust be 4 or more persons but you requested: "+bookingDTO.getBooking().getPeopleAmount());
            }
                break;
        }
    }

    private void validateMail(BookingDTO bookingDTO) throws WrongEmailFormatException {
        List<PeopleDTO> peopleDTOList = bookingDTO.getBooking().getPeople();

        for(PeopleDTO peopleDTO: peopleDTOList){
            if(!peopleDTO.getMail().contains("@")|| !peopleDTO.getMail().contains(".com")){
                throw new WrongEmailFormatException(peopleDTO.getMail());
            }
        }
    }

    private void validatePaymentMethod(BookingDTO bookingDTO) throws PaymentMethodNotAllowDuesException {
        if(!bookingDTO.getBooking().getPaymentMethod().getType().equals("CREDIT")
                && bookingDTO.getBooking().getPaymentMethod().getDues()>1){
            throw new PaymentMethodNotAllowDuesException(bookingDTO.getBooking().getPaymentMethod().getType());
        }
    }


}
