package cn.techpan.springcloud.controller;

import cn.techpan.springcloud.entity.Dept;
import cn.techpan.springcloud.service.DeptService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前后分类，返回JSON
 */
@RestController
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @PostMapping("/add")
    public boolean add(@RequestBody Dept dept) {
        return deptService.add(dept);
    }

    /**
     * 如果某个目标服务调用慢或者有大量超时，此时，熔断该服务的调用，对于后续调用请求，
     * 不在继续调用目标服务，直接返回，快速释放资源。如果目标服务情况好转则恢复调用。
     * HystrixCommand
     */
    @GetMapping("/get/{id}")
    @HystrixCommand(fallbackMethod = "processHystrixGet")
    public Dept get(@PathVariable("id") Long id) {
        Dept dept = deptService.get(id);
        if (null == dept) {
            //假设这里服务出问题了，会通过Hystrix服务熔断的@HystrixCommand注解进入到一个备选方案，而不是一直这里等待或者其他
            //真像AOP
            throw new RuntimeException("该ID：" + id + "没有对应的信息");
        }
        return dept;
    }

    @GetMapping("/list")
    public List<Dept> list() {
        return deptService.list();
    }

    public Dept processHystrixGet(@PathVariable("id") Long id) {
        return new Dept().setDeptNo(id).setDeptName("该ID：" + id + "没有对应的信息,null --  @HystrixCommand").setDatabaseSource("no record in this database");
    }

}
