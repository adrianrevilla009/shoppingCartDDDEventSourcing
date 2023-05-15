# shoppingCartDDDEventSourcing
Shopping cart spring boot app with DDD architecture and event sourcing


Pasos para las pruebas
1. Crear customer
   http://localhost:8080/customers/
2. Crear cart para customer
   http://localhost:8080/customers/6606e5c9-b5c6-4341-8b5a-1d5a9e84e375/cart
3. Crear producto
   localhost:8080/products/
4. Añadir producto para el cart
   localhost:8080/carts/693134a6-e900-44f0-bd37-f0cdfba46c4a/product/fe50856c-6a03-4420-bd0e-fe246f8754b2
5. Listar carts
   localhost:8080/carts
6. Listar productos eliminados de un cart
   localhost:8080/carts/693134a6-e900-44f0-bd37-f0cdfba46c4a/deletedProducts
7. Eliminar producto en un cart
   localhost:8080/carts/693134a6-e900-44f0-bd37-f0cdfba46c4a/product/fe50856c-6a03-4420-bd0e-fe246f8754b2?quantity=1
8. Listar productos eliminados para el cart
   localhost:8080/carts/693134a6-e900-44f0-bd37-f0cdfba46c4a/deletedProducts