package com.example.SpringCalorias.services;

import com.example.SpringCalorias.dtos.FoodDTO;
import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.dtos.PlatoDTO;
import com.example.SpringCalorias.repositories.IngredienteDTORepositoryImpl;
import com.sun.tools.internal.xjc.reader.xmlschema.BindGreen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    @Autowired
    private IngredienteDTORepositoryImpl ingredienteDTORepository;

    @Override
    public PlatoDTO caloriasTotales(PlatoDTO plato) {
        int caloriasTotales=0;
        for(IngredienteDTO ingredienteDTO: plato.getIngredientes()){
            FoodDTO foodDTO =ingredienteDTORepository.findIngredienteByName(ingredienteDTO.getName());
            caloriasTotales+=ingredienteDTO.getGramos()*foodDTO.getCalories();
        }

        plato.setCaloriasTotales(caloriasTotales);

        return plato;
    }

    @Override
    public HashMap<String,Integer> caloriasPorIngrediente(PlatoDTO plato) {
        HashMap<String,Integer> ingredientesMap=new HashMap<String, Integer>();
        for(IngredienteDTO ingredienteDTO: plato.getIngredientes()){
            FoodDTO foodDTO =ingredienteDTORepository.findIngredienteByName(ingredienteDTO.getName());
            ingredientesMap.put(ingredienteDTO.getName(),ingredienteDTO.getGramos()*foodDTO.getCalories());
        }


        return ingredientesMap;
    }

    @Override
    public IngredienteDTO ingredienteMasCalorico(PlatoDTO plato) {
        IngredienteDTO ingredienteMayorDTO=null;
        int mayoresCalorias=0;
        for(IngredienteDTO ingredienteDTO: plato.getIngredientes()){
            FoodDTO foodDTO =ingredienteDTORepository.findIngredienteByName(ingredienteDTO.getName());
            if(foodDTO.getCalories()>mayoresCalorias){
                ingredienteMayorDTO=ingredienteDTO;
                mayoresCalorias=foodDTO.getCalories();
            }
        }

        ingredienteMayorDTO.setCalorias(mayoresCalorias);

        return ingredienteMayorDTO;
    }
}
