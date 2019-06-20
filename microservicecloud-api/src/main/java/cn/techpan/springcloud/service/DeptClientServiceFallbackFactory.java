package cn.techpan.springcloud.service;

import cn.techpan.springcloud.entity.Dept;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 对DeptClientService接口类的方法进行服务降级。
 * 避免存在多个方法时，防止服务熔断在服务提供者出现大量使用@HystrixCommand的方法来降低耦合度（类似于AOP的通知）。
 * 这里便是客户端的服务熔断。
 * 假设provider某个关闭了，这里就降级了
 * 也就是hystrix在客服端进行降级，服务端进行熔断
 */
@Component
public class DeptClientServiceFallbackFactory implements FallbackFactory<DeptClientService> {
    @Override
    public DeptClientService create(Throwable cause) {
        return new DeptClientService() {
            @Override
            public boolean add(Dept dept) {
                return false;
            }

            @Override
            public Dept get(Long id) {
                return new Dept().setDeptNo(id).setDeptName("该ID" + id + "没有对应的信息，Consumer客户端提供的降级信息，此刻服务Provider已经关闭").setDatabaseSource("no database here");
            }

            @Override
            public List<Dept> list() {
                return null;
            }
        };
    }
}
