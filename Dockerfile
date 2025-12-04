# ============================
# STAGE 1: Build con Maven
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

COPY pom.xml .
RUN mvn -B dependency:go-offline

COPY src ./src
RUN mvn -B clean package -DskipTests


# ============================
# STAGE 2: Imagen ligera
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=builder /app/target/*.jar app.jar

# PUERTO CORRECTO
EXPOSE 8081

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
