package com.servlet;

import com.bean.Product;
import com.bean.ShoppingItem;
import com.sun.media.jfxmedia.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * 一个servlet处理了4个请求地址
 */
@WebServlet(urlPatterns = {"/products", "/viewProductDetails", "/addToCart", "/viewCart"})
public class ShoppingCartServlet extends HttpServlet {
    private static final String CART_ATTRIBUTE = "cart";
    private List<Product> products = new ArrayList<Product>();
    private NumberFormat currencyFormat = NumberFormat
            .getCurrencyInstance(Locale.US);

    @Override
    public void init() throws ServletException {
        /**
         * 初始化产品列表，真实环境中从数据库查询获取
         */
        products.add(new Product(1, "Bravo 32' HDTV", "Low-cost HDTV from renowned TV manufacturer", 159.95F));
        products.add(new Product(2, "Bravo BluRay Player", "High quality stylish BluRay player", 99.95F));
        products.add(new Product(3, "Bravo Stereo System", "5 speaker hifi system with iPod player", 129.95F));
        products.add(new Product(4, "Bravo iPod player", "An iPod plug-in that can play multiple formats", 39.95F));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncoding(req, resp);
        String uri = req.getRequestURI();
        /**
         * 依据获取到到请求uri，区分不同的请求
         */
        if (uri.endsWith("products")) {
            sendProductList(resp);
        } else if (uri.endsWith("viewProductDetails")) {
            sendProductDetails(req, resp);
        } else if (uri.endsWith("viewCart")) {
            showCart(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        setEncoding(req, resp);

        /**
         * post请求，向服务器中购物车添加商品
         */
        // add to cart
        /**
         * 要添加商品的ID
         */
        int productId = 0;
        /**
         * 要添加商品的数量
         */
        int quantity = 0;
        try {
            productId = Integer.parseInt(req.getParameter("id"));
            quantity = Integer.parseInt(req.getParameter("quantity"));
        } catch (NumberFormatException e) {
        }

        /**
         * 依据商品ID获取商品的对象实例
         */
        Product product = getProduct(productId);
        /**
         * 如果库中存在该商品且添加的数量>0
         */
        if (product != null && quantity > 0) {
            /**
             * 依据添加的商品和数量，构建一个购物车项实例
             */
            ShoppingItem shoppingItem = new ShoppingItem(product, quantity);
            // 单个用户在服务器中的容器，保存在服务器内存中
            /**
             * 获取请求对应的HttpSeesion对象
             */
            HttpSession session = req.getSession();
            /**
             * 从该HttpSession实例中查询是否存在购物车实例（整个购物过程中值应该存在一个购物车）
             */
            List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);
            if (cart == null) {
                /**
                 * 不存在购物车的话，先构建一个购物车，并分配到HttpSession中
                 */
                cart = new ArrayList<>();
                session.setAttribute(CART_ATTRIBUTE, cart);
            }
            /**
             * 向该请求对应的购物车中添加购物项
             */
            cart.add(shoppingItem);
        }

        // 跳转到产品列表页面
        sendProductList(resp);
    }

    private void setEncoding(HttpServletRequest req, HttpServletResponse resp) throws UnsupportedEncodingException {
        req.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
    }

    /**
     * 依据请求中商品ID获取对应商品实例对象
     * @param productId
     * @return
     */
    private Product getProduct(int productId) {
        for (Product product : products) {
            if (product.getId() == productId) {
                return product;
            }
        }
        return null;
    }

    /**
     * 展示购物车中的商品列表
     * @param req
     * @param resp
     * @throws IOException
     */
    private void showCart(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><title>Shopping Cart</title></head>");
        writer.println("<body><a href='products'>" +
                "Product List</a>");
        /**
         * 获取请求对应的HttpSession
         */
        HttpSession session = req.getSession();
        /**
         * 从请求对应的HttpSession中获取购物车实例
         */
        List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);
        if (cart != null) {
            writer.println("<table>");
            writer.println("<tr><td style='width:150px'>Quantity"
                    + "</td>"
                    + "<td style='width:150px'>Product</td>"
                    + "<td style='width:150px'>Price</td>"
                    + "<td>Amount</td></tr>");
            double total = 0.0;
            /**
             * 遍历购物车中的购物项
             */
            for (ShoppingItem shoppingItem : cart) {
                /**
                 * 获取购物项中的商品
                 */
                Product product = shoppingItem.getProduct();
                /**
                 * 获取购物项中的商品的数量
                 */
                int quantity = shoppingItem.getQuantity();
                if (quantity != 0) {
                    float price = product.getPrice();
                    writer.println("<tr>");
                    writer.println("<td>" + quantity + "</td>");
                    writer.println("<td>" + product.getName() + "</td>");
                    writer.println("<td>"
                            + currencyFormat.format(price)
                            + "</td>");
                    double subtotal = price * quantity;
                    writer.println("<td>"
                            + currencyFormat.format(subtotal) + "</td>");
                    total += subtotal;
                    writer.println("</tr>");
                }
            }

            writer.println("<tr><td colspan='4' "
                    + "style='text-align:right'>" + "Total:"
                    + currencyFormat.format(total) + "</td></tr>");
            writer.println("</table>");
        }

        writer.println("</table></body></html>");
    }

    /**
     * 展示商品详情
     * @param req
     * @param resp
     * @throws IOException
     */
    private void sendProductDetails(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        int productId = 0;
        /**
         * 获取请求中商品ID
         */
        try {
            productId = Integer.parseInt(req.getParameter("id"));
        } catch (NumberFormatException e) {
        }

        Product product = getProduct(productId);
        if (product != null) {
            /**
             * 构建一个post请求的表单（隐藏hidden 商品ID，该ID不是用户输入的，是从上一个页面传过来的），用来向购物车中添加该商品（提交）
             */
            writer.println("<html><head>"
                    + "<title>Product Details</title></head>"
                    + "<body><h2>Product Details</h2>"
                    + "<form method='post' action='addToCart'>");
            writer.println("<input type='hidden' name='id' " + "value='" + productId + "'/>");
            writer.println("<table>");
            writer.println("<tr><td>Name:</td><td>"
                    + product.getName() + "</td></tr>");
            writer.println("<tr><td>Description:</td><td>"
                    + product.getDescription() + "</td></tr>");
            writer.println("<tr>" + "<tr>"
                    + "<td><input name='quantity'/></td>"
                    + "<td><input type='submit' value='Buy'/>"
                    + "</td>"
                    + "</tr>");
            writer.println("<tr><td colspan='2'>"
                    + "<a href='products'>Product List</a>" + "</td></tr>");
            writer.println("</table>");
            writer.println("</form></body>");
        } else {
            writer.println("No product found");
        }
    }

    /**
     * 展示商品列表
     * @param resp
     * @throws IOException
     */
    private void sendProductList(HttpServletResponse resp) throws IOException {
        PrintWriter writer = resp.getWriter();
        writer.println("<html><head><title>Products</title>" +
                "</head><body><h2>Products</h2>");
        writer.println("<ul>");
        for (Product product : products) {
            /**
             * 将商品ID传递到页面中，以便于商品ID可以在其他页面中传递
             */
            writer.println("<li>" + product.getName() + "("
                    + currencyFormat.format(product.getPrice()) + ") (" + "<a href='viewProductDetails?id=" + product.getId() + "'>Details</a>)");
        }

        writer.println("</ul>");
        writer.println("<a href='viewCart'>View Cart</a>");
        writer.println("</body></html>");
    }
}
