package com.example.modelmybatis.mapper;

import com.example.modelmybatis.pojo.Employee;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Test
    void testGetEmployee(){
        Map<String,Object> map = new HashMap();
        map.put("salary",10000);
        map.put("employee_id",200);
        List<Employee> list = employeeMapper.getEmployee(map);
        list.forEach(System.out::println);
    }

    @Test
    void testUpdate(){
        Map<String,Object> map = new HashMap<>();
//        map.put("first_name","Hu yang");
//        map.put("last_name","Hu");

        if (map.isEmpty()) {
            System.out.println("条件是空！");
            return;
        }

        map.put("employee_id",200);
        int update = employeeMapper.update(map);
        System.out.println("update: "+update);
    }
}
