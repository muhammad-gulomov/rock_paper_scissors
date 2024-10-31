FROM openjdk:17

EXPOSE 80

WORKDIR /app

COPY target/rps_app.jar /app/rps_app.jar

ENTRYPOINT ["java", "-jar", "rps_app.jar"]
