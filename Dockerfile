FROM openjdk:17-jdk
WORKDIR /apps
COPY build/libs/*.jar recipe.jar
EXPOSE 8080
CMD ["java","-jar","recipe.jar"]