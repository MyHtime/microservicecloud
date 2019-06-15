package cn.techpan.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * 开启EnableEurekaClient支持
 * 服务启动后会自动注册进eureka服务中
 * 开启EnableDiscoveryClient支持
 * 服务发现
 */
@EnableEurekaClient
@EnableDiscoveryClient
@SpringBootApplication
public class DeptProvider8001_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptProvider8001_App.class, args);
    }
}
