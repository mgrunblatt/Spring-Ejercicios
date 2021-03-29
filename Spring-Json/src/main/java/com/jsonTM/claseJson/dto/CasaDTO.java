package com.jsonTM.claseJson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class CasaDTO {
    private String nombre;
    private String direccion;
    private ArrayList<HabitacionDTO> habitaciones;
    private int precio;
    private int metrosCuadrados;
}
