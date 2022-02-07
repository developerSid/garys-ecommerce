FROM groovy:3.0.9-jdk11 AS groovyImage

ARG USER_ID=1001
ARG GROUP_ID=1001

RUN mkdir -p /opt/high-touch-sign

COPY ./support/development/dbmigrator/migrate-development.sh /tmp
COPY ./support/development/dbmigrator/migrate-test.sh /tmp

RUN chmod a+x /tmp/migrate*.sh

COPY ./ /opt/high-touch-sign
RUN chown -R jenkins:jenkins /opt/high-touch-sign/

WORKDIR /opt/high-touch-sign

USER jenkins
