# Tmobile Order Calculator
- For given list of itemId, "asOf" dateTime returns for that dateTime

    - Final price on each item
    - List of promotions and discounts applied on each item
    - Total price on the order

#### Support
- support@dev.com

#### Setup

 - Java 1.8
 - Maven 3.3.x

#### Dependencies
 - Dropwizard for Jetty application container, Codahale metrics, HealthCheck 
 - Jersey for RESTful api
 - Spring for dependency injection
 - Lombok for getters, setters, log
 - Jackson for JSON parsing, JSON date string deserialization
 - ProductDetails.json product details cache
 
#### Build
 - from terminal ```mvn clean package```
 - Creates price-calculator.jar

###### Run 
- Run JAR from terminal 
```
java -jar target/price-calculator.jar server src/config.yml
``` 
- from browser see HealthCheck, Metrics ```http://localhost:9001/```

##### Sample Request/Response
- Request
```$xslt
GET /orders/finalPrice?items=201&items=101&asOf=2017-07-01T23:59:59Z
```
- Response
```$xslt
{
  "totalPrice": 293.4625,
  "asOf": "2017-07-01T23:59:59",
  "items": [
    {
      "itemId": 201,
      "finalPrice": 200.5,
      "basePrice": 200.5,
      "discount": null,
      "promotions": {
        "p1": {
          "percentOff": 3.0,
          "weight": 10
        },
        "p2": {
          "percentOff": 6.0,
          "weight": 20
        }
      }
    },
    {
      "itemId": 101,
      "finalPrice": 92.9625,
      "basePrice": 100.5,
      "discount": 1.5,
      "promotions": {
        "p1": {
          "percentOff": 2.5,
          "start": "2017-06-01T00:00:00Z",
          "weight": 10,
          "end": "2017-10-31T23:59:59Z"
        },
        "p2": {
          "percentOff": 5.0,
          "start": "2017-11-01T00:00:00Z",
          "weight": 20,
          "end": "2018-01-31T23:59:59Z"
        }
      }
    }
  ]
}
```

