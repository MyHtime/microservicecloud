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
> - 4 **eureka自我保护**
> - 4.1 某时刻某一个微服务不可用了，eureka不会立即清理，以旧会对该微服务的信息进行保存
> - 4.2 在自我保护模式中，EurekaServer会保护注册表中的信息，不再注销任何服务实例。当它收到的心跳数重新恢复到阈值以上时，该EurekaServer节点就会自动退出自我保护模式（它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例）
> - 5 服务发现(not important)
> - 5.1 对于在Eureka注册的微服务，可以用服务发现来获得该服务的信息
> - 5.2 修改8001的DeptController(添加服务发现接口)
```java
    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/discovery")
    public Object discovery() {
        //所有微服务
        List<String> serviceList = discoveryClient.getServices();
        System.out.println("**********" + serviceList);
        //部门微服务
        List<ServiceInstance> instances = discoveryClient.getInstances("MICROSERVICECLOUD-DEPT");
        for (ServiceInstance instance : instances) {
            System.out.println(instance.getServiceId() + "\t" + instance.getHost() + "\t" + instance.getPort() + "\t" + instance.getUri());
        }
        return this.discoveryClient;
    }
```
> - 5.3 修改 8001 DeptProvider8001_App
```java
/**
 * 开启EnableDiscoveryClient支持
 * 服务发现
 */
@EnableDiscoveryClient
```
> - 5.4 修改 80 DeptConsumerController
```java
    /**
     * 测试@EnableDiscoveryClient，消费者可以调用服务发现
     */
    @GetMapping("/discovery")
    public Object discovery() {
        return restTemplate.getForObject(REST_URL_PREFIX + "dept/discovery", Object.class);
    }
```
> - 6 **Eureka集群**
> - 6.1  集群的意思就是将一个应用程序，部署到多台服务器上面
> - 6.1.1 [参考1](https://blog.csdn.net/zuoyanyouyan/article/details/81044379)
> - 6.1.2 [参考2](https://blog.csdn.net/jiangyu1013/article/details/80417961)
> - 6.1.3 [参考3](https://blog.csdn.net/zhou2s_101216/article/details/51707270)
> - 6.2 集群步骤
> - 6.2.1 maven方式建立多个Eureka Server (port:7001,7002,7003,...)
> - 6.2.2 配置Eureka Server的application.yml文件
```yaml
server:
  port: 700x
eureka:
  instance:
    hostname: eureka700x.com
  client:
    register-with-eureka: false
    fetch-registry: false 
    service-url:
      defaultZone: http://eureka700a.com:700a/eureka/,http://eureka700b.com:700c/eureka/
      # a != b != x (1,2,3,...)
```
> - 6.2.3 编写启动类
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 开启EnableEurekaServer支持
 * EurekaServer服务端启动类，接收其他微服务注册进来
 * x 为 1,2,3,...
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer700x_App {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer700x_App.class, args);
    }
}
```
> - 6.2.4 修改微服务提供者的application.yml文件
```yaml
eureka:
  client:  #将客户端服务注册到Eureka服务列表类
    service-url:
      defaultZone: http://eureka700a.com:700a/eureka/,http://eureka700b.com:700b/eureka/
      # a, b 为1,2,3,...
```
> - 6.2.5 单台机器需要修改hosts文件来配置集群(非必须)：在hosts文件增加
```
127.0.0.1   eureka7001.com
127.0.0.1   eureka7002.com
127.0.0.1   eureka7003.com
```
> - 7 作为服务注册中心，Eureka比Zookeeper好在哪里
> - 7.1 著名的CAP理论指出，一个分布式系统不可能同时满足**C(一致性)、A(可用性)和P(分区容错性)**。由于分区容错性P在是分布式系统中必须要保证的，因此我们只能在A和C之间进行权衡。
> - 7.2 Zookeeper保证的是CP
> - 7.2.1 当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求要高于一致性。但是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30 ~ 120s, 且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的
> - 7.3 Eureka则是AP
> - 7.3.1 Eureka各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端在向某个Eureka注册或时如果发现连接失败，则会自动切换至其它节点，只要有一台Eureka还在，就能保证注册服务可用(保证可用性)，只不过查到的信息可能不是最新的(不保证强一致性)。
> - 7.3.2 除此之外，Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况:<br>
7.3.2.1. Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务 <br>
7.3.2.2. Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上(即保证当前节点依然可用) <br>
7.3.2.3. 当网络稳定时，当前实例新的注册信息会被同步到其它节点中
> 7.4 因此， Eureka可以很好的应对因网络故障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪