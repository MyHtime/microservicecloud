- 1 SpringCould 与 Dubbo的区别
> - 1 通信机制：SpringCould采用Https，Dubbo采用RPC
- 2 微服务是什么？
- 3 微服务与微服务架构
> - 1 微服务：强调一个一个小模块、服务（个体）
> - 2 微服务架构：架构模式，提倡程序按照（业务）拆分，服务之间相互配合、协调，（整体）
- 4 微服务优缺点
> - 优点
> - 1 高内聚，低耦合
> - 2 开发简单、效率高
> - 3 独立
> - 4 易于与第三方集成
> - 5 **微服务只是业务逻辑代码，不会和HTML，CSS或其他界面组件混合**
> - 6 **每个微服务有自己的存储能力，可以有自己的数据库，也可以有统一数据库**
> - 缺点
> - 1 复杂
> - 2 运维难度
> - 3 部署依赖
> - 4 服务间通信成本
> - 5 数据一致性
> - 6 系统集成测试
> - 7 性能监控...
- 5 微服务技术栈（多种技术的集合体和落地维度）
> 服务治理、注册、调用、负载均衡、服务监控
- 6 SpringCloud REST微服务案例
> - 1 maven工程管理，父工程（root），子工程（公共模块API，消费者，提供者）
> - 2 maven 模块管理
> - 3 RestTemplate 使用方法（RestTemplate提供了多种便捷访问远程HTTP服务的方法，是一种简单便捷的访问restful服务类模板，是Spring提供的用于访问Rest服务的客户端模板工具集；<br>
restTemplate.getForObject(url, responseType);<br>
restTemplate.postForObject(url, request, responseType);
<br>String url：rest请求地址;<br>
Object request：请求参数<br>
Class<T> responseType：Http响应转换被转换成的对象类型
> - 4 消费者只消费，不提供服务，所以没有service层（暂时不太理解）
- 7 Eureka服务注册与发现
> - 1.1 CS架构。Eureka Server作为服务注册功能的服务器，它是服务注册中心。<br>
系统中的其他微服务，使用Eureka的客户端连接到Eureka Server并维持心跳连接。
> - 1.2 两大组件：Eureka Server， Eureka Client<br>
**Eureka Server**:服务注册（服务节点启动后，会在Eureka Server中进行注册，这样Eureka Server中的服务注册表中将会存储所有可用服务节点信息，服务节点的信息可以在界面中直观的看到）<br>
**Eureka Client**:java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。应用启动后，将会向ES发送**心跳**（默认周期30s）。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳。ES将会从服务注册表把这个服务节点移除（默认90s）
> - 1.3 三大角色：Eureka Server，Service Provider，Service Consumer<br>
**Eureka Server**:提供服务注册与发现<br>
**Service Provider**：服务提供方将自身服务注册到Eureka，从而使服务消费方能够找到<br>
**Service Consumer**：服务消费方从Eureka获取注册服务列表，从而能够消费服务
> - 1.4 引入组件
```xml
<!--eureka-server服务端-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>
```
> - 1.5 配置文件
```yaml
server:
  port: 7001
eureka:
  instance:
    hostname: localhost                 #eureka服务端的实例名称
  client: 
    register-with-eureka: false         #false表示不向注册中心注册自己
    fetch-registry: false               #false表示自己端就是注册中心（我的职责就是维护服务实例，并不需要去检索服务）
    service-url: 
      defalutZone: http://${eureka.instance.hostname}:${server.port}/eureka/ #设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址
# eureka 默认将对应服务都注册
```
> - 1.6 Java注解
```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer7001_App {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7001_App.class, args);
    }
}
```
> - **2 服务注册(Eureka Client)**
> - 2.1 引入组件(8001)
```xml
<!--Eureka client-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
<!--Eureka client-->
```
> - 2.2 配置文件(8001)
```yaml
eureka:
  client:  #将客户端服务注册到Eureka服务列表类
    service-url:
      defaultZone: http://localhost:7001/eureka
```
> - 2.3 Java注解(8001)

```java
/**
 * 开启EnableEurekaClient支持
 * 服务启动后会自动注册进eureka服务中
 */
@EnableEurekaClient
@SpringBootApplication
public class DeptProvider8001_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptProvider8001_App.class, args);
    }
}
```
> - 3 actuator与注册微服务信息完善
> - 微服务实例status相关的修改{
<br>主机名称:PanXin:microservicecloud-dept:8001
<br>ip信息：panxin:8001/info
<br>微服务info内容详细信息-info页面}
> - 3.1 主机名称(8001)：服务名称修改
```ymal
  instance:
    instance-id: microservicecloud-dept8001 #自定义服务名称信息
```
> - 3.2 访问信息有IP提示(8001)
```ymal
    prefer-ip-address: true                 #访问路径可以显示IP地址
```
> - 3.3 微服务info内容详细信息
> - 3.3.1 修改8001
```xml
<!--actuator监控信息完善-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<!--actuator监控信息完善-->
```
> - 3.3.2 父工程添加构建build信息
```xml
<build> <!--build构建信息-->
    <finalName>${name}</finalName> <!--finalName：当前工程名称-->
    <resources> <!--resources：资源-->
        <resource> <!--resource：运行访问所有src/main/resource文件夹下的内容-->
            <directory>src/main/resources</directory>
            <filtering>true</filtering> <!--filtering：过滤-->
        </resource>
    </resources>
    <plugins> <!--plugins：插件-->
        <plugin> <!--plugin：解析-->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-resources-plugin</artifactId>
            <configuration>
                <delimiters> <!--delimiters：以$开头，$结尾的(配置)文件信息可以访问、读取,这里$$失效，用${project.version}替代-->
                    <delimit>$</delimit>
                </delimiters>
            </configuration>
        </plugin>
    </plugins>
</build>
```
> - 3.3.3 修改8001
```yaml
info:
  app.name: cn.techpan.springcloud-microservicecloud
  build.artifactId: ${project.artifactId}
  build.version: ${project.version}
```