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

@WebServlet("/profile")
public class MyProfile extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
    	
        HttpSession session = request.getSession(false);
        String username = (String) session.getAttribute("user");

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body><h2>My Profile</h2>");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/spice_net", "root", "Joel@2001");

            PreparedStatement ps = con.prepareStatement(
                    "SELECT u.user_name, c.customer_name, c.customer_email, c.customer_phoneNumber "
                    + "FROM users u LEFT JOIN customers c ON u.user_id = c.user_id "
                    + "WHERE u.user_name = ?");
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                out.println("<p>Name: " + rs.getString("customer_name") + "</p>");
                out.println("<p>Email: " + rs.getString("customer_email") + "</p>");
                out.println("<p>Phone: " + rs.getString("customer_phoneNumber") + "</p>");
            } else {
                out.println("<p style='color:red;'>Profile not found</p>");
            }

            con.close();
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }
        out.println("</body></html>");
    }
}
