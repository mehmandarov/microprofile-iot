FROM openjdk:8-jre

EXPOSE 8080
ADD target/weatherservice-swarm.jar /opt/weatherservice-swarm.jar
ENTRYPOINT java -jar /opt/weatherservice-swarm.jar -Djava.net.preferIPv4Stack=true
