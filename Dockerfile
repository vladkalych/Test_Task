#FROM mysql:8.0.28
#ENV MYSQL_ROOT_PASSWORD=12345678

FROM openjdk:17
COPY ./target/Test_Task-0.0.1-SNAPSHOT.war Test_Task-0.0.1-SNAPSHOT.war
CMD ["java", "-jar", "Test_Task-0.0.1-SNAPSHOT.war"]

