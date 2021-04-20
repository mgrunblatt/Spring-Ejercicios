package com.example.SpringQualityChallenge.services;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface VueloService {

    //public VueloDTO darAltaCliente(VueloDTO vueloDTO) throws DuplicateClientException, ClientIncompleteDataException;
    public List<VueloDTO> listarVuelos(Map<String,String> params) throws VueloDTONotFoundException, ExceededArgumentLimitException, NotExistedDestinyException, WrongDateFormatException;
    public FlightReservationResponseDTO procesarReserva(FlightReservationDTO flightReservationDTO) throws HotelDTONotFoundException, IOException, HotelNotAvailableException, VueloNotAvailableException;
}
