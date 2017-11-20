FROM jeanblanchard/java:8
COPY target/spring-boot-rest-example-0.4.0.war spring-boot-rest-example.war
CMD java -jar -Dspring.profiles.active=test spring-boot-rest-example.war
EXPOSE 8080
