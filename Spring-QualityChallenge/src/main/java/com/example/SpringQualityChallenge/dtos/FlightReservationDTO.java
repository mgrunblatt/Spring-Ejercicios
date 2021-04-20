package com.example.SpringQualityChallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightReservationDTO {

        private String userName;
        private FlightReservation flightReservation;
}
