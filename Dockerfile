FROM eclipse-temurin:21-jdk-alpine

RUN apk add --no-cache maven

WORKDIR /app

COPY . .

RUN mvn clean package -DskipTests

EXPOSE 8080

CMD ["java", "-cp", "imogene-gen/target/imogene-gen-1.0-SNAPSHOT.jar:imogene-ga/target/imogene-ga-1.0-SNAPSHOT.jar:imogene-app/target/imogene-app-1.0-SNAPSHOT.jar", "com.backend.BackendApplication"]
