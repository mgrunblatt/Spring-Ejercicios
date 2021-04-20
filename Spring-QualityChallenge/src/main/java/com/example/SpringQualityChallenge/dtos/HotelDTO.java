package com.example.SpringQualityChallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {

    private String codeHotel;
    private String name;
    private String city;
    private String roomType;
    private double nightPrice;
    private LocalDate dateFrom;
    private LocalDate dateTo;
    private String reserved;

    public String getAttribute(String value){
        String attribute=null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        switch (value){
            case "codeHotel": attribute = getCodeHotel();
                break;
            case "name": attribute = getName();
                    break;
            case "city": attribute = getCity();
                break;
            case "roomType": attribute = getRoomType();
                break;
            /*case "dateTo": attribute = getDateTo();
                break;
            case "dateFrom": attribute = getDateFrom();
                break;*/
            case "reserved": attribute = getReserved();
                break;
        }
        return attribute;
    }
}
