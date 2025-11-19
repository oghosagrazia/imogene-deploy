FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

RUN mvn clean install -DskipTests

EXPOSE 8080

CMD ["mvn", "-pl", "imogene-gen", "exec:java", "-Dexec.mainClass=com.backend.BackendApplication"]
