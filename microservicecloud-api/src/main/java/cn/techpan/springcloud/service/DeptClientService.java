package cn.techpan.springcloud.service;

import cn.techpan.springcloud.entity.Dept;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 开启FeignClient，完成对服务提供方的接口绑定
 * fallbackFactory:指定fallbackFactory，对这个接口类的方法进行降级。当出现问题时，会去DeptClientServiceFallbackFactory寻找对应的解决方法
 */
@RequestMapping("/dept")
@FeignClient(value = "MICROSERVICECLOUD-DEPT", fallbackFactory = DeptClientServiceFallbackFactory.class)
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
