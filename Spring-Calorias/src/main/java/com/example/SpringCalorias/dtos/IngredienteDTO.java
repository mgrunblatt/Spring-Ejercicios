package com.example.SpringCalorias.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IngredienteDTO {

    private String name;
    private int gramos;
    private int calorias;

}
