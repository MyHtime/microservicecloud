package cn.techpan.springcloud;

import cn.techpan.config.CustomRibbonRuleConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

/**
 * RibbonClient开启自定义负载均衡算法
 * name： 微服务名称
 * configuration： 自定义负载均衡算法配置内
 */
@EnableEurekaClient
@SpringBootApplication
@RibbonClient(name = "MICROSERVICECLOUD-DEPT", configuration = CustomRibbonRuleConfig.class)
public class DeptConsumer80_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptConsumer80_App.class, args);
    }
}
