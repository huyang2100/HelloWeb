package com.yang;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

public class SecondServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        Enumeration<String> initParameterNames = config.getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String key = initParameterNames.nextElement();
            String value = config.getInitParameter(key);

            System.out.println("key:" + key + "--value:" + value);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Second程序访问了....post");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Second程序访问了....get");

        System.out.println("servletContextName: "+getServletContext().getServletContextName());
        System.out.println("ContextPath: " + getServletContext().getContextPath());
        System.out.println("/----" + getServletContext().getRealPath("/"));

        Enumeration<String> initParameterNames = getServletContext().getInitParameterNames();
        while (initParameterNames.hasMoreElements()){
            String key = initParameterNames.nextElement();
            String value = getServletContext().getInitParameter(key);

            System.out.println("key:" + key + "--value:" + value);
        }
    }
}
