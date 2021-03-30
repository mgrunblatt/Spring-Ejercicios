package com.example.SpringDiploma.Controllers;


import com.example.SpringDiploma.Service.DiplomaService;
import com.example.SpringDiploma.dto.AlumnoDTO;
import com.example.SpringDiploma.dto.DiplomaDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/alumno")
public class DiplomaController {

    @PostMapping("/diploma")
    public ResponseEntity obtenerAlumno(@RequestBody AlumnoDTO alumnoDTO){
        return new ResponseEntity(DiplomaService.generarDiploma(alumnoDTO), HttpStatus.OK);
    }
}
