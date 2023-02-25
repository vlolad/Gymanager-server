FROM amazoncorretto:11
COPY target/*.jar app.jar
#ENV JAVA_TOOL_OPTIONS -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8081
ENV TZ Europe/Moscow
ENTRYPOINT ["java","-jar","/app.jar"]