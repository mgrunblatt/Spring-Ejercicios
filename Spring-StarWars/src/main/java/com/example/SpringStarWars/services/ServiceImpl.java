package com.example.SpringStarWars.services;

import com.example.SpringStarWars.dtos.PersonajeDTO;
import com.example.SpringStarWars.dtos.StarWarsDTO;
import com.example.SpringStarWars.repositories.PersonajeDTORepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@org.springframework.stereotype.Service
public class ServiceImpl implements Service{

    @Autowired
    private PersonajeDTORepositoryImpl personajeDTORepositoryImpl;

    @Override
    public List<StarWarsDTO> buscarPersonajes(String name) {

        return personajeDTORepositoryImpl.findIngredienteByName(name);
    }
}
