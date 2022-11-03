FROM debian

ENV LANG en_US.UTF-8
ENV LANGUAGE en_US:en
ENV LC_ALL en_US.UTF-8

RUN apt-get update -y && apt-get install vim mlocate -y

RUN ./gradlew build -x test
RUN groupadd -r myuserapp -g 65536 && useradd --no-log-init -u 65536 -r -g myuserapp myuserapp -m -d /myuserapp
USER myuserapp
WORKDIR /myuserapp
COPY build/libs/forum-0.0.1-SNAPSHOT.jar .

ENTRYPOINT java -jar /myuserapp/forum-0.0.1-SNAPSHOT.jar

ADD --chown=65536:65536 *.jar /myuserapp/main.jar
