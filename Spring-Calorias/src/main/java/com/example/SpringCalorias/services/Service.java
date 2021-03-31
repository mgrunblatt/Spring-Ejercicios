package com.example.SpringCalorias.services;

import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.dtos.PlatoDTO;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface Service {

    public ResponseEntity caloriasTotales(PlatoDTO plato);
    public ResponseEntity caloriasPorIngrediente(PlatoDTO plato);
    public ResponseEntity ingredienteMasCalorico(PlatoDTO plato);


}
