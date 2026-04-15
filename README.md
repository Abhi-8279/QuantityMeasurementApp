# Quantity Measurement Microservices Backend

This backend is now split from monolith into 5 Spring Boot services:

1. `eureka-server` (port `8761`)
- Service registry for all microservices

2. `api-gateway` (port `8080`)
- Public API for frontend (`/api/v1/users/**`, `/api/v1/quantities/**`)
- JWT validation and route protection
- Orchestrates calls to internal services using Eureka discovery

3. `auth-service` (port `8081`)
- Register/Login
- JWT token generation
- User lookup by email for gateway

4. `measurement-service` (port `8082`)
- Stateless quantity operations: `COMPARE`, `CONVERT`, `ADD`, `SUBTRACT`, `MULTIPLY`, `DIVIDE`

5. `history-service` (port `8083`)
- Persists operation history in MySQL
- Filtering, counts, details, update, delete APIs

## Run Order

Use separate terminals from backend root `QuantityMeasurementApp`.

```powershell
.\mvn.cmd -pl eureka-server spring-boot:run
.\mvn.cmd -pl auth-service spring-boot:run
.\mvn.cmd -pl measurement-service spring-boot:run
.\mvn.cmd -pl history-service spring-boot:run
.\mvn.cmd -pl api-gateway spring-boot:run
```

Frontend should keep using:

- `http://localhost:8080/api/v1`

## Build

```powershell
.\mvn.cmd -DskipTests compile
```

## Notes

- MySQL DB: `quantitydb`
- Update DB credentials in:
  - `auth-service/src/main/resources/application.properties`
  - `history-service/src/main/resources/application.properties`
- JWT secret is shared between `auth-service` and `api-gateway`
- Eureka URL used by services: `http://localhost:8761/eureka`
- Legacy monolith source folders were removed
