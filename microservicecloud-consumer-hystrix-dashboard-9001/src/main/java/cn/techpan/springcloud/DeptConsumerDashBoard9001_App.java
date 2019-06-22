package cn.techpan.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * EnableHystrixDashboard开启HystrixDashboard
 */
@SpringBootApplication
@EnableHystrixDashboard
public class DeptConsumerDashBoard9001_App {

    public static void main(String[] args) {
        SpringApplication.run(DeptConsumerDashBoard9001_App.class, args);
    }
}
