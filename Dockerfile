FROM eclipse-temurin:25-jdk AS build

WORKDIR /app
COPY . .
RUN sh mvnw -B -DskipTests package

FROM eclipse-temurin:25-jre

WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
USER 10001:10001
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
