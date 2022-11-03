FROM debian

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN apt-get update -y && apt-get install curl -y

RUN groupadd -r myuserapp -g 65536 && useradd --no-log-init -u 65536 -r -g myuserapp myuserapp -m -d /myuserapp
USER myuserapp
WORKDIR /myuserapp
COPY main.jar .

ENTRYPOINT java -jar /myuserapp/main.jar

ADD --chown=65536:65536 *.jar /myuserapp/main.jar
