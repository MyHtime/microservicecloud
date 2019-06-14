package cn.techpan.springcloud.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Entity
 * 微服务，需要序列化
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
public class Dept implements Serializable {

    /**
     * 主键
     */
    private Long deptNo;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 来自哪个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
     */
    private String databaseSource;
}
