package com.jsonTM.claseJson.Services;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.Period;

public class EdadService {
    public int calcularEdad(int day, int month, int year) {
        LocalDate date = LocalDate.of(year, month, day);
        Period period = Period.between(date, LocalDate.now());
        return period.getYears();
    }
}
