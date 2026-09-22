FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY . .

RUN chmod +x gradlew && ./gradlew bootJar -x test && rm -f build/libs/*-plain.jar

CMD ["sh", "-c", "java -jar build/libs/*.jar"]