package com.example.Springdesafio.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {

        private int id;
        private List<ResponseArticleDTO> articles;
        private double total;
}
