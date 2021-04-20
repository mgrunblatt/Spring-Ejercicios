# Spring-challenge

ENDPOINTS

BaseURL: localhost:8081/api/v1

1- Listado de hoteles disponibles: GET  /hotels

2- Listado de hoteles filtrados por fecha de entrada y de salida Ej: GET /hotels?dateFrom=10/02/2021&dateTo=19/02/2021

3- Listado de hoteles filtrados por fecha de entrada y de salida en un destino específico 
Ej: GET /hotels?dateFrom=10/02/2021&dateTo=19/02/2021&destination=Puerto Iguazu

4- Solicitud de reserva y devolución de booking responde con código de status:
POST: /booking

Payload Ej:

{
"userName" : "seba_gonzalez@unmail.com",
"booking" : {
"dateFrom" : "10/11/2021",
"dateTo" : "20/11/2021",
"destination" : "Buenos Aires",
"hotelCode" : "BH-0002",
"peopleAmount" : "4",
"roomType" : "multiple",
"people" : [
{
"dni" : "12345678",
"name" : "Pepito",
"lastName" : "Gomez",
"birthDate" : "10/11/1982",
"mail" : "pepito@mail.com.ar"
},
{
"dni" : "13345678",
"name" : "Fulanito",
"lastName" : "Gomez",
"birthDate" : "10/11/1983",
"mail" : "fulanitogomez@gmail.com"
}
],
"paymentMethod" : {
"type" : "DEBIT",
"number" : "1234-1234-1234-1234",
"dues" : 1
}
}
}

5- El control de hoteles disponibles se realiza en memoria con lo cual al realizarse una reserva exitosa, el hotel deja de estar disponible en tiempo de compilación


BaseURL: localhost:8081/api/v1

1- Listado de vuelos disponibles: GET  /flights

2- Listado de vuelos filtrados por fecha de ida y de vuelta Ej: GET /flights?dateFrom=10/02/2021&dateTo=19/02/2021

3- Listado de vuelos filtrados por fecha de ida y de veulta con un origen y un destino específicos
Ej: GET /flights?dateFrom=10/02/2021&dateTo=19/02/2021&origin=bogotá&destination=Buenos aires

4- Solicitud de reserva de vuelo y devolución de reservationFlight responde con código de status:
POST: /flight-reservation
Payload ej:

{
"userName" : "seba_gonzalez@unmail.com",
"flightReservation" : {
"dateFrom" : "10/02/2021",
"dateTo" : "15/02/2021",
"origin" : "Buenos Aires",
"destination" : "Iguazzo",
"flightNumber" : "BAPI-1235",
"seats" : "2",
"seatType" : "ECONOMY",
"people" : [
{
"dni" : "12345678",
"name" : "Pepito",
"lastName" : "Gomez",
"birthDate" : "10/11/1982",
"mail" : "pepito@mail.com.ar"
},
{
"dni" : "13345678",
"name" : "Fulanito",
"lastName" : "Gomez",
"birthDate" : "10/11/1983",
"mail" : "fulanitogomez@gmail.com"
}
],
"paymentMethod" : {
"type" : "CREDIT",
"number" : "1234-1234-1234-1234",
"dues" : 6
}
}
}

# Posibles mejoras:

-Agregar Clientes que puedan realizar reservas de hoteles o vuelos.
-Articular Clientes con una reserva en particular.
-Permitir que un usuario administrador pueda crear nuevos hoteles o vuelos en la base de datos.
-Aumentar el coverage de tests.

