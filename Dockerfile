FROM adoptopenjdk/openjdk11:jre-11.0.6_10-alpine
COPY target/web-crawler-0.0.1-jar-with-dependencies.jar /web-crawler.jar
CMD ["java", "-jar", "/web-crawler.jar"]
