FROM ubuntu:20.04 
 FROM openjdk:11
   RUN javac -version 
  WORKDIR / 
 COPY ./* ./ 
 RUN javac lexer.java 
