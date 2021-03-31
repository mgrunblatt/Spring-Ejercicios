package com.example.SpringStarWars.controllers;

import com.example.SpringStarWars.services.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PersonajeController {

    @Autowired
    private ServiceImpl service;

    @GetMapping("/name")
    public ResponseEntity buscarPersonajeStarwars(@RequestParam (value = "name", defaultValue = "CadenaDefault") String name){
        return new ResponseEntity(service.buscarPersonajes(name), HttpStatus.OK);
    }


}
