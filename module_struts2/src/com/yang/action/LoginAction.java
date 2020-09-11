package com.yang.action;

import com.opensymphony.xwork2.Action;

public class LoginAction implements Action {
    @Override
    public String execute() throws Exception {
        System.out.println("Struts2成功运行了！！！");
        return "success";
    }
}
