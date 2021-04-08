package com.example.Springdesafio.config;

import com.example.Springdesafio.dtos.ErrorDTO;
import com.example.Springdesafio.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);


	@ExceptionHandler(ArticleDTONotFoudException.class)
	public ResponseEntity<ErrorDTO> hanlerDuplicateClientException(ArticleDTONotFoudException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Artículo no encontrado");
		errorDTO.setDescription("El Artículo: " + errorException.getMessage()+" no ha sido encontrado");
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DuplicateClientException.class)
	public ResponseEntity<ErrorDTO> hanlerDuplicateClientException(DuplicateClientException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Cliente ya existe en la base de datos");
		errorDTO.setDescription("El cliente DNI: " + errorException.getMessage()+" ya se encuentra registrado");
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ExceededProductStockException.class)
	public ResponseEntity<ErrorDTO> hanlerExceededProductStockException(ExceededProductStockException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("No hay suficiente stock");
		errorDTO.setDescription("Puede realizar la compra por un máximo de " + errorException.getMessage()+"");
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(ClientIncompleteDataException.class)
	public ResponseEntity<ErrorDTO> hanlerClientIncompleteDataException(ClientIncompleteDataException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("No todos los datos están completos");
		errorDTO.setDescription(errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ClientNotFoundException.class)
	public ResponseEntity<ErrorDTO> hanlerClientNotFoundException(ClientNotFoundException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("No se encontró ningún cliente en la provincia elegida");
		errorDTO.setDescription(errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExceededArgumentLimitException.class)
	public ResponseEntity<ErrorDTO> hanlerClientNotFoundException(ExceededArgumentLimitException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Demasiados argumentos");
		errorDTO.setDescription(errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

}