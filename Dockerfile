####################################################################
# Stage 1 : MAVEN_BUILD
#
# Use a docker image with Maven to build the deliverable
# Build everywhere
####################################################################
#
#FROM maven:3.6.3-openjdk-11 AS MAVEN_BUILD
#MAINTAINER Lena Nörenberg
#COPY pom.xml /build/
#COPY src /build/src/
#WORKDIR /build/
#COPY target /build/target/
#
#
## Copy source files and junit tests from students /src/main/java/tasks into application/build/src/main/java
## ENV git_url = $git_url
#RUN git clone https://l.noerenberg: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@github.com/s80984/se-demo
##RUN mkdir /build/src/main/java/master/sedemo/tasks
#RUN cp ./se-demo/src/main/java/master/sedemo/tasks/* /build/src/main/java/master/sedemo/tasks
#RUN git clone https://s80984:BoYhomK7Puabags2TsMY@gitlab.beuth-hochschule.de/s80984/testrepository
##RUN mkdir /build/src/test/java/master/sedemo/tasks
#RUN cp testrepository/src/test/java/master/sedemo/tasks/Customer* /build/src/test/java/master/sedemo/tasks
#RUN mvn package
#ENV JAR_FILE=/build/target/*.jar
#COPY target/*.jar /build/target/
#ENTRYPOINT ["java","-jar","app.jar"]



#FROM adoptopenjdk/openjdk18:alpine-jre as LAYERS_BUILD
#ARG JAR_FILE=/target/masterthesis-1.8.11.jar
#COPY --from=MAVEN_BUILD ${JAR_FILE} application.jar
#RUN java -Djarmode=layertools -jar application.jar extract

#CMD [ "mvn" , "spring-boot:run" ]
#ARG JAR_FILE=/build/target/*.jar
#COPY ${JAR_FILE} application.jar
#ENTRYPOINT ["java", "-jar", "/application.jar"]


####################################################################
# Stage 2:  LAYERS_BUILD
#
# The deliverable has been built as layers as we asked for it in the
# pom.xml. We will use those layers to build and limit number
# of intermediate layers and leveraging on Docker Cache.
####################################################################

#FROM adoptopenjdk:11-jre-hotspot as LAYERS_BUILD
#WORKDIR application
#COPY --from=MAVEN_BUILD ${JAR_FILE} /application.jar
##RUN #java -Djarmode=layertools -jar application.jar extract
#ENTRYPOINT ["java","-jar","/target/masterthesis-1.8.11.jar"]

####################################################################
# Stage 3: BUILD IMAGE
# 
# We will use those layers to build image limiting number
# of intermediate layers and leveraging on Docker Cache.
####################################################################

#FROM adoptopenjdk:11-jre-hotspot
##FROM adoptopenjdk/openjdk18:alpine-jre
#WORKDIR application
#EXPOSE 8080
#COPY --from=LAYERS_BUILD application/dependencies/ ./
#COPY --from=LAYERS_BUILD application/spring-boot-loader ./
#COPY --from=LAYERS_BUILD application/snapshot-dependencies/ ./
#COPY --from=LAYERS_BUILD application/application/ ./


#ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
#

FROM maven:3.6.3-openjdk-11 AS build
WORKDIR workspace
ADD pom.xml /workspace
COPY src /workspace/src/
COPY target /workspace/target/
ARG GITHUB_USERNAME
ARG GITHUB_TOKEN
ARG GITLAB_USERNAME
ARG GITLAB_TOKEN
ARG GITHUB_URL
#RUN mvn verify clean --fail-never
RUN git clone https://$GITHUB_USERNAME:$GITHUB_TOKEN@github.com/s80984/se-demo
#RUN git clone https://l.noerenberg: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@github.com/s80984/se-demo
#RUN mkdir /build/src/main/java/master/sedemo/tasks
RUN cp ./se-demo/src/main/java/master/sedemo/tasks/* /workspace/src/main/java/master/sedemo/tasks
RUN git clone https://$GITLAB_USERNAME:$GITLAB_TOKEN@gitlab.beuth-hochschule.de/s80984/testrepository

#RUN #git clone https://s80984:BoYhomK7Puabags2TsMY@gitlab.beuth-hochschule.de/s80984/testrepository
##RUN mkdir /build/src/test/java/master/sedemo/tasks
RUN cp testrepository/src/test/java/master/sedemo/tasks/* /workspace/src/test/java/master/sedemo/tasks
COPY src /workspace/src
RUN mvn package -DskipTests=true
EXPOSE 8080
ENTRYPOINT ["java","-jar","/workspace/target/masterthesis-1.8.11.jar"]

#FROM maven:3.6.3-openjdk-11
#COPY --from=build /workspace/start-script.sh start-script.sh
#COPY --from=build /workspace/target/*.jar app.jar
#COPY --from=build /workspace/pom.xml pom.xml
#
#EXPOSE 8080
#ENTRYPOINT ["java","-jar","app.jar"]
#EXPOSE 8080
#ADD ./target/masterthesis-1.8.11.jar /developments/
#ENTRYPOINT ["java","-jar","/developments/masterthesis-1.8.11.jar"]
