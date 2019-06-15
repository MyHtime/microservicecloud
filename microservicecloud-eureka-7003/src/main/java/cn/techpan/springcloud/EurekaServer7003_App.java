package cn.techpan.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 开启EnableEurekaServer支持
 * EurekaServer服务端启动类，接收其他微服务注册进来
 */
@EnableEurekaServer
@SpringBootApplication
public class EurekaServer7003_App {

    public static void main(String[] args) {
        SpringApplication.run(EurekaServer7003_App.class, args);
    }
}
