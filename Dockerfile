FROM openjdk:14-jdk-alpine3.10
WORKDIR /app

RUN apk update \
  && apk add curl jq tzdata \
  && cp /usr/share/zoneinfo/America/Sao_Paulo /etc/localtime \
  && echo 'America/Sao_Paulo' > /etc/timezone \
  && apk del tzdata

ENV TZ=America/Sao_Paulo \
  LANG=pt_BR.UTF-8 \
  LANGUAGE=pt_BR.UTF-8 \
  LC_ALL=pt_BR.UTF-8

COPY build/libs/app.jar /app

ENTRYPOINT exec java $JAVA_OPTIONS -XshowSettings:vm -jar /app/app.jar

CMD []
