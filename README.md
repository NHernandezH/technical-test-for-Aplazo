# springboot-credit-test

This microservice calculates and generates the list of payments with the simple interest of a credit that must be paid in n installments and weekly considering the following formula:

Simple Interest = P * R * T 

where:

* P = Principal amount 
* R = monthly interest rate 
* T = Term of credit, in monts

## Requirements

For building and running the application you need:

- [JDK 1.8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Maven 3](https://maven.apache.org)
## Tests

To run the tests execute the following command in the root of the project:
```
mvn test
```
You can validate the coverage of the tests by building the project, then go to the /target/site/jacoco/ directory and open the index.html file

## Build and Run 

To build the .jar execute the following command in the root of the project:
```
mvn clean package
```
Once the jar is created, go to the /target directory and run the .jar with the following command:
```
java -jar credit-test-0.0.1-SNAPSHOT.jar
```
## Run whit docker

To run the application using docker, first compile the image executing the following command in the root of the project:

```
sudo docker build -t credit-test .
```
Then, raise the container with the following command:

```
sudo docker run -p 8080:8080 credit-test
```

# REST API

This microservice only contains the following api.

## Calculate payments

### Request

`POST /api/v1/credit/simple-interest/payments`
    
    Body

    {
    "amount": 1000,
    "terms": 1,
    "rate":10
    }

### Response when is ok

    HTTP/1.1 200 OK
    
    Body

    [
        {
            "payment_number": 1,
            "amount": 275.0,
            "payment_date": "2022-02-27T18:54:48.014-06:00"
        },
        {
            "payment_number": 2,
            "amount": 275.0,
            "payment_date": "2022-03-06T18:54:48.014-06:00"
        },
        {
            "payment_number": 3,
            "amount": 275.0,
            "payment_date": "2022-03-13T18:54:48.014-06:00"
        },
        {
            "payment_number": 4,
            "amount": 275.0,
            "payment_date": "2022-03-20T18:54:48.014-06:00"
        }
    ]   

### Response when is bad request
    
    HTTP/1.1 400 BAD REQUEST

    {
        "status": 400,
        "error": "Bad request",
        "message": "amount is required , terms is required , rate is required"
    }

# H2 In Memory Database

To consult the requests and responses registered in the database, you can use the H2 console, opening the following url:

    http://localhost:8080/h2-console

Add the following value in the "JDB URL" field and click connect.

    jdbc:h2:mem:test