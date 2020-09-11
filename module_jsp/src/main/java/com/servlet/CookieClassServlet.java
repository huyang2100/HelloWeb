package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(urlPatterns = "/cookieClass")
public class CookieClassServlet extends HttpServlet {
    private String[] methods = {
            "clone", "getComment", "getDomain",
            "getMaxAge", "getName", "getPath",
            "getSecure", "getValue", "getVersion",
            "isHttpOnly", "setComment", "setDomain",
            "setHttpOnly", "setMaxAge", "setPath",
            "setSecure", "setValue", "setVersion"
    };

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();
        Cookie maxRecordCookie = null;
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("maxRecords")){
                maxRecordCookie = cookie;
                break;
            }
        }

        int maxRecords = 5;
        if(maxRecordCookie != null){
            try {
                maxRecords = Integer.parseInt(maxRecordCookie.getValue());
            } catch (NumberFormatException e) {
                // do nothing, use maxRecords default value
            }
        }

        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.print("<html><head>" + "<title>Cookie Class</title>"
                + "</head><body>"
                + PreferenceServlet.MENU
                + "<div>Here are some of the methods in " +
                "javax.servlet.http.Cookie");
        writer.print("<ul>");
        for (int i = 0; i < maxRecords; i++) {
            writer.print("<li>" + methods[i] + "</li>");
        }

        writer.print("</ul>"); writer.print("</div></body></html>");
    }
}
