# Etapa de construcción
FROM gradle:8.5-jdk17 AS build
COPY . /app
WORKDIR /app
RUN chmod +x ./gradlew
RUN ./gradlew build -x test

# Etapa de ejecución
FROM eclipse-temurin:17-jdk-alpine
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]