package com.example.SpringQualityChallenge.repositories;

import com.example.SpringQualityChallenge.dtos.FlightReservationDTO;
import com.example.SpringQualityChallenge.dtos.VueloDTO;
import com.example.SpringQualityChallenge.exceptions.NotExistedDestinyException;
import com.example.SpringQualityChallenge.exceptions.VueloDTONotFoundException;
import com.example.SpringQualityChallenge.exceptions.WrongDateFormatException;

import java.util.List;
import java.util.Map;

public interface VueloDTORepository {
    public List<VueloDTO> findFlightByCodeAndDates(FlightReservationDTO flightReservationDTO);
    public List<VueloDTO> findFlightByDates (List<VueloDTO> vueloDTOS, Map<String, String> params) throws WrongDateFormatException, VueloDTONotFoundException;
    public List<VueloDTO> findFlightByDatesOriginAndDestination (List<VueloDTO> vueloDTOS, Map<String, String> params) throws WrongDateFormatException, VueloDTONotFoundException, NotExistedDestinyException;
}
