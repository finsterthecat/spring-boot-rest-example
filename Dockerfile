FROM jeanblanchard/java:8
COPY target/spring-boot-rest-example-0.4.0.war spring-boot-rest-example.war
CMD java -jar -Dspring.profiles.active=test spring-boot-rest-example.war
<<<<<<< HEAD
#Main port
EXPOSE 8090
#Metrics, system health, configurations etc
EXPOSE 8091
=======
EXPOSE 8080
>>>>>>> 827c2e94945e4625353b7b01ec4af7e0025a9b25
