
FROM maven:3.8.4-jdk-8  as compile
COPY pom.xml /credit-test-tmp/
COPY src /credit-test-tmp/src/
WORKDIR /credit-test-tmp
RUN mvn clean package

FROM openjdk:8-jdk
CMD ["dir"]
ARG JAR_FILE=/credit-test-tmp/target/*.jar
COPY --from=compile ${JAR_FILE} credit-test.jar
ENTRYPOINT ["java","-jar","/credit-test.jar",  "-webAllowOthers" , "-tcpAllowOthers"]
EXPOSE 8080