# Используем официальный образ Maven для сборки
FROM maven:latest AS build

# Копируем pom.xml и src для сборки
COPY pom.xml /myapp/
COPY src /myapp/src/

# Устанавливаем рабочую директорию
WORKDIR /myapp

# Собираем проект
RUN mvn clean package -DskipTests

# Используем официальный образ Tomcat для запуска
#FROM tomcat:latest

# Используем более легкий образ для запуска
FROM openjdk:18-jdk-slim

# Копируем собранный jar-файл из предыдущего этапа
COPY --from=build /myapp/target/InventoryControl-0.0.1-SNAPSHOT.war /myapp/InventoryControl-0.0.1-SNAPSHOT.war

# Указываем команду для запуска приложения
ENTRYPOINT ["sh", "-c", "sleep 30 && java -jar /myapp/InventoryControl-0.0.1-SNAPSHOT.war"]
