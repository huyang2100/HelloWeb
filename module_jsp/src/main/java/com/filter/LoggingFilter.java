package com.filter;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@WebFilter(urlPatterns = "/*", initParams = {@WebInitParam(name = "logFileName", value = "log.txt"), @WebInitParam(name = "prefix", value = "URI: ")})
public class LoggingFilter implements Filter {
    private PrintWriter logger;
    private String prefix;


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        prefix = filterConfig.getInitParameter("prefix");
        String logFileName = filterConfig.getInitParameter("logFileName");
        String appPath = filterConfig.getServletContext().getRealPath("/");
        // without path info in logFileName, the log file will be created in $TOMCAT_HOME/bin
        try {
            File file = new File(appPath, logFileName);
            System.out.println("logFilePath: " + file.getAbsolutePath());
            logger = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("LoggingFilter.doFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        logger.println(new Date() + " " + prefix + httpServletRequest.getRequestURI());
        logger.flush();
        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {
        System.out.println("destorying filter");
        if (logger != null) {
            logger.close();
        }
    }
}
