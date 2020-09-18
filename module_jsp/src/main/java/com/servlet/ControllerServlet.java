package com.servlet;

import com.bean.Product;
import com.form.ProductForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = {"/product_input.action", "/product_save.action"})
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        req.setCharacterEncoding("utf-8");

        String uri = req.getRequestURI();
        int lastIndex = uri.lastIndexOf("/");
        String action = uri.substring(lastIndex + 1);
        if(action.equals("product_input.action")){
            // no action class, there is nothing to be done
        }else if (action.equals("product_save.action")){
            // create form
            ProductForm productForm = new ProductForm();
            // populate action properties
            productForm.setName(req.getParameter("name"));
            productForm.setDescription(req.getParameter("description"));
            productForm.setPrice(req.getParameter("price"));

            // create model
            Product product = new Product();
            product.setDescription(productForm.getDescription());
            product.setName(productForm.getName());
            try {
                product.setPrice(Float.parseFloat(productForm.getPrice()));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }

            // execute action method
//            SaveProductAction saveProductAction = new SaveProductAction();
//            saveProductAction.save(product);

            // code to save product
            // store model in a scope variable for the view
            req.setAttribute("product",product);
        }

        // forward to a view
        String dispatchUrl = null;
        if (action.equals("product_input.action")){
            dispatchUrl = "/WEB-INF/jsp/ProductForm.jsp";
        }else if (action.equals("product_save.action")){
            dispatchUrl = "/WEB-INF/jsp/ProductDetails.jsp";
        }

        if (dispatchUrl != null){
            req.getRequestDispatcher(dispatchUrl).forward(req,resp);
        }
    }
}
