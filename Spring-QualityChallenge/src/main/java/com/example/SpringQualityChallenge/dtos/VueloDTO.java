package com.example.SpringQualityChallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VueloDTO {

        private String flightNumber;
        private String origin;
        private String destination;
        private String seatType;
        private double price;
        private LocalDate dateFrom;
        private LocalDate dateTo;


}
