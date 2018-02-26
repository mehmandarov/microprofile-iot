FROM openjdk:8-jre

EXPOSE 8080
ADD target/timeservice-swarm.jar /opt/timeservice-swarm.jar
ENTRYPOINT java -jar /opt/timeservice-swarm.jar -Djava.net.preferIPv4Stack=true
