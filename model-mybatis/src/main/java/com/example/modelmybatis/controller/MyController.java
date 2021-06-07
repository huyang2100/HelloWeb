package com.example.modelmybatis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: huyang
 * @Date: 2021/6/7 上午9:28
 */
@RestController
public class MyController {

    @GetMapping("/hello")
    public String hello(){
        return "Hello friends";
    }
}
