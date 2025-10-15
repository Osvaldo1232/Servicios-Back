# Imagen base de Java
FROM openjdk:17-jdk-alpine

# Directorio dentro del contenedor
WORKDIR /app

# Copiar el WAR generado
COPY target/Servicios-0.0.1-SNAPSHOT.war app.war

# Exponer el puerto en el que corre Spring Boot
EXPOSE 8080

# Ejecutar el WAR con java -jar
ENTRYPOINT ["java","-jar","app.war"]
