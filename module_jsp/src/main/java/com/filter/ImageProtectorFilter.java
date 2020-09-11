package com.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"*.png", "*.jpg", "*.gif"})
public class ImageProtectorFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("ImageProtectorFilter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String referer = httpServletRequest.getHeader("referer");
        System.out.println("referer" + referer);
        if (referer == null) {
            throw new ServletException("Image not available");
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }

    @Override
    public void destroy() {

    }
}
