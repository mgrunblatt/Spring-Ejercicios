package com.example.Springdesafio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseArticleDTO {

    private int productId;
    private String name;
    private String brand;
    private int quantity;

}
