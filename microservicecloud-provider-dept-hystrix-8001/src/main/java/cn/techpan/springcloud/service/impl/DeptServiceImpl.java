package cn.techpan.springcloud.service.impl;

import cn.techpan.springcloud.entity.Dept;
import cn.techpan.springcloud.mapper.DeptMapper;
import cn.techpan.springcloud.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeptServiceImpl implements DeptService {

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public boolean add(Dept dept) {
        return deptMapper.addDept(dept);
    }

    @Override
    public Dept get(Long id) {
        return deptMapper.findById(id);
    }

    @Override
    public List<Dept> list() {
        return deptMapper.findAll();
    }
}
