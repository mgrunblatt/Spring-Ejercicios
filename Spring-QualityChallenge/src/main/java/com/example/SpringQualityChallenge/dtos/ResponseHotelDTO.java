package com.example.SpringQualityChallenge.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseHotelDTO {

    private int productId;
    private String name;
    private String brand;
    private int quantity;

}
