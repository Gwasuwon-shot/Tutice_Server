# open jdk java11 버전 환경
FROM openjdk:11-jdk

# JAR_FILE 변수 정의
ARG JAR_FILE=./build/libs/tutice-0.0.1-SNAPSHOT.jar

# JAR 파일 메인 디렉터리에 복사
COPY ${JAR_FILE} app.jar

# 시스템 진입점 정의
ENTRYPOINT ["java", "-jar", "/app.jar"]
