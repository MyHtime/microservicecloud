package cn.techpan.springcloud.controller;

import cn.techpan.springcloud.entity.Dept;
import cn.techpan.springcloud.service.DeptClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/consumer/dept")
public class DeptConsumerController {
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    /**
     * Ribbon通过微服务名访问，实现负载均衡
     */
    //private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";

    /**
     * 通过feign接口调用微服务提供者，同时具有负载均衡
     */
    @Autowired
    private DeptClientService deptClientService;

    @PostMapping("/add")
    public boolean add(Dept dept) {
        return this.deptClientService.add(dept);
    }

    @GetMapping("/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return this.deptClientService.get(id);
    }

    @GetMapping("/list")
    public List<Dept> list() {
        return this.deptClientService.list();
    }

}
