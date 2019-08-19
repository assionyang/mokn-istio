FROM java:8-jdk-alpine

COPY ./target/istio-service-1.0.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch istio-service-1.0.jar'

ENTRYPOINT ["java", "-jar", "istio-service-1.0.jar"]