package cn.techpan.springcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 配置类
 * applicationContext.xml
 */
@Configuration
public class ConfigBean {

    /**
     * jdbc     -   jdbcTemplate
     * redis    -   redisTemplate
     * 提供了多种便捷访问远程HTTP服务的方法（服务之间的调用？通信？）
     * 是一种简单便捷的访问restful服务类模板，是Spring提供的用于访问Rest服务的客户端模板工具集
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
