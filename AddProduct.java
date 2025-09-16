package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/addProduct")
public class AddProduct extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Add Product</title></head><body>");
        out.println("<h1>Add New Cardamom Product</h1>");
        out.println("<form method='post'>");
        out.println("Product Name: <input type='text' name='name'><br><br>");
        out.println("Price: <input type='text' name='price'><br><br>");
        out.println("<input type='submit' value='Add Product'>");
        out.println("</form>");
        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spice_net", "root", "Joel@2001");

            PreparedStatement ps = con.prepareStatement("INSERT INTO product(product_name,product_price) VALUES (?,?)");
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.executeUpdate();

            response.sendRedirect("viewProducts");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
