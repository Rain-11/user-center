FROM java:11

VOLUME /tmp

ADD ../target/user-center-0.0.1-SNAPSHOT.jar code-nav-mp-server.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "code-nav-mp-server.jar", "--spring.profiles.active=prod"]