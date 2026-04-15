FROM maven:3.9.9-eclipse-temurin-17 AS build

WORKDIR /workspace

COPY pom.xml ./
COPY api-gateway/pom.xml api-gateway/pom.xml
COPY auth-service/pom.xml auth-service/pom.xml
COPY measurement-service/pom.xml measurement-service/pom.xml
COPY history-service/pom.xml history-service/pom.xml
COPY eureka-server/pom.xml eureka-server/pom.xml

RUN mvn -q -DskipTests dependency:go-offline

COPY api-gateway api-gateway
COPY auth-service auth-service
COPY measurement-service measurement-service
COPY history-service history-service
COPY eureka-server eureka-server

RUN mvn -q -DskipTests package

FROM eclipse-temurin:17-jre-jammy

WORKDIR /app

COPY --from=build /workspace/eureka-server/target/eureka-server-1.0.0.jar /app/eureka-server.jar
COPY --from=build /workspace/api-gateway/target/api-gateway-1.0.0.jar /app/api-gateway.jar
COPY --from=build /workspace/auth-service/target/auth-service-1.0.0.jar /app/auth-service.jar
COPY --from=build /workspace/measurement-service/target/measurement-service-1.0.0.jar /app/measurement-service.jar
COPY --from=build /workspace/history-service/target/history-service-1.0.0.jar /app/history-service.jar

EXPOSE 8080 8081 8082 8083 8761

CMD ["java", "-jar", "/app/api-gateway.jar"]
