FROM maven:3.6.0-jdk-11-slim AS build
COPY src /home/app/src
COPY pom.xml /home/app
RUN mvn -f /home/app/pom.xml clean package
FROM openjdk:11-jdk-alpine
VOLUME /tmp
# ARG DEPENDENCY=target/dependency
# COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY ${DEPENDENCY}/META-INF /app/META-INF
# COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY --from=build /home/app/target/location-0.0.1-SNAPSHOT.jar .
# ENTRYPOINT [ "java","-cp","app:app/lib/*","hello.application" ]
ENTRYPOINT [ "java","-jar","location-0.0.1-SNAPSHOT.jar" ]
