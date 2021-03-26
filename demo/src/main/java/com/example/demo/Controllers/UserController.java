package com.example.demo.Controllers;

import com.example.demo.Service.RomanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class UserController {

    private final AtomicLong id= new AtomicLong();

    @GetMapping("/convertir")
    public RomanService numeroRomano(@RequestParam(value = "numero",defaultValue = "uno")int numero){

        RomanService romanService = new RomanService(numero,RomanService.convertirANumerosRomanos(numero));

        return romanService;
    }

}
