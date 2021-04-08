package com.example.Springdesafio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDTO {

    private TicketDTO ticket;
    private StatusCodeDTO statusCode;
}
