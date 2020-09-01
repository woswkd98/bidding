FROM openkbs/jdk11-mvn-py3
VOLUME /tmp
ADD target/demo-0.0.1-snapshot.jar app.jar
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java"  ]
