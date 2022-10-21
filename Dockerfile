FROM gradle:7.5.1-jdk8 as builder
USER root
WORKDIR /builder
ADD . /builder
RUN gradle build -x test --stacktrace
