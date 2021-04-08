package com.example.Springdesafio.services;

import com.example.Springdesafio.dtos.ClienteDTO;
import com.example.Springdesafio.dtos.TicketDTO;
import com.example.Springdesafio.dtos.TicketResponseDTO;
import com.example.Springdesafio.exceptions.*;

import java.io.IOException;
import java.util.List;

public interface ClienteService {

    public ClienteDTO darAltaCliente(ClienteDTO clienteDTO) throws DuplicateClientException, ClientIncompleteDataException;
    public List<ClienteDTO> listarClientes(String provincia) throws ClientNotFoundException;
}
