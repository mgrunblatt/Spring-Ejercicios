package com.example.Springdesafio.repositories;

import com.example.Springdesafio.dtos.ClienteDTO;
import com.example.Springdesafio.exceptions.ClientNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClienteDTORepositoryImpl implements ClienteDTORepository{
    @Override
    public boolean guardarCliente(List<ClienteDTO> clienteDTOList) {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:clientes.json");
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if(clienteDTOList!=null)
                objectMapper.writeValue(file, clienteDTOList);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<ClienteDTO> findClientesByProvincia(String provincia) throws ClientNotFoundException {
        List<ClienteDTO> clienteDTOList=new ArrayList<>();
        List<ClienteDTO> clienteDTOS = null;
        clienteDTOS = loadDataBase();
        if (clienteDTOS != null){
            clienteDTOList = clienteDTOS.stream()
                    .filter(clienteDTO -> clienteDTO.getProvincia().equalsIgnoreCase(provincia))
                    .collect(Collectors.toList());
        }
        if(clienteDTOList.size()==0){
            throw new ClientNotFoundException(provincia);
        }

        return clienteDTOList;
    }

    public List<ClienteDTO> loadDataBase() {
        File file = null;
        try {
            file = ResourceUtils.getFile("classpath:clientes.json");
        }catch (Exception e){
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<ClienteDTO>> typeRef = new TypeReference<List<ClienteDTO>>() {};
        List<ClienteDTO> clienteDTOS = null;

        try {
            clienteDTOS = objectMapper.readValue(file, typeRef);

        }catch (Exception e){
            e.printStackTrace();
        }

        return clienteDTOS;

    }
}
