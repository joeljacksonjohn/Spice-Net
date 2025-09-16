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

@WebServlet("/reports")
public class Reports extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Sales Reports</title></head><body>");
        out.println("<h1>Sales Reports</h1>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/spice_net", "root", "Joel@2001");

            Statement stmt = con.createStatement();

            // ✅ Total Sales
            ResultSet rs1 = stmt.executeQuery("SELECT SUM(total_price) AS total_sales FROM orders");
            if (rs1.next()) {
                out.println("<h3>Total Sales Revenue: Rs. " + rs1.getDouble("total_sales") + "</h3>");
            }

            // ✅ Sales by Product
            out.println("<h3>Sales by Product</h3>");
            out.println("<table border='1' cellpadding='10'><tr><th>Product</th><th>Total Qty</th><th>Total Revenue</th></tr>");
            ResultSet rs2 = stmt.executeQuery(
                "SELECT product_name, SUM(quantity) AS total_qty, SUM(total_price) AS total_revenue FROM orders GROUP BY product_name");
            while (rs2.next()) {
                out.println("<tr><td>" + rs2.getString("product_name") + "</td><td>" +
                            rs2.getInt("total_qty") + "</td><td>Rs " +
                            rs2.getDouble("total_revenue") + "</td></tr>");
            }
            out.println("</table>");

            // ✅ Sales by Customer
            out.println("<h3>Sales by Customer</h3>");
            out.println("<table border='1' cellpadding='10'><tr><th>Customer</th><th>Total Spent</th></tr>");
            ResultSet rs3 = stmt.executeQuery(
                "SELECT customer_name, SUM(total_price) AS customer_spent FROM orders GROUP BY customer_name");
            while (rs3.next()) {
                out.println("<tr><td>" + rs3.getString("customer_name") + "</td><td>Rs " +
                            rs3.getDouble("customer_spent") + "</td></tr>");
            }
            out.println("</table>");

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            out.println("<p style='color:red;'>Error generating report: " + e.getMessage() + "</p>");
        }

        out.println("</body></html>");
    }
}
