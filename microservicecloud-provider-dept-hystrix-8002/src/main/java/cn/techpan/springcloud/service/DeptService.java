package cn.techpan.springcloud.service;

import cn.techpan.springcloud.entity.Dept;

import java.util.List;

public interface DeptService {

    boolean add(Dept dept);

    Dept get(Long id);

    List<Dept> list();
}
