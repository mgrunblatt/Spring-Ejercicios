package com.example.SpringQualityChallenge.repositories;

import com.example.SpringQualityChallenge.dtos.HotelDTO;
import com.example.SpringQualityChallenge.exceptions.HotelDTONotFoundException;
import com.example.SpringQualityChallenge.exceptions.NotExistedDestinyException;
import com.example.SpringQualityChallenge.exceptions.WrongDateFormatException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface HotelDTORepository {

    public List<HotelDTO> findHotelByDatesAndDestination(List<HotelDTO> hotelDTOS, Map<String,String> params) throws IOException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException;
    public List<HotelDTO> findHotelByDates(List<HotelDTO> articleDTOS, Map<String,String> params) throws IOException, HotelDTONotFoundException, WrongDateFormatException;
    public List<HotelDTO> findHotelByCode(String code);
    //public List<HotelDTO> findProductsByCategoriesAndOrder(List<HotelDTO> articleDTOS, Map<String,String> params) throws IOException, ArticleDTONotFoudException;
    //public HotelDTO findArticleByName(String name, String brand) throws ArticleDTONotFoudException;

}
