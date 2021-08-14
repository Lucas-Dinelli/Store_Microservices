# Store_Microservices
Microservices based system on an example of components for an online store
<br/><br/>

## **Technologies used:**

- Spring Cloud Config
- Eureka Server (Service Discovery)
- Spring Cloud OpenFeign
- Hystrix (Circuit Breaker)
- Spring Cloud Gateway
- Spring Boot Actuator
  <br/><br/>
- Java 11
- Gradle
- JPA
- Hibernate
- Lombok
- Model Mapper
  <br/><br/>
- Junit
- Mockito
- Rest Assured

--------------------------------------------------------------------------------------------------------------------------

## **How to acess?**

- Eureka Server: 
    - http://localhost:9000
    
##

- Product Catalog:
    - http://localhost:8081/v1/product

- Shopping Cart: 
    - http://localhost:8082/v1/cart
    
##

- Hystrix:

    - Link to the dashboard => http://localhost:8082/hystrix

    - After, put in the text field => ```http://localhost:8082/actuator/hystrix.stream```

##
- Gateway:

    - Link to Product Catalog => http://localhost:8080/product-catalog/v1/product

    - Link to Shopping Cart => http://localhost:8080/shopping-cart/v1/cart
    
--------------------------------------------------------------------------------------------------------------------------

## **Examples of insertion (POST):**

- Product Catalog:
```json
  {
    "name": "Product Name", 
    "amount": 100
  }
```
- Shopping Cart:
```json
  {
    "items": 
      [
        {
          "productId": 1, 
          "amount": 3
        },
        {
          "productId": 2, 
          "amount": 5
        }
      ]
  }
```

--------------------------------------------------------------------------------------------------------------------------

_Unit testing of Repository, DTO, Service and Controller Layers_

_Communication handled between microservices_

_Handled and customized exceptions_

_Validation fields and returned messages_

--------------------------------------------------------------------------------------------------------------------------

#### Link to Repository where the configuration files are => https://github.com/Lucas-Dinelli/Store_Architecture_Microservice.git
