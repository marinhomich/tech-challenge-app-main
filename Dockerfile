FROM maven:3.9.4-eclipse-temurin-17 as build
WORKDIR /workspace
COPY pom.xml .
COPY src ./src
RUN mvn -B -DskipTests package
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /workspace/target/techchallenge-0.0.1-SNAPSHOT.jar ./app.jar
ADD https://dtdg.co/latest-java-tracer /app/dd-java-agent.jar
ENV JAVA_OPTS="-Xms256m -Xmx512m -javaagent:/app/dd-java-agent.jar"
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
