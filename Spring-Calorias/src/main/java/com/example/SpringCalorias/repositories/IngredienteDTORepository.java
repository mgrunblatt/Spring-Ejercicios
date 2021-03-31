package com.example.SpringCalorias.repositories;

import com.example.SpringCalorias.dtos.FoodDTO;
import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.dtos.PlatoDTO;
import com.example.SpringCalorias.exception.IngredientNotFound;

public interface IngredienteDTORepository {

    FoodDTO findIngredienteByName(String name) throws IngredientNotFound;
}
