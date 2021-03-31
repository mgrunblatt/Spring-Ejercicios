package com.example.SpringCalorias.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class PlatoDTO {

    private String name;
    private List<IngredienteDTO> ingredientes=new ArrayList<>();
    private int caloriasTotales;
}
