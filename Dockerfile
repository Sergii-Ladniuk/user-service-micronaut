FROM openjdk:14-alpine
COPY build/libs/mn_test-*-all.jar mn_test.jar
EXPOSE 8080
CMD ["java", "-Dcom.sun.management.jmxremote", "-Xmx128m", "-jar", "mn_test.jar"]