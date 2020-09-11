package com.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpCookie;
import java.util.HashMap;
import java.util.Map;

@WebServlet(urlPatterns = "/bigCities")
public class BigCitiesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> capitals = new HashMap<>();
        capitals.put("Indonesia", "Jakarta");
        capitals.put("Malaysia", "Kuala Lumpur");
        capitals.put("Thailand", "Bangkok");
        req.setAttribute("capitals", capitals);

        Map<String, String[]> bigCities = new HashMap<String, String[]>();
        bigCities.put("Australia", new String[]{"Sydney", "Melbourne", "Perth"});
        bigCities.put("New Zealand", new String[]{"Auckland", "Christchurch", "Wellington"});
        bigCities.put("Indonesia", new String[]{"Jakarta", "Surabaya", "Medan"});
        req.setAttribute("capitals", capitals);
        req.setAttribute("bigCities", bigCities);
        req.getRequestDispatcher("/bigCities.jsp").forward(req,resp);
    }
}
