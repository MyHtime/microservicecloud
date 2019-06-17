package cn.techpan.springcloud.config;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 * applicationContext.xml
 */
@Configuration
public class ConfigBean {

    /**
     * 设置负载均衡算法
     * 未定时，采用RoundRobinRule-轮询
     */
    @Bean
    public IRule getIRule() {
        return new RandomRule();
    }
}
