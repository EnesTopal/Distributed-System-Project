#FROM ubuntu:latest
#LABEL authors="Enes"
#
#ENTRYPOINT ["top", "-b"]

# Base image olarak OpenJDK kullan
FROM openjdk:17-jdk-slim
#COPY C:/Users/Enes/.jdks/openjdk-23.0.1 /usr/local/java
# Etiket ekle
LABEL authors="Enes"

# JAR dosyasını konteynere kopyala
COPY target/distributedProject-0.0.1-SNAPSHOT.jar /usr/app/

# Çalışma dizinini belirle
WORKDIR /usr/app

# Uygulama portunu belirle
EXPOSE 8080

# Uygulamayı başlat
CMD ["java", "-jar", "distributedProject-0.0.1-SNAPSHOT.jar"]
