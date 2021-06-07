package com.example.modelmybatis.mapper;

import com.example.modelmybatis.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/7 下午2:35
 */
@Mapper
public interface EmployeeMapper {
    List<Employee> getAll();

    Employee getEmployeeById(int eid);
}
