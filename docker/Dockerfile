FROM openjdk:8-jre-alpine

COPY target/testcontainers-demo-0.0.1-SNAPSHOT.jar app.jar
CMD java $JAVA_OPTS -jar app.jar --spring.profiles.active=$PROFILE