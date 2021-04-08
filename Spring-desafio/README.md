# Spring-challenge

ENDPOINTS

BaseURL: localhost:8081/api/v1

1- Listado de productos disponibles: GET  /articles

2- Listado de productos filtrados por categoría Ej: GET /articles?category=Indumentaria

3- Un listado que permita la combinación de cualquiera de los filtros: 
GET /articles?category=indumentaria&freeShipping=false

4- Orden Albabético ascendente y descendente : 
GET /articles?category=indumentaria&freeShipping=false&order=0
GET /articles?category=indumentaria&freeShipping=false&order=1

5- Orden por mayor precio: GET /articles?category=indumentaria&freeShipping=false&order=2

6- Orden por menor precio: GET /articles?category=indumentaria&freeShipping=false&order=3

7- Solicitud de compra y devolución de ticket con código de status:
POST: /purchase-request

Payload Ej:

{
    "articles":
    [
        {
            "productId":10,
            "name":"Desmalezadora",
            "brand":"Makita",
            "quantity":1
        },
        {
            "productId":5,
            "name":"Zapatillas Deportivas",
            "brand":"Nike",
            "quantity":2
        }
    ]
}

8- El control de stock se realiza en memoria con lo cual la ExceededStockException
es arrojada si se excede el stock en tiempo de compilación.


BaseURL: localhost:8081/api/v2

1- Dar de alta nuevos clientes: POST /new-customer
Payload ej: 

{
"name": "Carlos",
"dni": "33445566",
"direccion": "Avellaneda 570",
"provincia": "Mendoza"
}

2- Obtener listado completo de todos los clientes: 
GET /list-customers 

3- Obtener listado de clientes filtrados por categoría:
GET /list-customers?provincia=Buenos aires

# Posibles mejoras:

-Articular la posibilidad de asociar una compra con un cliente.
-Agregar tests al proyecto para cotejar los casos de uso y agregar coverage al código.
-Permitir que un usuario administrador pueda crear nuevos artículos en la base de datos.
-Permitir la posibilidad de agregar stock en los artículos existentes.

