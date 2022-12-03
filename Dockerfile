FROM eclipse-temurin:17-jre-alpine
RUN mkdir /app

WORKDIR /app

ADD ponudniki-api-*.jar /app

EXPOSE 8080

CMD java -jar ponudniki-api-*.jar
#ENTRYPOINT ["java", "-jar", "image-catalog-api-1.0.0-SNAPSHOT.jar"]
#CMD java -jar image-catalog-api-1.0.0-SNAPSHOT.jar
