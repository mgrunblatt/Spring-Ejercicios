package com.example.SpringQualityChallenge.config;

import com.example.SpringQualityChallenge.dtos.ErrorDTO;
import com.example.SpringQualityChallenge.exceptions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(ControllerExceptionHandler.class);


	@ExceptionHandler(HotelDTONotFoundException.class)
	public ResponseEntity<ErrorDTO> hanlerHotelNotFoundException(HotelDTONotFoundException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Hotel not found");
		errorDTO.setDescription("There is no rooms availables between: " + errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(VueloDTONotFoundException.class)
	public ResponseEntity<ErrorDTO> hanlerVueloDTONotFoudException(VueloDTONotFoundException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Vuelo not found");
		errorDTO.setDescription("There is no flights availables between: " + errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(NotExistedDestinyException.class)
	public ResponseEntity<ErrorDTO> hanlerNotExistedDestinException(NotExistedDestinyException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Destiny does not exist");
		errorDTO.setDescription("There is no destination named: " + errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(WrongDateFormatException.class)
	public ResponseEntity<ErrorDTO> hanlerWrongDateFormatException(WrongDateFormatException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Wrong date format");
		errorDTO.setDescription("Date format must be dd/mm/aaaa not-> " + errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(WrongRangeDateException.class)
	public ResponseEntity<ErrorDTO> hanlerWrongRangeDateException(WrongRangeDateException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Wrong range between dates");
		errorDTO.setDescription("DateFrom must be smaller than dateTo-> " + errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(NotMatchingRoomAndPeopleAmountException.class)
	public ResponseEntity<ErrorDTO> hanlerNotMatchingRoomAndPeopleAmountException(NotMatchingRoomAndPeopleAmountException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Not Matching roomType with people amount");
		errorDTO.setDescription(errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(WrongEmailFormatException.class)
	public ResponseEntity<ErrorDTO> hanlerWrongEmailFormatException(WrongEmailFormatException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Wrong Email format");
		errorDTO.setDescription("Email format must be 'example@email.com' not ->"+errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PaymentMethodNotAllowDuesException.class)
	public ResponseEntity<ErrorDTO> hanlerWrongEmailFormatException(PaymentMethodNotAllowDuesException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Only CREDIT allows dues");
		errorDTO.setDescription("The payment type doesn't allows dues "+errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(HotelNotAvailableException.class)
	public ResponseEntity<ErrorDTO> hanlerHotelNotAvailableException(HotelNotAvailableException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Hotel not available");
		errorDTO.setDescription("The hotel doesn't exists or is reserved "+errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(VueloNotAvailableException.class)
	public ResponseEntity<ErrorDTO> hanlerVueloNotAvailableException(VueloNotAvailableException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Flight not available");
		errorDTO.setDescription("The flight doesn't exists or is reserved "+errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExceededArgumentLimitException.class)
	public ResponseEntity<ErrorDTO> hanlerExceededArgumentLimitException(ExceededArgumentLimitException errorException){
		ErrorDTO errorDTO= new ErrorDTO();
		errorDTO.setName("Too many arguments");
		errorDTO.setDescription(errorException.getMessage());
		return new ResponseEntity<>(errorDTO, HttpStatus.BAD_REQUEST);
	}

}