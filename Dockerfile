FROM openjdk:21
LABEL authors="janho"

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from your local machine to the container
COPY ./target/portfoliopilot-0.9.jar /app/Simulators-0.9.jar