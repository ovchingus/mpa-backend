FROM openjdk:8

VOLUME /tmp

ADD /build/libs/mpa-0.0.1-SNAPSHOT.jar server.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=docker","-jar", "server.jar"]
