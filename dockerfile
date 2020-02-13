FROM openjdk:8
ADD /portly_0.1.jar portly_0.1.jar
EXPOSE 8086
ENTRYPOINT ["java", "-jar", "portly_0.1.jar"]