FROM openjdk:21-jdk-slim
WORKDIR /app
#COPY --from=build /app/target/*.jar app.jar
COPY target/TNSEnergy-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]