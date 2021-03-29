package com.jsonTM.claseJson.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HabitacionDTO {
    private String nombre;
    private int ancho;
    private int largo;
    private int metrosCuadrados;

    public int calcularArea(){
        this.setMetrosCuadrados(this.getAncho() * this.getLargo());
        return this.getMetrosCuadrados();
    }
}
