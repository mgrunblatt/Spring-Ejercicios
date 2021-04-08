package com.example.Springdesafio.controllers;

import com.example.Springdesafio.dtos.TicketDTO;
import com.example.Springdesafio.exceptions.ArticleDTONotFoudException;
import com.example.Springdesafio.exceptions.ExceededArgumentLimitException;
import com.example.Springdesafio.exceptions.ExceededProductStockException;
import com.example.Springdesafio.services.ArticleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @Autowired
    private ArticleServiceImpl service;

    /**
     * Allows to get the whole list without arguments or get filter by category, or by a combination of two filters
     * and also a order.
     * Example: localhost:8081/api/v1/articles?category=Indumentaria&brand=Taverniti&freeShipping=true&order=0
     * More info in README.md
     * @param params
     * @return
     * @throws IOException
     * @throws ExceededArgumentLimitException
     * @throws ArticleDTONotFoudException
     */
    @GetMapping("/articles")
    public ResponseEntity listarProductos(@RequestParam Map<String,String> params) throws IOException, ExceededArgumentLimitException, ArticleDTONotFoudException {
        return new ResponseEntity(service.listarProductos(params), HttpStatus.OK);
    }


    /**
     * Allows a POST with a TicketDTO object with a list of ResponseArticleDTO inside. More info in README.md
     * @param ticketDTO
     * @return
     * @throws ArticleDTONotFoudException
     * @throws IOException
     * @throws ExceededProductStockException
     */
    @PostMapping("/purchase-request")
    public ResponseEntity realizarCompra(@RequestBody TicketDTO ticketDTO) throws ArticleDTONotFoudException, IOException, ExceededProductStockException {
        return new ResponseEntity(service.procesarCompra(ticketDTO), HttpStatus.OK);
    }



}
