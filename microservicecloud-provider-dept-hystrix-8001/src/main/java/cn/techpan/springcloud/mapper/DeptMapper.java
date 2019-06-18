package cn.techpan.springcloud.mapper;

import cn.techpan.springcloud.entity.Dept;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DeptMapper {

    @Insert("insert into dept (dept_name, database_source) values (#{deptName}, database())")
    boolean addDept(Dept dept);

    @Select("select * from dept where dept_no = #{deptNo}")
    Dept findById(@Param("deptNo") Long id);

    @Select("select * from dept")
    List<Dept> findAll();
}
