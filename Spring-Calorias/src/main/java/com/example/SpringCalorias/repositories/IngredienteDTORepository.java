package com.example.SpringCalorias.repositories;

import com.example.SpringCalorias.dtos.FoodDTO;
import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.dtos.PlatoDTO;

public interface IngredienteDTORepository {

    FoodDTO findIngredienteByName(String name);
}
