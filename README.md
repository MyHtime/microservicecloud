- 1 SpringCould 与 Dubbo的区别
> - 1.1 通信机制：SpringCould采用Https，Dubbo采用RPC
- 2 微服务是什么？
- 3 微服务与微服务架构
> - 3.1 微服务：强调一个一个小模块、服务（个体）
> - 3.2 微服务架构：架构模式，提倡程序按照（业务）拆分，服务之间相互配合、协调，（整体）
- 4 微服务优缺点
> - 1 优点
> - 4.1.1 高内聚，低耦合
> - 4.1.2 开发简单、效率高
> - 4.1.3 独立
> - 4.1.4 易于与第三方集成
> - 4.1.5 **微服务只是业务逻辑代码，不会和HTML，CSS或其他界面组件混合**
> - 4.1.6 **每个微服务有自己的存储能力，可以有自己的数据库，也可以有统一数据库**
> - 2 缺点
> - 4.2.1 复杂
> - 4.2.2 运维难度
> - 4.2.3 部署依赖
> - 4.2.4 服务间通信成本
> - 4.2.5 数据一致性
> - 4.2.6 系统集成测试
> - 4.2.7 性能监控...
- 5 微服务技术栈（多种技术的集合体和落地维度）
> 服务治理、注册、调用、负载均衡、服务监控
- 6 SpringCloud REST微服务案例
> - 6.1 maven工程管理，父工程（root），子工程（公共模块API，消费者，提供者）
> - 6.2 maven 模块管理
> - 6.3 RestTemplate 使用方法（RestTemplate提供了多种便捷访问远程HTTP服务的方法，是一种简单便捷的访问restful服务类模板，是Spring提供的用于访问Rest服务的客户端模板工具集；<br>
restTemplate.getForObject(url, responseType);<br>
restTemplate.postForObject(url, request, responseType);
<br>String url：rest请求地址;<br>
Object request：请求参数<br>
Class<T> responseType：Http响应转换被转换成的对象类型
> - 6.4 消费者只消费，不提供服务，所以没有service层（暂时不太理解）
- 7 Eureka服务注册与发现
> - 7.1.1 CS架构。Eureka Server作为服务注册功能的服务器，它是服务注册中心。<br>
系统中的其他微服务，使用Eureka的客户端连接到Eureka Server并维持心跳连接。
> - 7.1.2 两大组件：Eureka Server， Eureka Client<br>
**Eureka Server**:服务注册（服务节点启动后，会在Eureka Server中进行注册，这样Eureka Server中的服务注册表中将会存储所有可用服务节点信息，服务节点的信息可以在界面中直观的看到）<br>
**Eureka Client**:java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。应用启动后，将会向ES发送**心跳**（默认周期30s）。如果Eureka Server在多个心跳周期内没有接收到某个节点的心跳。ES将会从服务注册表把这个服务节点移除（默认90s）
> - 7.1.3 三大角色：Eureka Server，Service Provider，Service Consumer<br>
**Eureka Server**:提供服务注册与发现<br>
**Service Provider**：服务提供方将自身服务注册到Eureka，从而使服务消费方能够找到<br>
**Service Consumer**：服务消费方从Eureka获取注册服务列表，从而能够消费服务
> - 7.1.4 引入组件
```xml
<!--eureka-server服务端-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka-server</artifactId>
</dependency>
```
> - 7.1.5 配置文件
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
> - 7.1.6 Java注解
```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer7001_App {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7001_App.class, args);
    }
}
```
> - **7.2 服务注册(Eureka Client)**
> - 7.2.1 引入组件(8001)
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
> - 7.2.2 配置文件(8001)
```yaml
eureka:
  client:  #将客户端服务注册到Eureka服务列表类
    service-url:
      defaultZone: http://localhost:7001/eureka
```
> - 7.2.3 Java注解(8001)

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
> - 7.3 actuator与注册微服务信息完善
> - 微服务实例status相关的修改{
<br>主机名称:PanXin:microservicecloud-dept:8001
<br>ip信息：panxin:8001/info
<br>微服务info内容详细信息-info页面}
> - 7.3.1 主机名称(8001)：服务名称修改
```ymal
  instance:
    instance-id: microservicecloud-dept8001 #自定义服务名称信息
```
> - 7.3.2 访问信息有IP提示(8001)
```ymal
    prefer-ip-address: true                 #访问路径可以显示IP地址
```
> - 7.3.3 微服务info内容详细信息
> - 7.3.3.1 修改8001
```xml
<!--actuator监控信息完善-->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<!--actuator监控信息完善-->
```
> - 7.3.3.2 父工程添加构建build信息
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
> - 7.3.3.3 修改8001
```yaml
info:
  app.name: cn.techpan.springcloud-microservicecloud
  build.artifactId: ${project.artifactId}
  build.version: ${project.version}
```
> - 7.4 **eureka自我保护**
> - 7.4.1 某时刻某一个微服务不可用了，eureka不会立即清理，以旧会对该微服务的信息进行保存
> - 7.4.2 在自我保护模式中，EurekaServer会保护注册表中的信息，不再注销任何服务实例。当它收到的心跳数重新恢复到阈值以上时，该EurekaServer节点就会自动退出自我保护模式（它的设计哲学就是宁可保留错误的服务注册信息，也不盲目注销任何可能健康的服务实例）
> - 7.5 服务发现(not important)
> - 7.5.1 对于在Eureka注册的微服务，可以用服务发现来获得该服务的信息
> - 7.5.2 修改8001的DeptController(添加服务发现接口)
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
> - 7.5.3 修改 8001 DeptProvider8001_App
```java
/**
 * 开启EnableDiscoveryClient支持
 * 服务发现
 */
@EnableDiscoveryClient
```
> - 7.5.4 修改 80 DeptConsumerController
```java
    /**
     * 测试@EnableDiscoveryClient，消费者可以调用服务发现
     */
    @GetMapping("/discovery")
    public Object discovery() {
        return restTemplate.getForObject(REST_URL_PREFIX + "dept/discovery", Object.class);
    }
```
> - 7.6 **Eureka集群**
> - 7.6.1  集群的意思就是将一个应用程序，部署到多台服务器上面
> - 7.6.1.1 [参考1](https://blog.csdn.net/zuoyanyouyan/article/details/81044379)
> - 7.6.1.2 [参考2](https://blog.csdn.net/jiangyu1013/article/details/80417961)
> - 7.6.1.3 [参考3](https://blog.csdn.net/zhou2s_101216/article/details/51707270)
> - 7.6.2 集群步骤
> - 7.6.2.1 maven方式建立多个Eureka Server (port:7001,7002,7003,...)
> - 7.6.2.2 配置Eureka Server的application.yml文件
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
> - 7.6.2.3 编写启动类
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
> - 7.6.2.4 修改微服务提供者的application.yml文件
```yaml
eureka:
  client:  #将客户端服务注册到Eureka服务列表类
    service-url:
      defaultZone: http://eureka700a.com:700a/eureka/,http://eureka700b.com:700b/eureka/
      # a, b 为1,2,3,...
```
> - 7.6.2.5 单台机器需要修改hosts文件来配置集群(非必须)：在hosts文件增加
```
127.0.0.1   eureka7001.com
127.0.0.1   eureka7002.com
127.0.0.1   eureka7003.com
```
> - 7.7 作为服务注册中心，Eureka比Zookeeper好在哪里
> - 7.7.1 著名的CAP理论指出，一个分布式系统不可能同时满足**C(一致性)、A(可用性)和P(分区容错性)**。由于分区容错性P在是分布式系统中必须要保证的，因此我们只能在A和C之间进行权衡。
> - 7.7.2 Zookeeper保证的是CP
> - 7.7.2.1 当向注册中心查询服务列表时，我们可以容忍注册中心返回的是几分钟以前的注册信息，但不能接受服务直接down掉不可用。也就是说，服务注册功能对可用性的要求要高于一致性。但是zk会出现这样一种情况，当master节点因为网络故障与其他节点失去联系时，剩余节点会重新进行leader选举。问题在于，选举leader的时间太长，30 ~ 120s, 且选举期间整个zk集群都是不可用的，这就导致在选举期间注册服务瘫痪。在云部署的环境下，因网络问题使得zk集群失去master节点是较大概率会发生的事，虽然服务能够最终恢复，但是漫长的选举时间导致的注册长期不可用是不能容忍的
> - 7.7.3 Eureka则是AP
> - 7.7.3.1 Eureka各个节点都是平等的，几个节点挂掉不会影响正常节点的工作，剩余的节点依然可以提供注册和查询服务。而Eureka的客户端在向某个Eureka注册或时如果发现连接失败，则会自动切换至其它节点，只要有一台Eureka还在，就能保证注册服务可用(保证可用性)，只不过查到的信息可能不是最新的(不保证强一致性)。
> - 7.7.3.2 除此之外，Eureka还有一种自我保护机制，如果在15分钟内超过85%的节点都没有正常的心跳，那么Eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况:<br>
7.7.3.2.1 Eureka不再从注册列表中移除因为长时间没收到心跳而应该过期的服务 <br>
7.7.3.2.2 Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其它节点上(即保证当前节点依然可用) <br>
7.7.3.2.3 当网络稳定时，当前实例新的注册信息会被同步到其它节点中
> - 7.7.4 因此， Eureka可以很好的应对因网络故障导致部分节点失去联系的情况，而不会像zookeeper那样使整个注册服务瘫痪
> - 8 负载均衡nginx、Ribbon、Feign
> - **9 Ribbon 负载均衡**
> - [Ribbon](https://github.com/Netflix/ribbon/wiki/Getting-Started)
> - 9.1 Spring Cloud Ribbon是基于Netflix Ribbon实现的一套**客户端       负载均衡**的工具
> - 9.1.1 简单的说，Ribbon是Netflix发布的开源项目，主要功能是提供客户端的软件负载均衡算法，将Netflix的中间层服务连接在一起。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer（简称LB）后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随机连接等）去连接这些机器。我们也很容易使用Ribbon实现自定义的负载均衡算法
> - 9.2 LB，即负载均衡(Load Balance)，在微服务或分布式集群中经常用的一种应用。
负载均衡简单的说就是将用户的请求平摊的分配到多个服务上，从而达到系统的HA<br>
常见的负载均衡有软件Nginx，LVS，硬件 F5等。<br>
相应的在中间件，例如：dubbo和SpringCloud中均给我们提供了负载均衡**，SpringCloud的负载均衡算法可以自定义。**<br>
> - 9.2.1 集中式LB(偏硬件)即在服务的消费方和提供方之间使用独立的LB设施(可以是硬件，如F5, 也可以是软件，如nginx), 由该设施负责把访问请求通过某种策略转发至服务的提供方；
> - 9.2.2 进程内LB：将LB逻辑集成到消费方，消费方从服务注册中心获知有哪些地址可用，然后自己再从这些地址中选择出一个合适的服务器。<br>
**Ribbon就属于进程内LB**，它只是一个类库，集成于消费方进程，消费方通过它来获取到服务提供方的地址。
> - 9.3 Ribbon配置初步
> - 9.3.1 80修改pom.xml
```xml
<!--Ribbon-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-eureka</artifactId>
</dependency>
<!--Ribbon相关-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-ribbon</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```
> - 9.3.2 80修改application.xml,追加eureka的服务注册地址
```yaml
eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
```
> - 9.3.3 80开启负载均衡支持
```java
/**
 * Ribbon是客户端的负载均衡。因为80是客户端，通过RestTemplate去消费服务。如想具有负载均衡能，则需要要开启负载均衡，就需要让RestTemplate开启负载均衡@LoadBalanced
 * 在getRestTemplate()追加注解@LoadBalanced
 */
```
> - 9.3.4 80主启动类添加@EnableEurekaClient
> - 9.3.5 80修改客户端访问类DeptConsumerController
```java
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    /**
     * 通过微服务名访问，实现负载均衡
     */
    private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";
```
> - 9.3.6 Ribbon和Eureka整合后Consumer可以直接调用服务而不用再关心地址和端口号(即9.3.5)
> - **9.4 Ribbon负载均衡**
> - 9.4.1 Ribbon在工作时分成两步<br>
第一步先选择 EurekaServer ,它优先选择在同一个区域内负载较少的server.<br>
第二步再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址。<br>
其中Ribbon提供了多种策略：比如轮询、随机和根据响应时间加权。<br>
> - 9.4.2 创建同一个微服务服务提供者(多端口)8001,8002,8003,...
> - 9.4.3 创建每个服务对应的数据库clouddb01,clouddb02,clouddb03,...
> - 9.4.4 配置各自的application.yml
```yaml
# 端口800x
server:
  port: 800x #x端口 1,2,3,...
spring:
  application:
    name: microservicecloud-dept    #对外暴露的微服务名字,三个服务保持一致
eureka:
  client:  #将客户端服务注册到Eureka服务列表类
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
  instance:
    instance-id: microservicecloud-dept800x #自定义服务名称信息(x=1,2,3,...)
    prefer-ip-address: true                 #访问路径可以显示IP地址
```
> - 9.4.5 完成三个相同服务提供者的主启动类
> - 9.4.6 启动多个EurekaServer，启动服务提供者，启动消费者
> - 9.5 **Ribbon核心组件IRule**
> - 9.5.1 IRule：根据特定算法中从服务列表中选取一个要访问的服务
> - 9.5.2 内置算法
> - 9.5.2.1 **RoundRobinRule**(轮询)
> - 9.5.2.2 **RandomRule**(随机)
> - 9.5.2.3 AvailabilityFilteringRule(会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，
还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问)
> - 9.5.2.4 WeightedResponseTimeRule(根据平均响应时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越高。
刚启动时如果统计信息不足，则使用RoundRobinRule策略，等统计信息足够，
会切换到WeightedResponseTimeRule)
> - 9.5.2.5 **RetryRule**(先按照RoundRobinRule的策略获取服务，如果获取服务失败则在指定时间内会进行重试，获取可用的服务)
> - 9.5.2.6 BestAvailableRule(会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务)
> - 9.5.2.7 ZoneAvoidanceRule(默认规则,复合判断server所在区域的性能和server的可用性选择服务器)
> - 9.5.3 配置，在客户端的配置类里注入IRule
```java
/**
 * 设置负载均衡算法
 * 未定时，采用RoundRobinRule-轮询
 */
@Bean
public IRule getIRule() {
    return new RoundRobinRule();
}
```
> - 9.6 **Ribbon自定义**
> - 9.6.1 80主启动类增加注解@RibbonClient(name = "MICROSERVICECLOUD-DEPT", configuration = CustomRibbonRuleConfig.class)
> - 9.6.2 80创建自定义Robbin规则配置类(警告：这个配置类不能放在主程序能扫描的包以及子包下)
```java
import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomRibbonRuleConfig {

    @Bean
    public IRule myRule()
    {
        return new CustomRibbonRule();//Ribbon默认是轮询，我自定义为随机
    }

}
```
> - 9.6.3 80创建自定义Robbin规则类
```java
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;

import java.util.List;

/**
 * 问题：依旧轮询策略，但是加上新需求，每个服务器要求被调用5次。也即
 * 以前是每台机器一次，现在是每台机器5次
 */
public class CustomRibbonRule extends AbstractLoadBalancerRule {

    private int total = 0;    //总共被调用的次数，目前要求每台被调用5次
    private int currentIndex = 0;//当前提供服务的机器号

    public Server choose(ILoadBalancer lb, Object key) {
        if (lb == null) {
            return null;
        }
        Server server = null;

        while (server == null) {
            if (Thread.interrupted()) {
                return null;
            }
            List<Server> upList = lb.getReachableServers();
            List<Server> allList = lb.getAllServers();

            int serverCount = allList.size();
            if (serverCount == 0) {
                return null;
            }

            if (total < 5) {
                server = upList.get(currentIndex);
                total++;
            } else {
                total = 0;
                currentIndex++;
                if (currentIndex >= upList.size()) {
                    currentIndex = 0;
                }
            }
            
            if (server == null) {
                Thread.yield();
                continue;
            }

            if (server.isAlive()) {
                return (server);
            }
            
            server = null;
            Thread.yield();
        }

        return server;

    }

    @Override
    public void initWithNiwsConfig(IClientConfig clientConfig) {

    }

    @Override
    public Server choose(Object key) {
        return choose(getLoadBalancer(), key);
    }
}
```
> - 9.6.4 不能同时出现两种或多种负载均衡算法(故移除了80 ConfigBean.class中的public IRule getIRule()方法)
- 10 Feign负载均衡
> - 10.1 **Feign是一个声明式WebService客户端。**<br>
使用Feign能让编写Web Service客户端更加简单, 它的使用方法是定义一个接口，然后在上面添加注解，同时也支持JAX-RS标准的注解。<br>
Feign也支持可拔插式的编码器和解码器。<br>
Spring Cloud对Feign进行了封装，使其支持了Spring MVC标准注解和HttpMessageConverters。<br>
Feign可以与Eureka和Ribbon组合使用以支持负载均衡。
> - 10.2 Feign是一个声明式的Web服务客户端，使得编写Web服务端变得非常容易（只需要创建一个接口，然后在上面添加注解即可。）
> - 10.2.1 调用微服务可以使用微服务名字，也可以使用接口+注解（feign）
> - 10.3 相对于Ribbon，Feign是面向接口编程，而Ribbon面向的是微服务。
> - 10.3.1 Feign旨在使编写Java Http客户端变得更容易。
前面在使用Ribbon+RestTemplate时，利用RestTemplate对http请求的封装处理，形成了一套模版化的调用方法。
> - 10.3.2 但是在实际开发中，由于对服务依赖的调用可能不止一处，往往**一个接口会被多处调用，所以通常都会针对每个微服务自行封装一些客户端类来包装这些依赖服务的调用。**
> - 10.3.3 所以，Feign在此基础上做了进一步封装，由他来帮助我们定义和实现依赖服务接口的定义。
> - 10.3.4 在Feign的实现下，我们**只需创建一个接口并使用注解的方式来配置它(以前是Dao接口上面标注Mapper注解,现在是一个微服务接口上面标注一个Feign注解即可)**，即可完成对服务提供方的接口绑定，简化了使用Spring cloud Ribbon时，自动封装服务调用客户端的开发量
> - 10.4.1 创建maven module microservicecloud-consumer-dept-feign-80
> - 10.4.2 修改pom文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <parent>
        <artifactId>microservicecloud</artifactId>
        <groupId>cn.techpan.springcloud</groupId>
        <version>0.0.1-SNAPSHOT</version>
    </parent>
    <modelVersion>4.0.0</modelVersion>

    <artifactId>microservicecloud-consumer-dept-feign-80</artifactId>
    <description>消费者feign</description>

    <dependencies>
        <dependency>
            <groupId>cn.techpan.springcloud</groupId>
            <artifactId>microservicecloud-api</artifactId>
            <version>${project.version}</version>
        </dependency>
        <!--eureka相关-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-eureka</artifactId>
        </dependency>
        <!--eureka相关-->
        <!--feign相关-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-feign</artifactId>
        </dependency>
        <!--feign相关-->
        <!--ribbon相关-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-ribbon</artifactId>
        </dependency>
        <!--ribbon相关-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-config</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <!--热部署 修改后立即生效-->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>springloaded</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
        </dependency>
    </dependencies>

</project>
```
> - 10.4.3 创建配置文件application.yml
```yaml
server:
  port: 80

eureka:
  client:
    register-with-eureka: false
    service-url:
      defaultZone: http://eureka7001.com:7001/eureka/,http://eureka7002.com:7002/eureka/,http://eureka7003.com:7003/eureka/
```
> - 10.4.4 便于多处调用，可在api module 创建feign的调用接口（也可以直接在dept-feign-80创feign创建接口）
> - 10.4.4.1 修改api的pom.xml，增加以下内容：
```xml
<dependency>
       <groupId>org.springframework.cloud</groupId>
       <artifactId>spring-cloud-starter-feign</artifactId>
</dependency>
```
> - 10.4.4.2 新建DeptClientService接口
```java
import cn.techpan.springcloud.entity.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 开启FeignClient，完成对服务提供方的接口绑定
 */
@RequestMapping("/dept")
@FeignClient(value = "MICROSERVICECLOUD-DEPT")
public interface DeptClientService {

    /**
     * http://MICROSERVICECLOUD-DEPT/dept/add
     */
    @PostMapping("/add")
    boolean add(Dept dept);

    /**
     * http://MICROSERVICECLOUD-DEPT/dept/get/id
     */
    @GetMapping("/get/{id}")
    Dept get(@PathVariable("id") Long id);

    /**
     * http://MICROSERVICECLOUD-DEPT/dept/list
     */
    @GetMapping("/list")
    List<Dept> list();
}
```
> - 10.4.4.3 分别在（api工程下）执行以下两条maven命名
```bash
mvn clean
mvn install
```
> - 10.4.5 在dept-feign-80创feign创建主启动类
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * 开启EnableFeignClients的支持
 * 扫描所有被@FeignClient标注的接口，并注入到IOC容器中，并可通过@Autowired的获取并调用服务
 * 相对于Ribbon来说，feign更加符合面向接口编程，Ribbon是通过微服务名调用，feign是通过特定注解下的接口，更易于维护
 * 同时，feign整合了Eureka和Ribbon
 * Feign集成了Ribbon
 * 利用Ribbon维护了MicroServiceCloud-Dept的服务列表信息，并且通过轮询实现了客户端的负载均衡。
 * 而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用
 */
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients
public class DeptConsumerFeign80_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptConsumerFeign80_App.class, args);
    }
}
```
> - 10.4.6 在dept-feign-80创feign创建controller类
```java
import cn.techpan.springcloud.entity.Dept;
import cn.techpan.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer/dept")
public class DeptConsumerController {
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    /**
     * Ribbon通过微服务名访问，实现负载均衡
     */
    //private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";

    /**
     * 通过feign接口调用微服务提供者，同时具有负载均衡
     */
    @Autowired
    private DeptClientService deptClientService;

    @PostMapping("/add")
    public boolean add(Dept dept) {
        return this.deptClientService.add(dept);
    }

    @GetMapping("/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return this.deptClientService.get(id);
    }

    @GetMapping("/list")
    public List<Dept> list() {
        return this.deptClientService.list();
    }

}
```
> - 10.5 Feign集成了Ribbon
<br>利用Ribbon维护了MicroServiceCloud-Dept的服务列表信息，并且通过轮询实现了客户端的负载均衡。
<br>而与Ribbon不同的是，通过feign只需要定义服务绑定接口且以声明式的方法，优雅而简单的实现了服务调用
> - 10.6 Feign通过接口的方法调用Rest服务（之前是Ribbon+RestTemplate）
<br>该请求发送给Eureka服务器（http://MICROSERVICECLOUD-DEPT/dept/list）,
通过Feign直接找到服务接口
<br>由于在进行服务调用的时候融合了Ribbon技术，所以也支持负载均衡作用
> - 10.7 同理，可使用修改Ribbon负载均衡算法的方式来修改feign的负载均衡算法
> - 11 Hystrix断路器（熔断器）
> - [Hystrix](https://github.com/Netflix/Hystrix/wiki/How-To-Use)
> - 11.1 SpringCloud服务熔断、降级
> - 11.2 服务雪崩
<br>多个微服务之间调用的时候，假设微服务A调用微服务B和微服务C，微服务B和微服务C又调用其它的微服务，这就是所谓的“扇出”。
<br>如果扇出的链路上某个微服务的调用响应时间过长或者不可用，对微服务A的调用就会占用越来越多的系统资源，进而引起系统崩溃，所谓的“雪崩效应”.
<br><br>
对于高流量的应用来说，单一的后端依赖可能会导致所有服务器上的所有资源都在几秒钟内饱和。
<br>比失败更糟糕的是，这些应用程序还可能导致服务之间的延迟增加，备份队列，线程和其他系统资源紧张，导致整个系统发生更多的级联故障。
<br>这些都表示需要对故障和延迟进行隔离和管理，以便单个依赖关系的失败，不能取消整个应用程序或系统。
> - 11.3 Hystrix是一个用于处理分布式系统的**延迟**和**容错**的开源库。<br>
在分布式系统里，许多依赖不可避免的会调用失败，比如超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，**不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性**。<br>
“断路器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控（类似熔断保险丝），**向调用方返回一个符合预期的、可处理的备选响应（FallBack），而不是长时间的等待或者抛出调用方无法处理的异常**，这样就保证了服务调用方的线程不会被长时间、不必要地占用，从而避免了故障在分布式系统中的蔓延，乃至雪崩。
> - 11.4 功能（服务降级、熔断、限流，接近实时的监控）<br>
备注：一般情况对于服务依赖的保护主要有3中解决方案：<br>
（1）熔断模式：这种模式主要是参考电路熔断，如果一条线路电压过高，保险丝会熔断，防止火灾。放到我们的系统中，如果某个目标服务调用慢或者有大量超时，此时，熔断该服务的调用，对于后续调用请求，不在继续调用目标服务，直接返回，快速释放资源。如果目标服务情况好转则恢复调用。<br>
（2）隔离模式：这种模式就像对系统请求按类型划分成一个个小岛的一样，当某个小岛被火少光了，不会影响到其他的小岛。例如可以对不同类型的请求使用线程池来资源隔离，每种类型的请求互不影响，如果一种类型的请求线程资源耗尽，则对后续的该类型请求直接返回，不再调用后续资源。这种模式使用场景非常多，例如将一个服务拆开，对于重要的服务使用单独服务器来部署，再或者公司最近推广的多中心。<br>
（3）限流模式：上述的熔断模式和隔离模式都属于出错后的容错处理机制，而限流模式则可以称为预防模式。限流模式主要是提前对各个类型的请求设置最高的QPS阈值，若高于设置的阈值则对该请求直接返回，不再调用后续资源。这种模式不能解决服务依赖的问题，只能解决系统整体资源分配问题，因为没有被限流的请求依然有可能造成雪崩效应。
> - 11.5 服务熔断<br>
熔断机制是应对雪崩效应的一种微服务链路保护机制。<br>
当扇出链路的某个微服务不可用或者响应时间太长时，**会进行服务的降级，进而熔断该节点微服务的调用，快速返回"错误"的响应信息。**<br>
当检测到该节点微服务调用响应正常后恢复调用链路。在SpringCloud框架里熔断机制通过Hystrix实现。<br>
Hystrix会监控微服务间调用的状况，当失败的调用到一定阈值，缺省是5秒内20次调用失败就会启动熔断机制。<br>
**熔断机制的注解是@HystrixCommand。**
> - 11.5.1 创建maven子模块microservicecloud-provider-dept-hystrix-8001，microservicecloud-provider-dept-hystrix-8002
> - 11.5.2 复制microservicecloud-provider-dept-8001的pom.xml、application.yml以及java目录下的全部文件
> - 11.5.3 新模块pom.xml分别添加hystrix依赖
```xml
<!--hystrix-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-hystrix</artifactId>
    </dependency>
<!--hystrix-->
```
> - 11.5.3 新模块修改application.yml<br>
```yaml
eureka:
  client:  
    instance-id: microservicecloud-dept800a-hystrix #自定义服务名称信息(hystrix) (a:1,2)
```
> - 11.5.4 修改DeptController.java：使用@HystrixCommand注解，指定服务出现问题时使用的备选返回
```java
 /**
 * 如果某个目标服务调用慢或者有大量超时，此时，熔断该服务的调用，对于后续调用请求，
 * 不在继续调用目标服务，直接返回，快速释放资源。如果目标服务情况好转则恢复调用。
 * HystrixCommand
 */
@GetMapping("/get/{id}")
@HystrixCommand(fallbackMethod = "processHystrixGet")
public Dept get(@PathVariable("id") Long id) {
    Dept dept = deptService.get(id);
    if (null == dept) {
        //假设这里服务出问题了，会通过Hystrix服务熔断的@HystrixCommand注解进入到一个备选方案，而不是一直这里等待或者其他
        //真像AOP
        throw new RuntimeException("该ID：" + id + "没有对应的信息");
    }
    return dept;
}

public Dept processHystrixGet(@PathVariable("id") Long id) {
    return new Dept().setDeptNo(id).setDeptName("该ID：" + id + "没有对应的信息,null --  @HystrixCommand").setDatabaseSource("no record in this database");
}
```
> - 11.5.5 修改各自的主启动类DeptProviderHystrix800a_App(a:1,2):添加@EnableCircuitBreaker注解
```java
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 开启EnableCircuitBreaker功能
 * 支持hystrix服务熔断功能
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
@EnableCircuitBreaker
public class DeptProviderHystrix8001_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptProviderHystrix8001_App.class, args);
    }
}
```
> - 11.5.6 服务熔断：服务端
> - 11.6 服务降级
> - 11.6.1 整体资源快耗尽时，主动将某些服务先关掉，待渡过难关，再开启回来
> - 11.6.2 服务降级处理是在客户端实现完成的，与服务端没有关系
> - 11.6.3 在11.5，服务熔断时，没存在一个调用失败的方式，就会添加一个@HystrixCommand注解的备选方法，导致方法膨胀，提高耦合度。<br>
类似于AOP，去降低耦合:织入 + 异常通知<br>
> - 11.6.4 修改microservicecloud-api工程，根据已经有的DeptClientService接口新建一个实现了FallbackFactory接口的类DeptClientServiceFallbackFactory
```java
import cn.techpan.springcloud.entity.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 对DeptClientService接口类的方法进行服务降级。
 * 避免存在多个方法时，防止服务熔断在服务提供者出现大量使用@HystrixCommand的方法来降低耦合度（类似于AOP的通知）。
 * 这里便是客户端的服务熔断。
 * 假设provider某个关闭了，这里就降级了
 * 也就是hystrix在客服端进行降级，服务端进行熔断
 */
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable cause) {
        return new DeptClientService() {
            @Override
            public boolean add(Dept dept) {
                return false;
            }

            @Override
            public Dept get(Long id) {
                return new Dept().setDeptNo(id).setDeptName("该ID" + id + "没有对应的信息，Consumer客户端提供的降级信息，此刻服务Provider已经关闭").setDatabaseSource("no database here");
            }

            @Override
            public List<Dept> list() {
                return null;
            }
        };
    }
}
```
> - 11.6.5 修改microservicecloud-api工程，DeptClientService接口在注解@FeignClient中添加fallbackFactory属性值 
fallbackFactory:指定fallbackFactory，对这个接口类的方法进行降级。当出现问题时，会去DeptClientServiceFallbackFactory寻找对应的解决方法  
@FeignClient(value = "MICROSERVICECLOUD-DEPT", fallbackFactory = DeptClientServiceFallbackFactory.class)  
> - 11.6.6 microservicecloud-api mvn clean, maven install
> - 11.6.7 修改客户端yml文件
```yaml
feign:
  hystrix:
    # 开启服务熔断
    enabled: true
```
> - 11.6.7 此时服务端provider已经down了，但是我们做了服务降级处理，让客户端在服务端不可用时也会获得提示信息而不会挂起耗死服务器
> - 11.6.8 便于多处调用，可在api module 创建服务fallback方法（也可以直接在dept-feign-80创feign创建接口）
> - 11.6.9 客服端访问某个服务失败（系统紧张时，保证重要服务运行，关闭这个服务，保证正常运行）时，通过客户端的fallback备选方法去通知访问者，就是服务降级。<br>
相对于服务熔断，它们处理的原理相同（类似于AOP的织入和异常返回【fallback】）。<br>
熔断是在服务端多处绑定@HystrixCommand，导致方法膨胀，耦合过高。<br>
降级是在客服端使用一个FallbackFactory<E>(E是某一类绑定的微服务)的实现类绑定一类服务，使得E内出现问题时，会主动到这个实现类寻找对应的fallback备选方法（类似于AOP的通知）去通知访问者，来降低耦合。
> - 11.7 服务熔断VS服务降级
> - 11.7.1 复杂分布式体系结构中的应用程序有数十个依赖关系，每个依赖关系在某些时候将不可避免地失败<br>
分布式系统系统中，微服务之间的调用很频繁。微服务已经解耦,但服务之间的通信总会存在超时或者异常或者失败。<br>
如果存在大量的请求调用失败、超时、异常，会导致系统资源消耗殆尽，产生服务雪崩效应。<br>
为避免服务雪崩，可使用服务熔断、服务降级以及其他Hystrix提供的方案
> - 11.7.2 服务熔断（服务端）【一般是某个服务故障或异常引起，类似于保险丝。当某个异常/错误条件触发时，直接熔断这个服务，而不是一直等到此服务超时】。<br>
> - 11.7.2.1 预先为每个服务方法指定一个fallback备选方法，用@HystrixCommand注解指定。使得服务方法出现错误时，由这个fallback备选方法去做相应的处理。（Spring AOP - 织入 和 异常通知）。<br>
> - 11.7.2.2 这样做的坏处时会有多个fallback备选方法，导致系统耦合度提高，方法膨胀。<br>
> - 11.7.2.3 类似于SpringAOP思想，可以将这些备选方法从业务逻辑抽出，做成切面，用织入方式的来执行（服务降级）。<br>
> - 11.7.4 服务降级（客户端）【服务降级：由于某些因素，为保证整个系统正常运行，会主动关停某些服务。当客户端访问这个服务时，客户端会返回一个预先准备的fallback备选方法，通知客户端，以防止整个系统瘫痪】
> - 11.7.4.1 客户端每个访问方法在服务端都有接口。
> - 11.7.4.2.1 实现一个FallbackFactory<客户端接口>，并在客户端接口用注解声明这个fallbackFactory。告诉客户端这是它的备选方法类。
> - 11.7.4.2.2 当服务端某个服务停止时，客户端访问时。会进入到这个备选方法类寻找对应方法，来通知客户端。完成服务降级
> - 11.7.4.2.3 这样降低了服务端业务方法与备选方法的耦合，使得备选方法单独抽出作为一个FallbackFactory切面，进入客户端，由客户端去调取。
> - 11.7.5 服务熔断、服务降级以及其他Hystrix提供的方案，都是可避免服务雪崩的方案。
> - 11.7.5.1 服务熔断于服务端，某个服务错误（服务关闭）时，主动返回fallback备选方法通知
> - 11.7.5.2 服务降级于客户端，它从整体考虑。某些情况下会关停部分服务，保证系统正常运行。同时对客户端访问这些关停服务时，做了客户端fallback返回