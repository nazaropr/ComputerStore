FROM openjdk:20-oracle
ARG JAR_FILE=target/*.jar
COPY ./target/ComputerStore-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]