FROM amazoncorretto:21.0.6

WORKDIR /app

# 애플리케이션 JAR 파일 복사
COPY build/libs/*.jar app.jar

# 포트 노출
EXPOSE 8080

# 실행 명령
ENTRYPOINT ["java", "-jar", "app.jar"]