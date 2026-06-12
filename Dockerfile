# ─── Stage 1: Build ───────────────────────────────────────────────────────────
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom.xml first — allows Docker to cache dependency layer separately.
# Dependencies are only re-downloaded when pom.xml changes, not on every code change.
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Now copy source and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ─── Stage 2: Run ─────────────────────────────────────────────────────────────
# FIX: Use JRE (not JDK) — you don't need a compiler to RUN a jar.
# eclipse-temurin:17-jre is ~100MB smaller than 17-jdk.
FROM eclipse-temurin:17-jre
WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

# FIX: Run as non-root user for basic security hardening
RUN addgroup --system appgroup && adduser --system --ingroup appgroup appuser
USER appuser

ENTRYPOINT ["java", "-jar", "app.jar"]
