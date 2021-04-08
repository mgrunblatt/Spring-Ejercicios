package com.example.Springdesafio.services;

import com.example.Springdesafio.dtos.ClienteDTO;
import com.example.Springdesafio.exceptions.ClientIncompleteDataException;
import com.example.Springdesafio.exceptions.ClientNotFoundException;
import com.example.Springdesafio.exceptions.DuplicateClientException;
import com.example.Springdesafio.repositories.ClienteDTORepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteDTORepositoryImpl clienteDTORepository;

    /**
     * Da de alta cliente siempre y cuando tenga todos los campos completos
     * @param clienteDTO
     * @return
     * @throws DuplicateClientException
     * @throws ClientIncompleteDataException
     */
    @Override
    public ClienteDTO darAltaCliente(ClienteDTO clienteDTO) throws DuplicateClientException, ClientIncompleteDataException {
        List<ClienteDTO> clienteDTOList= clienteDTORepository.loadDataBase();

        for(ClienteDTO cliente : clienteDTOList){
            if(cliente.getDni().equals(clienteDTO.getDni())){
                throw new DuplicateClientException(clienteDTO.getDni());
            }
        }

        if(clienteDTO.getName()==null || clienteDTO.getDni()==null || clienteDTO.getDireccion()==null
                || clienteDTO.getProvincia()==null){
            throw new ClientIncompleteDataException("Campos incompletos, ingrese: nombre, DNI, direccion y provincia");
        }
        clienteDTOList.add(clienteDTO);

        clienteDTORepository.guardarCliente(clienteDTOList);

        return clienteDTO;
    }

    /**
     * Lista todos los clientes o solo los clientes filtrados por provincia.
     * @param provincia
     * @return
     * @throws ClientNotFoundException
     */
    @Override
    public List<ClienteDTO> listarClientes(String provincia) throws ClientNotFoundException {
        if(provincia.equals("")){
            return clienteDTORepository.loadDataBase();
        }else{
            return clienteDTORepository.findClientesByProvincia(provincia);
        }
    }
}
