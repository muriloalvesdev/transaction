FROM openjdk:17-oracle
VOLUME /tmp
EXPOSE 8081
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/transaction-0.0.1-SNAPSHOT.jar /app/transaction.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-jar", "/app/transaction.jar"]
