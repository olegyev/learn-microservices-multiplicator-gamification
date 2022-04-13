FROM openjdk:14
COPY ./target/gamification-0.0.1-SNAPSHOT.jar /usr/src/gamification/
WORKDIR /usr/src/gamification
EXPOSE 8081
CMD ["java", "-jar", "gamification-0.0.1-SNAPSHOT.jar"]