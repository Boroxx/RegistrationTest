FROM gradle:latest AS BUILD
WORKDIR /build
COPY . .
RUN gradle bootJar
RUN cp build/libs/registrationTest-0.0.1-SNAPSHOT.jar app-portly.jar

FROM openjdk:8-jre-alpine
WORKDIR /app
COPY wait-for-it.sh .
RUN chmod +x wait-for-it.sh
COPY --from=BUILD /build/app-portly.jar app.jar
