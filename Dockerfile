FROM openjdk:17
WORKDIR /opt
ENV PORT 9006
EXPOSE 9006
COPY target/*.jar /opt/authservice.jar
ENTRYPOINT exec java -jar authservice.jar