# ============================
# STAGE 1: Build con Maven
# ============================
FROM maven:3.9.9-eclipse-temurin-21 AS builder

WORKDIR /app

# Copiar pom y descargar dependencias
COPY pom.xml .
RUN mvn -B dependency:go-offline

# Copiar el código fuente
COPY src ./src

# Compilar proyecto
RUN mvn -B clean package -DskipTests


# ============================
# STAGE 2: Imagen ligera
# ============================
FROM eclipse-temurin:21-jre

WORKDIR /app

# Copiar jar generado
COPY --from=builder /app/target/*.jar app.jar

# Puerto expuesto
EXPOSE 8080

# Comando de inicio
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
