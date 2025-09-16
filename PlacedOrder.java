package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/placeOrder")
public class PlacedOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        // Simple form
        out.println("<html><body><h2>Place an Order</h2>");
        out.println("<form method='post' action='placeOrder'>");
        out.println("Product ID: <input type='text' name='productId'><br>");
        out.println("Quantity: <input type='text' name='quantity'><br>");
        out.println("<input type='submit' value='Order'>");
        out.println("</form></body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("user") == null) {
            // no session or user not logged in → redirect to login
            response.sendRedirect("index.html");
            return;
        }
        String username = (String) session.getAttribute("user");

        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spice_net", "root", "Joel@2001");

            // get product info
            PreparedStatement ps1 = con.prepareStatement("SELECT product_name, product_price FROM product WHERE product_id=?");
            ps1.setInt(1, productId);
            ResultSet rs = ps1.executeQuery();

            PrintWriter out = response.getWriter();
            if (rs.next()) {
                String productName = rs.getString("product_name");
                double price = rs.getDouble("product_price");
                double total = price * quantity;
                

                PreparedStatement ps2 = con.prepareStatement(
                		"INSERT INTO orders (product_id, product_name, customer_name, quantity, total_price, user_id, customer_id) " +
                			    "VALUES (?, ?, ?, ?, ?, (SELECT user_id FROM users WHERE user_name=?), " +
                			    "(SELECT customer_id FROM customers WHERE user_id=(SELECT user_id FROM users WHERE user_name=?)))");
                ps2.setInt(1, productId);
                ps2.setString(2, productName);
                ps2.setString(3, username);
                ps2.setInt(4, quantity);
                ps2.setDouble(5, total);
                ps2.setString(6, username);
                ps2.setString(7, username); 
                ps2.executeUpdate();

                out.println("<p>Order placed successfully for " + quantity + " " + productName + "!</p>");
            } else {
                out.println("<p style='color:red;'>Invalid Product ID</p>");
            }

            con.close();
        } catch (Exception e) {
            response.getWriter().println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
