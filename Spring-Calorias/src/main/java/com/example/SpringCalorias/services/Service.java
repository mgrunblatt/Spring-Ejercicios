package com.example.SpringCalorias.services;

import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.dtos.PlatoDTO;

import java.util.HashMap;

public interface Service {

    public PlatoDTO caloriasTotales(PlatoDTO plato);
    public HashMap<String,Integer> caloriasPorIngrediente(PlatoDTO plato);
    public IngredienteDTO ingredienteMasCalorico(PlatoDTO plato);


}
