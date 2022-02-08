FROM docker.io/eclipse-temurin:17-jdk-focal

ARG USER_ID=1001
ARG GROUP_ID=1001

RUN mkdir -p /opt/garys-ecommerce

RUN groupadd --system --gid ${GROUP_ID} jenkins
RUN useradd --system --gid jenkins --uid ${USER_ID} --shell /bin/bash --create-home jenkins
RUN mkdir -p /home/jenkins/.gradle/
RUN echo "org.gradle.daemon=false" >| /home/jenkins/.gradle/gradle.properties
RUN chown -R jenkins:jenkins /home/jenkins/.gradle/

COPY ./support/development/dbmigrator/migrate-development.sh /tmp
COPY ./support/development/dbmigrator/migrate-test.sh /tmp

RUN chmod a+x /tmp/migrate*.sh

COPY ./ /opt/garys-ecommerce
RUN chown -R jenkins:jenkins /opt/garys-ecommerce/

WORKDIR /opt/garys-ecommerce

USER jenkins
