# Smart Attendance System – Setup Guide

Welcome to the Smart Attendance System!  
This guide will help you configure, set up dependencies, and run all microservices in the correct order.

---

## 1. Fill Required Sensitive Details

Before starting, **edit all `application.properties` files** in each service folder and fill in the required sensitive fields (such as passwords, secrets, etc.) that are currently set as:

```
{enter details/hidden for EC purpose}
```

Replace these placeholders with your actual credentials and secrets.

---

## 2. Prerequisite: Start Required Containers

Run the following commands to start all required dependencies using Docker:

### Start Redis

```sh
docker run -d --name SAS-redis -p 6379:6379 redis:7
```

### Start Zookeeper (needed by Kafka)

```sh
docker run -d --name SAS-zookeeper -p 2181:2181 \
  -e ZOOKEEPER_CLIENT_PORT=2181 \
  -e ZOOKEEPER_TICK_TIME=2000 \
  confluentinc/cp-zookeeper:7.5.0
```

### Start Kafka

```sh
docker run -d --name SAS-kafka -p 9092:9092 \
  --link SAS-zookeeper \
  -e KAFKA_BROKER_ID=1 \
  -e KAFKA_ZOOKEEPER_CONNECT=SAS-zookeeper:2181 \
  -e KAFKA_LISTENERS=PLAINTEXT://0.0.0.0:9092 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://localhost:9092 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  confluentinc/cp-kafka:7.5.0
```

### Start Zipkin

```sh
docker run -d --name SAS-zipkin -p 9411:9411 openzipkin/zipkin:latest
```

### Verify Containers Are Running

```sh
docker ps
```

### Create Kafka Topic

```sh
docker exec -it SAS-kafka kafka-topics --create \
  --topic low_attendance_emails \
  --bootstrap-server localhost:9092 \
  --partitions 3 \
  --replication-factor 1
```

---

## 3. Service Startup Order

Start the microservices in the following order for proper registration and monitoring:

1. **Eureka Service** (Service Discovery)
2. **Admin Service** (Spring Boot Admin)
3. **Classroom Service**
4. **Student Service**
5. **API Gateway Service**

Each service can be started using Maven:

```sh
mvn spring-boot:run
```

---

## 4. Service Endpoints

- **Redis:** `localhost:6379`
- **Kafka:** `localhost:9092`
- **Zipkin:** [http://localhost:9411](http://localhost:9411)
- **Kafka Topic:** `low_attendance_emails`

---

## 5. Notes

- Ensure Docker is installed and running.
- Fill all sensitive fields before starting services.
- For troubleshooting, check logs and verify all containers are running.

---

## 🚀 You’re ready to go!

Enjoy using the Smart Attendance System!
