package com.example.SpringQualityChallenge.services;

import com.example.SpringQualityChallenge.dtos.HotelDTO;
import com.example.SpringQualityChallenge.dtos.BookingDTO;
import com.example.SpringQualityChallenge.dtos.BookingResponseDTO;
import com.example.SpringQualityChallenge.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HotelService {

    public List<HotelDTO> listarHoteles(Map<String,String> params) throws IOException, ExceededArgumentLimitException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException;
    public BookingResponseDTO procesarReserva(BookingDTO bookingDTO) throws HotelDTONotFoundException, IOException, HotelNotAvailableException;
}
