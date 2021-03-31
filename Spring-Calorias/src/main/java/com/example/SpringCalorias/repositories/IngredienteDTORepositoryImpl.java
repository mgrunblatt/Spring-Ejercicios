package com.example.SpringCalorias.repositories;

import com.example.SpringCalorias.dtos.FoodDTO;
import com.example.SpringCalorias.dtos.IngredienteDTO;
import com.example.SpringCalorias.exception.IngredientNotFound;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public class IngredienteDTORepositoryImpl implements IngredienteDTORepository{


    @Override
    public FoodDTO findIngredienteByName(String name) throws IngredientNotFound {
        List<FoodDTO> foodDTOS = null;
        foodDTOS = loadDataBase();
        FoodDTO result = null;
        if (foodDTOS != null){
            Optional<FoodDTO> item = foodDTOS.stream()
                    .filter(foodDTO -> foodDTO.getName().equals(name))
                    .findFirst();
            if (item.isPresent())
                result = item.get();
            else throw new IngredientNotFound(name);
        }

        return result;
    }

    private List<FoodDTO> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:food.json");
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<FoodDTO>> typeRef = new TypeReference<List<FoodDTO>>() {};
        List<FoodDTO> foodDTOS = null;

        try {
            foodDTOS = objectMapper.readValue(file, typeRef);

        }catch (Exception e){
            e.printStackTrace();
        }

        return foodDTOS;

    }
}
