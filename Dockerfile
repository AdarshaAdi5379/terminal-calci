FROM eclipse-temurin:25-jdk AS build
WORKDIR /src
COPY Main.java Calculator.java ./
RUN javac Main.java Calculator.java && jar cfe calc.jar Main *.class

FROM eclipse-temurin:25-jre
WORKDIR /app
COPY --from=build /src/calc.jar /app/calc.jar
ENTRYPOINT ["java","-jar","/app/calc.jar"]
