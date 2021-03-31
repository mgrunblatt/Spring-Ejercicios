package com.example.SpringCalorias.services;

import com.example.SpringCalorias.dtos.FoodDTO;
import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.dtos.PlatoDTO;
import com.example.SpringCalorias.exception.ErrorDTO;
import com.example.SpringCalorias.exception.IngredientNotFound;
import com.example.SpringCalorias.repositories.IngredienteDTORepositoryImpl;
import com.sun.tools.internal.xjc.reader.xmlschema.BindGreen;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    @Autowired
    private IngredienteDTORepositoryImpl ingredienteDTORepository;

    @Override
    public ResponseEntity caloriasTotales(PlatoDTO plato) {
        int caloriasTotales=0;
        try {
            for (IngredienteDTO ingredienteDTO : plato.getIngredientes()) {
                FoodDTO foodDTO = ingredienteDTORepository.findIngredienteByName(ingredienteDTO.getName());
                caloriasTotales += ingredienteDTO.getGramos() * foodDTO.getCalories();
            }
        }catch (IngredientNotFound e){
            e.printStackTrace();
            return new ResponseEntity(new ErrorDTO("Invalid ingredient", "Ingredient "+ e.getMessage()+" not found"), HttpStatus.BAD_REQUEST);
        }
        plato.setCaloriasTotales(caloriasTotales);

        return new ResponseEntity(plato,HttpStatus.OK);
    }

    @Override
    public ResponseEntity caloriasPorIngrediente(PlatoDTO plato) {
        HashMap<String,Integer> ingredientesMap=new HashMap<String, Integer>();
        try {
            for (IngredienteDTO ingredienteDTO : plato.getIngredientes()) {
                FoodDTO foodDTO = ingredienteDTORepository.findIngredienteByName(ingredienteDTO.getName());
                ingredientesMap.put(ingredienteDTO.getName(), ingredienteDTO.getGramos() * foodDTO.getCalories());
            }
        }catch (IngredientNotFound e){
            e.printStackTrace();
            return new ResponseEntity(new ErrorDTO("Invalid ingredient", "Ingredient "+ e.getMessage()+" not found"), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity(ingredientesMap,HttpStatus.OK);
    }

    @Override
    public ResponseEntity ingredienteMasCalorico(PlatoDTO plato) {
        IngredienteDTO ingredienteMayorDTO=null;
        int mayoresCalorias=0;
        try {
            for (IngredienteDTO ingredienteDTO : plato.getIngredientes()) {
                FoodDTO foodDTO = ingredienteDTORepository.findIngredienteByName(ingredienteDTO.getName());
                if (foodDTO.getCalories() > mayoresCalorias) {
                    ingredienteMayorDTO = ingredienteDTO;
                    mayoresCalorias = foodDTO.getCalories();
                }
            }
        }catch (IngredientNotFound e){
            e.printStackTrace();
            return new ResponseEntity(new ErrorDTO("Invalid ingredient", "Ingredient "+ e.getMessage()+" not found"), HttpStatus.BAD_REQUEST);
        }

        ingredienteMayorDTO.setCalorias(mayoresCalorias);

        return new ResponseEntity(ingredienteMayorDTO,HttpStatus.OK);
    }
}
