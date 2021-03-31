package com.example.SpringStarWars.repositories;

import com.example.SpringStarWars.dtos.StarWarsDTO;

import java.util.List;

public interface PersonajeDTORepository {

    List<StarWarsDTO> findIngredienteByName(String name);
}
