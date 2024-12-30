FROM maven:3.9.6-amazoncorretto-17 as builder
WORKDIR /usr/home/app
COPY pom.xml ./
COPY src ./src
RUN mvn clean install

FROM eclipse-temurin:17-jdk-alpine
RUN mkdir -p /usr/opt/service && addgroup --system batchgroup && adduser --system --ingroup batchgroup batch

COPY --from=builder /usr/home/app/target/*.jar /usr/opt/service/app.jar

RUN chown -R batch:batchgroup /usr/opt/service/ && \
    chmod -R 755 /usr/opt/service/ && \
    chown -R batch:batchgroup /mnt/

ENV JAVA_OPTS ''
USER batch
EXPOSE 80

ENTRYPOINT ["sh", "-c", "java  $JAVA_OPTS -jar /usr/opt/service/app.jar"]