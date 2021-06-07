package com.example.modelmybatis.controller;

import com.example.modelmybatis.mapper.EmployeeMapper;
import com.example.modelmybatis.pojo.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/7 下午3:07
 */
@RestController
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeMapper employeeMapper;

    @GetMapping("/getAll")
    public List<Employee> getAll(){
        return employeeMapper.getAll();
    }
}
