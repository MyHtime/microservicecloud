package cn.techpan.config;

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
