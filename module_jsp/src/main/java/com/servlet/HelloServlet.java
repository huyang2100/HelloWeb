package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

@WebServlet(urlPatterns = "/hello", initParams = {@WebInitParam(name = "key",value = "value")})
public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Order Form</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Order Form</h1>");
        writer.println("<form method='post'>");
        writer.println("<table>");

        writer.println("<tr>");
        writer.println("<td>名称:</td>");
        writer.println("<td><input name='name'/></td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Address:</td>");
        writer.println("<td><textarea name='address' cols='40' rows='5'></textarea></td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Country:</td>");
        writer.println("<td><select name='country'/><option>United States</option><option>Canada</option></select></td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Delivery Method:</td>");
        writer.println("<td><input type='radio' name='deliveryMethod' value='First Class'/>First Class<input type='radio' name='deliveryMethod' value='Second Class'/>Second Class</td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Shipping Instructions:</td>");
        writer.println("<td><textarea name='instruction' cols='40' rows='5'/></textarea></td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>&nbsp;</td>");
        writer.println("<td><textarea name='instruction' cols='40' rows='5'/></textarea></td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Please send me the latest product catalog:</td>");
        writer.println("<td><input type='checkbox' name='catalogRequest'/></td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>&nbsp;</td>");
        writer.println("<td><input type='reset'/><input type='submit'/></td>");
        writer.println("</tr>");

        writer.println("</table>");
        writer.println("</form>");
        writer.println("</body>");
        writer.println("</html>");

        writer.println("value: "+getServletConfig().getInitParameter("key"));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");
        PrintWriter writer = resp.getWriter();
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>");
        writer.println("Order Form");
        writer.println("</title>");
        writer.println("</head>");

        writer.println("<body>");
        writer.println("<h1>");
        writer.println("Order Form");
        writer.println("</h1>");
        writer.println("<table>");

        writer.println("<tr>");
        writer.println("<td>Name:</td>");
        writer.println("<td>"+ req.getParameter("name") +"</td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Address:</td>");
        writer.println("<td>"+ req.getParameter("address") +"</td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Country:</td>");
        writer.println("<td>"+ req.getParameter("country") +"</td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Shipping Instructions:</td>");
        writer.println("<td>");
        String[] instructions = req.getParameterValues("instruction");
        if(instructions != null){
            for (String ins : instructions) {
                writer.println(ins+"<br/>");
            }
        }
        writer.println("</td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Delivery Method:</td>");
        writer.println("<td>"+ req.getParameter("deliveryMethod") +"</td>");
        writer.println("</tr>");

        writer.println("<tr>");
        writer.println("<td>Catalog Request:</td>");
        writer.println("<td>");
        if(req.getParameter("catalogRequest") == null){
            writer.println("No");
        }else{
            writer.println("Yes");
        }
        writer.println("</td>");
        writer.println("</tr>");

        writer.println("</table>");

        writer.println("<div style='border:1px solid #ddd;margin-top:40px;font-size:90%'>");
        writer.println("Debug Info<br/>");
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()){
            String paramName = parameterNames.nextElement();
            writer.println(paramName + ": ");
            String[] parameterValues = req.getParameterValues(paramName);
            for (String parameterValue : parameterValues) {
                writer.println(parameterValue+"<br/>");
            }
        }
        writer.println("</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
