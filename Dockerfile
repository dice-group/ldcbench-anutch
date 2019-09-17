FROM openjdk:8u151-jdk-alpine

RUN apk update && apk add bash


COPY ./target/ldcbench-nutch-1.0-with-dependencies.jar /data/nutch/ldcbench-nutch-1.0-with-dependencies.jar
COPY ./apache-nutch-1.15 /var/nutch
WORKDIR /data/nutch

VOLUME ["/var/nutch/data"]

CMD java -jar ldcbench-nutch-1.0-with-dependencies.jar