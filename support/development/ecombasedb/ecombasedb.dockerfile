FROM docker.io/postgres:13.4-alpine

RUN apk update && \
    apk add pspg dos2unix

COPY postgresql.conf /usr/local/share/postgresql/postgresql.conf.sample
COPY psqlrc /root/.psqlrc