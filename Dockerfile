# 1. 找一个带 Maven 和 Java 21 的环境，当作“施工队”
FROM maven:3.9-eclipse-temurin-21 AS build

# 2. 在容器里建个 /app 文件夹工作
WORKDIR /app

# 3. 把本地的 pom.xml 拷进去，先下载依赖（这样以后改代码不用重新下依赖，速度快）
COPY pom.xml .
RUN mvn dependency:go-offline

# 4. 把本地的 src 源码拷进去，然后编译打包成 jar 包
COPY src ./src
RUN mvn clean package -DskipTests

# 5. 找一个只装了 Java 运行环境的干净镜像（体积小）
FROM eclipse-temurin:21-jre-alpine

# 6. 把刚才打包好的 jar 包，从“施工队”那一步拷过来
COPY --from=build /app/target/backend-0.0.1-SNAPSHOT.jar app.jar

# 7. 暴露 8080 端口
EXPOSE 8080

# 8. 启动命令：运行这个 jar 包
ENTRYPOINT ["java", "-jar", "app.jar"]