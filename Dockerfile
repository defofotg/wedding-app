FROM openjdk:17
MAINTAINER gdefofot (defo.georges@gmail.com)
ADD ./target/Wedding_app.jar /weddingapp/wedding_app.jar
EXPOSE 8090
ENTRYPOINT ["java","-jar","/weddingapp/wedding_app.jar"]
