<<<<<<< HEAD
# ---------- build stage ----------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=/app/target/*.jar
COPY --from=build ${JAR_FILE} /app/app.jar

VOLUME /app/logs
ENV JAVA_OPTS=""

EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
=======
# ---------- build stage ----------
FROM maven:3.9.4-eclipse-temurin-21 AS build
WORKDIR /app

# copy only what's needed for dependency resolution first (speeds rebuilds)
COPY pom.xml .
# if you have settings.xml or .m2, mount them or COPY them here
RUN mvn -B -q -DskipTests dependency:go-offline

COPY src ./src
# build the Spring Boot jar
RUN mvn -B -DskipTests package

# ---------- runtime stage ----------
FROM eclipse-temurin:21-jre-jammy
ARG JAR_FILE=/app/target/*.jar
# copy jar from build stage
COPY --from=build ${JAR_FILE} /app/app.jar

# optional: set a place for logs
VOLUME /app/logs

# recommended memory/JVM options can be set through JAVA_OPTS env var
ENV JAVA_OPTS=""

EXPOSE 8081
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar /app/app.jar"]
>>>>>>> e17a6d4c3188685feadbbfdabd3ad1ad4ace9122
