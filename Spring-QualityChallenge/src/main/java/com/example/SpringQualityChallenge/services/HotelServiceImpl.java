package com.example.SpringQualityChallenge.services;

import com.example.SpringQualityChallenge.dtos.*;
import com.example.SpringQualityChallenge.exceptions.*;
import com.example.SpringQualityChallenge.repositories.HotelDTORepositoryImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@org.springframework.stereotype.Service
public class HotelServiceImpl implements HotelService {

    private HotelDTORepositoryImpl hotelDTORepository;

    public HotelServiceImpl(HotelDTORepositoryImpl hotelDTORepository) {
        this.hotelDTORepository = hotelDTORepository;
    }

    private double totalPurchase=0;
    private int ticketId=0;

    /**
     * Busca los distintos listados de hoteles en función de la cantidad de parámetros que lleguen del controller
     * @param params
     * @return
     * @throws IOException
     * @throws ExceededArgumentLimitException
     * @throws HotelDTONotFoundException
     * @throws NotExistedDestinyException
     * @throws WrongDateFormatException
     */
    @Override
    public List<HotelDTO> listarHoteles(Map<String,String> params) throws IOException, ExceededArgumentLimitException, HotelDTONotFoundException, NotExistedDestinyException, WrongDateFormatException {
        List<HotelDTO> hotelDTOS=hotelDTORepository.loadDataBase();

        if(params.isEmpty())
            return hotelDTOS;
        else {
            switch (params.size()) {
                case 2: hotelDTOS = hotelDTORepository.findHotelByDates(hotelDTOS, params);
                    break;
                case 3: hotelDTOS = hotelDTORepository.findHotelByDatesAndDestination(hotelDTOS, params);
                    break;
                default:
                    throw new ExceededArgumentLimitException("too many arguments. Max: 3 -> (2 dates, 1 destination)");
            }
        }
        return hotelDTOS;
    }

    /**
     * Procesa la reserva de hotel siempre que el hotel esté disponible
     * @param bookingDTO
     * @return
     * @throws HotelDTONotFoundException
     * @throws IOException
     * @throws HotelNotAvailableException
     */
    @Override
    public BookingResponseDTO procesarReserva(BookingDTO bookingDTO) throws HotelDTONotFoundException, IOException, HotelNotAvailableException {

        BookingResponseDTO bookingResponseDTO= new BookingResponseDTO();

        bookingResponseDTO.setUserName(bookingDTO.getUserName());
        int cantidadDias = bookingDTO.getBooking().getDateTo().getDayOfMonth()
                -bookingDTO.getBooking().getDateFrom().getDayOfMonth();
        List<HotelDTO> hotelDTOS= hotelDTORepository.findHotelByCode(bookingDTO.getBooking().getHotelCode());
        if(hotelDTOS.isEmpty()){
            throw new HotelNotAvailableException(bookingDTO.getBooking().getHotelCode());
        }
        HotelDTO hotelDTO = hotelDTOS.get(0);
        double amount = hotelDTO.getNightPrice()*cantidadDias*bookingDTO.getBooking().getPeopleAmount();
        bookingResponseDTO.setAmount(amount);

        BookingResponse bookingResponse= new BookingResponse();

        if(bookingDTO.getBooking().getPaymentMethod().getType().equals("CREDIT")){
            switch (bookingDTO.getBooking().getPaymentMethod().getDues()){
                case 1:
                case 2:
                case 3:
                    bookingResponseDTO.setInterest(5);
                        bookingResponseDTO.setTotal(amount*1.05);
                    break;
                case 4:
                case 5:
                case 6:
                    bookingResponseDTO.setInterest(10);
                    bookingResponseDTO.setTotal(amount*1.1);
                    break;
            }

        }else{
            bookingResponseDTO.setTotal(amount);
        }

        bookingResponse = bookingResponseSetData(bookingDTO,bookingResponse);

        bookingResponseDTO.setBooking(bookingResponse);
        bookingResponseDTO.setStatusCode(new StatusCodeDTO(200,"El proceso terminó satisfactoriamente"));
        hotelDTO.setReserved("SI");

        return bookingResponseDTO;
    }

    /**
     * Setea los datos del BookingResponse si es que la reserva se hizo correctamente
     * @param bookingDTO
     * @param bookingResponse
     * @return
     */
    public BookingResponse bookingResponseSetData(BookingDTO bookingDTO, BookingResponse bookingResponse){
        bookingResponse.setDateFrom(bookingDTO.getBooking().getDateFrom());
        bookingResponse.setDateTo(bookingDTO.getBooking().getDateTo());
        bookingResponse.setDestination(bookingDTO.getBooking().getDestination());
        bookingResponse.setHotelCode(bookingDTO.getBooking().getHotelCode());
        bookingResponse.setPeopleAmount(bookingDTO.getBooking().getPeopleAmount());
        bookingResponse.setRoomType(bookingDTO.getBooking().getRoomType());
        bookingResponse.setPeople(bookingDTO.getBooking().getPeople());

        return bookingResponse;
    }


}
