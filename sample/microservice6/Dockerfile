FROM maven:3-jdk-8
WORKDIR /sources
# download deps to cache
COPY pom.xml /sources
RUN mvn dependency:resolve

# build code
COPY * /sources
RUN mvn clean install

# make production container
FROM openjdk:8-jdk
WORKDIR /app
#COPY --from=0 /sources/target/microservice6-1.0-SNAPSHOT.jar /app
COPY target/microservice6-1.0-SNAPSHOT.jar app.jar
EXPOSE 8081

#ENTRYPOINT ["java", "-jar", "microservice6-1.0-SNAPSHOT.jar"]
ENTRYPOINT ["java", "-jar", "app.jar"]