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

@WebServlet("/viewOrder")
public class MyOrder extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("user");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>My Orders</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spice_net", "root", "Joel@2001");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM orders WHERE customer_name=?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            out.println("<table border='1'><tr><th>Product</th><th>Quantity</th><th>Total Price</th></tr>");
            while (rs.next()) {
                out.println("<tr><td>" + rs.getString("product_name") + "</td><td>" +
                        rs.getInt("quantity") + "</td><td>Rs. " +
                        rs.getDouble("total_price") + "</td></tr>");
            }
            out.println("</table>");

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
        out.println("</body></html>");
    }
}
