package com.example.modelmybatis.mapper;

import com.example.modelmybatis.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/7 下午2:42
 */
@SpringBootTest
public class EmployeeMapperTest {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Test
    void testGetAll(){
        List<Employee> all = employeeMapper.getAll();
        for (Employee e : all) {
            System.out.println(e);
        }
    }
}
