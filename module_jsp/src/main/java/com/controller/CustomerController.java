package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomerController {

    @RequestMapping("/customer_input")
    public String inputCustomer(){
        return "CustomerForm";
    }
}