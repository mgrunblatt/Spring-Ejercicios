package com.example.SpringStarWars.repositories;

import com.example.SpringStarWars.dtos.StarWarsDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class PersonajeDTORepositoryImpl implements PersonajeDTORepository{
    @Override
    public List<StarWarsDTO> findIngredienteByName(String name) {
        List<StarWarsDTO> starWarsDTOS = null;
        starWarsDTOS = loadDataBase();
        StarWarsDTO result = null;
        List<StarWarsDTO> starWarsDTOList=new ArrayList<>();
        if (starWarsDTOS != null){
                    starWarsDTOList = starWarsDTOS.stream()
                    .filter(starwarsDTO -> starwarsDTO.getName().contains(name))
                    .collect(Collectors.toList());
        }

        return starWarsDTOList;
    }

    private List<StarWarsDTO> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:starwars.json");
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<StarWarsDTO>> typeRef = new TypeReference<List<StarWarsDTO>>() {};
        List<StarWarsDTO> foodDTOS = null;

        try {
            foodDTOS = objectMapper.readValue(file, typeRef);

        }catch (Exception e){
            e.printStackTrace();
        }

        return foodDTOS;

    }
}
