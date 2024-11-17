# Spring Boot Microservice with MongoDB, Kafka, and Spring Cloud

This project is a Spring Boot-based microservice architecture with the following features:

- MongoDB integration for database storage
- Eureka for service registry and discovery
- API Gateway for routing and security
- Kafka for message brokering between services
- Feign clients for inter-service communication
- Redis for caching

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Requirements](#requirements)
- [Getting Started](#getting-started)
- [Service Overview](#service-overview)
  - [Eureka Server](#eureka-server)
  - [API Gateway](#api-gateway)
  - [Kafka](#kafka)
  - [Feign Clients](#feign-clients)
  - [Freemarker Template and SMTP](#freemarker-template-and-smtp)
  - [Redis](#redis)
- [Running the Services](#running-the-services)
- [API Endpoints](#api-endpoints)
- [License](#license)

---

## Overview

This is a Spring Boot-based microservice project that integrates with MongoDB for database storage and utilizes several Spring Cloud components to enable a distributed architecture. The services are designed to be scalable, secure, and easy to manage.

### Key Services:

1. **Orders Service**: Handles orders and interacts with Kafka.
2. **Product Service**: Manages product data and integrates with Kafka.
3. **API Gateway**: Centralized routing and security.

---

## Features

- **RESTful API**: Exposes endpoints for various operations like CRUD for orders and products.
- **MongoDB Integration**: Stores and retrieves data from MongoDB for scalability and fast access.
- **Spring Boot**: Uses Spring Boot version 3.3.5 for easy setup and minimal configuration.
- **Swagger UI**: For API documentation and testing.
    - Orders: [Swagger UI for Orders](http://localhost:8070/swagger-ui/index.html)
    - Product: [Swagger UI for Product](http://localhost:8090/swagger-ui/index.html)
- **Security**: API Gateway enforces authentication and authorization via JWT tokens.
- **Feign Clients**: Enables communication between Product and Order services.
- **Kafka Integration**: Product service acts as a producer, while Order service consumes messages from Kafka for order validation and email notifications.
- **Redis**: Used as a caching mechanism to improve API performance.

---

## Requirements

Before running the project, ensure the following are installed:

- **Java 21** or higher
- **MongoDB** (running locally or remotely)
- **Apache Kafka**
- **Maven** (for building and managing dependencies)
- **Redis** (optional, for caching)

---
 ## Install Dependencies
 
kafka Dependencies:
 tar -xzf kafka_2.13-3.9.0.tgz
 cd kafka_2.13-3.9.0
 ./bin/zookeeper-server-start.sh ./config/zookeeper.properties
 ./bin/kafka-server-start.sh ./config/server.properties
./bin/kafka-topics.sh --create --topic order-placed --bootstrap-server localhost:9092
./bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic order-placed

#Running the Spring Boot Microservices
You can start each service individually or use Docker/Container orchestration for scaling.

For example, to run the Order Service:
./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=8070"

Similarly, start the Product Service, Eureka Server, and API Gateway on their respective ports.

Config Server (Not Implemented)
Currently, the Config Server is not present in this repository, but it is a recommended component for centralizing and managing application configurations in a microservices architecture.

Redis (Optional)
Ensure you have Redis running for caching support. For local Redis installation, refer to the Redis documentation.

#Service Overview
**Eureka Server**
Eureka Server is used for service registry and discovery. Microservices register themselves with Eureka, allowing for dynamic communication between services.

**API Gateway**
The API Gateway serves as a central point for routing requests to microservices, enforcing security (JWT authentication), and handling cross-cutting concerns like rate limiting, logging, and authorization.

API Gateway URL: http://localhost:8222
**Kafka**
Kafka is integrated into the Product Service (producer) and Order Service (consumer). The Product Service sends messages to Kafka (e.g., when an order is placed), and the Order Service processes those messages, validating them and triggering actions (e.g., email notifications).

**Feign Clients**
Feign clients are used for communication between Product Service and Order Service, allowing for seamless RESTful communication between the services with minimal boilerplate code.

**Freemarker Template and SMTP**
Freemarker templates are used to design email templates, where ${value} placeholders are replaced with actual data. SMTP is used to send emails based on these templates (e.g., order confirmation emails).

**Redis**
Redis is integrated to improve the performance of APIs by caching frequently accessed data, reducing database load.

**Running the Services Locally**
Ensure all the required services are running before accessing the API. You should have the following endpoints available:

## Service Endpoints

| Service          | URL                                    |
|------------------|----------------------------------------|
| **Eureka Server**| [http://localhost:8282/eureka/](http://localhost:8282/eureka/) |
| **API Gateway**  | [http://localhost:8222](http://localhost:8222) |
| **Product Service**| [http://localhost:8090](http://localhost:8090) |
| **Order Service** | [http://localhost:8070](http://localhost:8070) |



## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Dillibabu390/microservice-mongodb.git
cd your-repo
