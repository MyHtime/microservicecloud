package cn.techpan.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * 开启EnableZuulProxy支持，完成zuul路由网关功能
 */
@EnableZuulProxy
@SpringBootApplication
public class SpringCloudZuul9527_App {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudZuul9527_App.class, args);
    }
}
