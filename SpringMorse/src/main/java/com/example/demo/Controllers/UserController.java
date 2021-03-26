package com.example.demo.Controllers;

import com.example.demo.Service.MorseService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @GetMapping("/decifrar")
    public MorseService morseConvertion(@RequestParam(value = "codigo",defaultValue = "CadenaDefault") String codigo){

        MorseService morseService= new MorseService(codigo,MorseService.convertidorMorse(codigo));

        return morseService;
    }
}
