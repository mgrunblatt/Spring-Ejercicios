package com.example.Springdesafio.services;

import com.example.Springdesafio.dtos.ArticleDTO;
import com.example.Springdesafio.dtos.TicketDTO;
import com.example.Springdesafio.dtos.TicketResponseDTO;
import com.example.Springdesafio.exceptions.ArticleDTONotFoudException;
import com.example.Springdesafio.exceptions.ExceededArgumentLimitException;
import com.example.Springdesafio.exceptions.ExceededProductStockException;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ArticleService {

    public List<ArticleDTO> listarProductos(Map<String,String> params) throws IOException, ExceededArgumentLimitException, ArticleDTONotFoudException;
    public TicketResponseDTO procesarCompra(TicketDTO ticketDTO) throws ArticleDTONotFoudException, IOException, ExceededProductStockException;
}
