package cn.techpan.springcloud.controller;

import cn.techpan.springcloud.entity.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * 消费者Controller(,可以不存在Service??????)
 */
@RestController
@RequestMapping("/consumer/dept")
public class DeptConsumerController {
    //private static final String REST_URL_PREFIX = "http://localhost:8001";
    /**
     * 通过微服务名访问，实现负载均衡
     */
    private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";

    /**
     * 使用RestTemplate访问restful接口很easy，三个参数(String url, Object request, Class<T> responseType)
     * String url：rest请求地址
     * Object request：请求参数
     * Class<T> responseType：Http响应转换被转换成的对象类型
     */
    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/add")
    public boolean add(Dept dept) {
        return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean.class);
    }

    @GetMapping("/get/{id}")
    public Dept get(@PathVariable("id") Long id) {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Dept.class);
    }

    @GetMapping("/list")
    public List<Dept> list() {
        return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
    }

    /**
     * 测试@EnableDiscoveryClient，消费者可以调用服务发现
     */
    @GetMapping("/discovery")
    public Object discovery() {
        return restTemplate.getForObject(REST_URL_PREFIX + "dept/discovery", Object.class);
    }
}
