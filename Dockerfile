FROM tomcat:9.0.27-jdk11

WORKDIR /usr/local/tomcat

COPY target/engima.war webapps/

EXPOSE 8080
CMD [ "catalina.sh", "run" ]