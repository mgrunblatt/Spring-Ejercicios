package com.example.SpringDiploma.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DiplomaDTO {

    private String mensaje;
    private double promedio;
    private String alumno;

}
