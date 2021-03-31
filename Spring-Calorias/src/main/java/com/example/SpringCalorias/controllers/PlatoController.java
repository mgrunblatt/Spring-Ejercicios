package com.example.SpringCalorias.controllers;

import com.example.SpringCalorias.dtos.PlatoDTO;
import com.example.SpringCalorias.repositories.IngredienteDTORepositoryImpl;
import com.example.SpringCalorias.services.Service;
import com.example.SpringCalorias.services.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/plato")
public class PlatoController {

    @Autowired
    private ServiceImpl service;

    @PostMapping("/calorias")
    public ResponseEntity buscarCalorias(@RequestBody PlatoDTO platoDTO){
        return new ResponseEntity(service.caloriasTotales(platoDTO), HttpStatus.OK);
    }

    @PostMapping("/calorias/ingrediente")
    public ResponseEntity caloriasPorIngrediente(@RequestBody PlatoDTO platoDTO){
        return new ResponseEntity(service.caloriasPorIngrediente(platoDTO), HttpStatus.OK);
    }

    @PostMapping("/calorias/mayor")
    public ResponseEntity ingredienteMasCalorico(@RequestBody PlatoDTO platoDTO){
        return new ResponseEntity(service.ingredienteMasCalorico(platoDTO), HttpStatus.OK);
    }
}
