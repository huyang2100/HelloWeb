package com.yang.mybatisplus.pojo;

import lombok.Data;

import java.util.Date;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/7 下午2:33
 */
@Data
public class Employees {
    private Integer employee_id;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
    private Double salary;
    private Date hiredate;
}
