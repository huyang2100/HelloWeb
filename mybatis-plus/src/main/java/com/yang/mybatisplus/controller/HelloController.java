package com.yang.mybatisplus.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/8 下午2:39
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    String hello(){
        return "<h1>Hello MyBatis Plus!</h1>";
    }
}
