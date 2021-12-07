FROM openjdk:11

#target은 docker build 시 추가할 .jar파일의 위치. gradle의 경우 /build/libs
ARG JAR_FILE=build/libs/*.jar

# .jar file을 app.jar이라는 이름으로 이미지 내부에 복사
COPY ${JAR_FILE} app.jar
COPY src/main/resources/turtleproject-2021-firebase-adminsdk-9tj4v-4d6deda7cb.json src/main/resources/turtleproject-2021-firebase-adminsdk-9tj4v-4d6deda7cb.json

# .jar 파일을 JVM으로 실행
ENTRYPOINT ["java","-jar","/app.jar"]