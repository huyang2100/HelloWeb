package com.example.modelmybatis.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/7 下午2:33
 */
@Data
public class Employee {
    public Integer employee_id;
    public String first_name;
    public String last_name;
    public String email;
    public String phone_number;
    public Double salary;
    public Date hiredate;
}
