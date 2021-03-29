package com.jsonTM.claseJson.Controllers;

import com.jsonTM.claseJson.Services.EdadService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/edad")
public class EdadController {
    EdadService edadService = new EdadService();
    @GetMapping("/{day}/{month}/{year}")
    public ResponseEntity calcularEdad(@PathVariable int day, @PathVariable int month, @PathVariable int year){
        return new ResponseEntity(edadService.calcularEdad(day, month, year), HttpStatus.OK);
    }
}
