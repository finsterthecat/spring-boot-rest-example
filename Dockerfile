FROM jeanblanchard/java:8
COPY target/spring-boot-rest-example-0.4.0.war spring-boot-rest-example.war
CMD java -jar -Dspring.profiles.active=test spring-boot-rest-example.war
#Main port
EXPOSE 8090
#Metrics, system health, configurations etc
EXPOSE 8091

