# ---- Stage 1: build ----
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy *everything* (pom.xml, src, etc.) into the image
COPY . .

# Build the Quarkus app (JVM mode)
RUN mvn -q clean package -DskipTests

# ---- Stage 2: runtime ----
FROM eclipse-temurin:21-jre
WORKDIR /work

# Copy Quarkus runnable app
COPY --from=build /app/target/quarkus-app/lib/ /work/lib/
COPY --from=build /app/target/quarkus-app/*.jar /work/
COPY --from=build /app/target/quarkus-app/app/ /work/app/
COPY --from=build /app/target/quarkus-app/quarkus/ /work/quarkus/

# Render will inject PORT; Quarkus uses it via application.properties
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["sh","-c","java -Dquarkus.http.host=0.0.0.0 -Dquarkus.http.port=${PORT} -jar /work/quarkus-run.jar"]
