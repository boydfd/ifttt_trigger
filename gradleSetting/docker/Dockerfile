FROM 10.10.5.46:8082/frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ARG JAR_FILE
COPY entrypoint.sh entrypoint.sh
RUN chmod +x entrypoint.sh
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["./entrypoint.sh"]