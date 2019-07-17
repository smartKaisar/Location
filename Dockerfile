FROM openjdk:8-jdk-alpine
VOLUME /tmp
# ARG DEPENDENCY=target/dependency
# COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
# COPY ${DEPENDENCY}/META-INF /app/META-INF
# COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY /target/location-0.0.1-SNAPSHOT.jar .
# ENTRYPOINT [ "java","-cp","app:app/lib/*","hello.application" ]
ENTRYPOINT [ "java","-jar","location-0.0.1-SNAPSHOT.jar" ]