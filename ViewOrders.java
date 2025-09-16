package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/viewOrders")
public class ViewOrders extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Orders List</title></head><body>");
        out.println("<h1>All Orders</h1>");
        out.println("<table border='1' cellpadding='10'>");
        out.println("<tr><th>Product ID</th><th>Product Name</th><th>Customer Name</th><th>Quantity</th><th>Total Price</th><th>User ID</th></tr>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/spice_net", "root", "Joel@2001");

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT product_id, product_name, customer_name, quantity, total_price, user_id FROM orders");

            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("product_id") + "</td>");
                out.println("<td>" + rs.getString("product_name") + "</td>");
                out.println("<td>" + rs.getString("customer_name") + "</td>");
                out.println("<td>" + rs.getInt("quantity") + "</td>");
                out.println("<td>" + rs.getDouble("total_price") + "</td>");
                out.println("<td>" + rs.getInt("user_id") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");
            out.println("<br><a href='admin'>Back to Admin Dashboard</a>");
            out.println("</body></html>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
    }
}
