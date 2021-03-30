package com.example.SpringDiploma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AlumnoDTO {

    private String nombre;
    private List<AsignaturaDTO> asignaturas;


}
