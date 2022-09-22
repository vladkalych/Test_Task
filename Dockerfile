FROM openjdk:17
COPY ./target/Test_Task-0.0.1-SNAPSHOT.war Test_Task-0.0.1-SNAPSHOT.war
CMD ["java", "-jar", "Test_Task-0.0.1-SNAPSHOT.war"]

