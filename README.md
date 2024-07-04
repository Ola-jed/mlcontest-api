# mlcontest-api

## Requirements
- Java 17
- Docker
- PostgreSQL

## Setup
```bash
git clone https://github.com/Ola-jed/mlcontest-api
cd mlcontest-api
cp example.secret.properties secret.properties

# Create the database in postgres
# Change the configuration values according to your environment

# Run using docker : host-mode
docker build -t mlcontest-api .
docker run --network="host" mlcontest-api

# Or, run using the maven wrapper if you have java 17
./mvnw spring-boot:run

# The api will be running on the port 8080
# Swagger ui is available on the uri [http://localhost:8080/swagger-ui/index.html]
```