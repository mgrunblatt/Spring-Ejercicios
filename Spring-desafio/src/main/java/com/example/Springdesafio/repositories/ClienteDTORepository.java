package com.example.Springdesafio.repositories;

import com.example.Springdesafio.dtos.ClienteDTO;
import com.example.Springdesafio.exceptions.ClientNotFoundException;

import java.util.List;

public interface ClienteDTORepository {

    public boolean guardarCliente(List<ClienteDTO> clienteDTOList);
    public List<ClienteDTO> findClientesByProvincia(String provincia) throws ClientNotFoundException;
}
