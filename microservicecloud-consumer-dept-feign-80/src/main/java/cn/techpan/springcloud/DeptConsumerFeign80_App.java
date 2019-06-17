package cn.techpan.springcloud;

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
