package com.example.Springdesafio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

        private String name;
        private String dni;
        private String direccion;
        private String provincia;

}
