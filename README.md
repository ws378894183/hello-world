# hello-world
一个简单的微服务入门程序
SpringCloud版本Greenwich.SR2
SpringBoot版本2.1.2.RELEASE

## 1 父工程引入通用依赖
```xml
  <project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>test</groupId>
  <artifactId>test</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>pom</packaging>
  <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.1.2.RELEASE</version>
	</parent>
	
  <dependencies>
  	<dependency>
	    <groupId>org.springframework.boot</groupId>
	    <artifactId>spring-boot-starter</artifactId>
	</dependency>
	<dependency>
	  <groupId>org.springframework.boot</groupId>
	  <artifactId>spring-boot-starter-web</artifactId>
	</dependency>
  </dependencies>
  
  <dependencyManagement>
  	<dependencies>
  		<dependency>
		  <groupId>org.springframework.cloud</groupId>
		  <artifactId>spring-cloud-dependencies</artifactId>
		  <version>Greenwich.SR2</version>
		  <type>pom</type>
		  <scope>import</scope>
		</dependency>
  	</dependencies>
  </dependencyManagement>
  <modules>
  	<module>configServer</module>
  	<module>eurekaServer</module>
  	<module>eurekaClient</module>
  </modules>
</project>
```
## 2 eurekaServer服务注册中心
pom文件
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>test</groupId>
    <artifactId>test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </parent>
  <artifactId>eurekaServer</artifactId>
    <dependencies>
  	<dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
   </dependency>
  </dependencies>
</project>
```
yaml文件以及说明
```yaml
server:
  port: 8761
eureka:
  instance:
    instance-id: ${spring.cloud.client.ip-address}:${server.port}
    # 使用ip地址而不是主机名进行注册
    prefer-ip-address: true
    
  # 服务端配置
    server:
    # 是否开启自我保护，开启后，不主动踢出已关停的节点
     enable-self-preservation: false
    # 关停节点清理间隔
     eviction-interval-timer-in-ms: 5000
  
  # 客户端配置  
  client:
    # 是否将eureka自身作为应用注册到eureka注册中心,独立启动时，需设定为false
    registerWithEureka: false
    serviceUrl:
      # 部署到tomcat时，defaultZone需要配置为如下格式 http://host:port/${server.context-path}/eureka
      # 配置访问用户名密码： defaultZone: http://user:password@localhost:8761/eureka
      # 使用集群 defaultzone不能使用localhost作為訪問地址，如果有三个及以上server时，每个都需要向n-1个server进行注册
      # defaultZone: http://admin:1@eureka2:8762/eureka,defaultZone: http://admin:1@eureka3:8763/eureka,。。。。
      # 需要修改hosts文件。向其添加hostname对应关系。如127.0.0.1 eureka1
      defaultZone: http://localhost:8761/eureka
```
启动类上添加@EnableEurekaServer即可
```java
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EurekaServerApplication.class, args);
    }

}
```
## 3 configServer 服务配置中心（本里是读取本地配置文件），同时将该服务注册到服务中心
pom
```pom
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>test</groupId>
    <artifactId>test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </parent>
  <artifactId>configServer</artifactId>
  
  <dependencies>
  	<dependency>
  		<groupId>org.springframework.cloud</groupId>
  		<artifactId>spring-cloud-config-server</artifactId>
  	</dependency>
  	<dependency>
  		<groupId>org.springframework.cloud</groupId>
  		<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
  	</dependency>
  </dependencies>
</project>
```
yml文件
```yml
server:
  port: 8762
spring:
  application:
    name: configServer
  profiles:
   active: native
  cloud:
    config: 
     server:
      native:
        #配置文件存放路径
       search-locations: classpath:/config 
eureka:
  instance:
    prefer-ip-address: true
  client:
    registerWithEureka: true
    fetchRegistry: true
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```
配置文件 eurekaClient-dev.yml
```yml
server:
 port: 8763
eureka:
 client:
  serviceUrl:
   defaultZone: http://localhost:8761/eureka
 instance:
  appname: eurekaCient
  #instance-id: ${spring.cloud.client.ip-address}:${server.port}
  prefer-ip-address: true
```
主类添加相应注解
```java
@SpringBootApplication
@EnableEurekaClient
@EnableConfigServer
public class ConfigServerApplication {
    public static void main(final String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
```
## 3 eurekaClient 服务消费者，去配置中心configServer读取配置
```xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <parent>
    <groupId>test</groupId>
    <artifactId>test</artifactId>
    <version>0.0.1-SNAPSHOT</version>
  </parent>
  <artifactId>eurekaClient</artifactId>
  
  <dependencies>
	  <dependency>
	    <groupId>org.springframework.cloud</groupId>
	    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework.cloud</groupId>
		<artifactId>spring-cloud-config-client</artifactId>
	</dependency>
  </dependencies>
</project>
```
bootstrap.yml 必须是这个名字，虽然和某个前端库重名
```yml
spring:
 application:
   name: eurekaClient
 cloud:
   config:
     name: eurekaClient  #文件前缀名称
     profile: dev #服务环境名称  例如 {name}-{profile} = service-gateway-dev.yml
     uri: http://localhost:8762
```
注意config name和profile要和配置中心存放的配置文件名称一致

主类上只需加上@EnableEurekaClient注解即可，用于注册到服务中心
```java
@SpringBootApplication
@EnableEurekaClient
@RestController
public class EurekaClientApplication {

    public static void main(final String[] args) {
        SpringApplication.run(EurekaClientApplication.class, args);
    }
    @Value("${server.port}")
    private String port;
    
    @GetMapping("/client/port")
    public String getPort() {
    	return this.port;
    }
}
```
这里加@RestController注解，只是为了测试一下是否能取到配置文件中的值
