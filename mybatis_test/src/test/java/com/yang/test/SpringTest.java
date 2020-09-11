package com.yang.test;

import com.yang.domain.Dept;
import com.yang.domain.Emp;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringTest {

    @Test
    public void helloTest(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
        Dept dept = ac.getBean(Dept.class);
        dept.setDname("部门一");
        System.out.println(dept);
    }
}
