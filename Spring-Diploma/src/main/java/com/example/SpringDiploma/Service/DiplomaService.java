package com.example.SpringDiploma.Service;

import com.example.SpringDiploma.dto.AlumnoDTO;
import com.example.SpringDiploma.dto.AsignaturaDTO;
import com.example.SpringDiploma.dto.DiplomaDTO;

public class DiplomaService {

    public static DiplomaDTO generarDiploma(AlumnoDTO alumnoDTO){
        double promedio=0;
        for(AsignaturaDTO asignaturaDTO: alumnoDTO.getAsignaturas()){
            promedio+=asignaturaDTO.getNota();
        }
        promedio=promedio/alumnoDTO.getAsignaturas().size();
        String mensaje= promedio>9 ? "Excelente promedio! Te felicitamos!" : "Buen trabajo";
        return new DiplomaDTO(mensaje,promedio,alumnoDTO.getNombre());
    }
}
