package com.example.SpringStarWars.services;

import com.example.SpringStarWars.dtos.PersonajeDTO;
import com.example.SpringStarWars.dtos.StarWarsDTO;

import java.util.List;

public interface Service {

    public List<StarWarsDTO> buscarPersonajes(String name);
}
