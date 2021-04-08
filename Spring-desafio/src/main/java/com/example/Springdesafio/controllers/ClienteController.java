package com.example.Springdesafio.controllers;

import com.example.Springdesafio.dtos.ClienteDTO;
import com.example.Springdesafio.exceptions.ClientIncompleteDataException;
import com.example.Springdesafio.exceptions.ClientNotFoundException;
import com.example.Springdesafio.exceptions.DuplicateClientException;
import com.example.Springdesafio.services.ClienteServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v2")
public class ClienteController {

    @Autowired
    private ClienteServiceImpl service;


    /**
     *  Allows to get the whole list without arguments or with filter by provincia.
     *  Example: localhost:8081/api/v2/list-cutomers?provincia=Buenos aires More info in README.md
     * @param provincia
     * @return
     * @throws ClientNotFoundException
     */
    @GetMapping("/list-customers")
    public ResponseEntity listarClientes(@RequestParam (value = "provincia", defaultValue = "")String provincia) throws ClientNotFoundException {
        return new ResponseEntity(service.listarClientes(provincia),HttpStatus.OK);
    }

    /**
     *  Allows create new Customers localhost:8081/api/v2/new-cutomer More info in README.md
     * @param clienteDTO
     * @return
     * @throws DuplicateClientException
     * @throws ClientIncompleteDataException
     */
    @PostMapping("/new-customer")
    public ResponseEntity altaCliente(@RequestBody ClienteDTO clienteDTO) throws DuplicateClientException, ClientIncompleteDataException {
        return new ResponseEntity(service.darAltaCliente(clienteDTO), HttpStatus.OK);
    }


}
