FROM maven:3.8.4-openjdk-17-slim AS buildstage

ENV  SPRING_DATASOURCE_URL='jdbc:postgresql://wedding-app-postgres-1:5432/postgres' \
    SPRING_DATASOURCE_USERNAME=postgres \
    SPRING_DATASOURCE_PASSWORD=mysecretpassword

WORKDIR /app

COPY . .

RUN mvn package

FROM openjdk:17 AS runstage

COPY --from=buildstage /app/target/Wedding_app.jar /weddingapp/wedding_app.jar

EXPOSE 8080 

ENTRYPOINT ["java", "-jar", "/weddingapp/wedding_app.jar"]
