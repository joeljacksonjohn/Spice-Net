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

@WebServlet("/manageVendors")
public class ManageVendor extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // DB Connection details
    final String jdbcURL = "jdbc:mysql://localhost:3306/spice_net";
    final String dbUser = "root";
    final String dbPassword = "Joel@2001";

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<html><head><title>Manage Vendors</title></head><body>");
        out.println("<h1>Vendor Management</h1>");

        // Add form
        out.println("<h3>Add Vendor</h3>");
        out.println("<form method='post'>");
        out.println("Vendor Name: <input type='text' name='vendorName'><br><br>");
        out.println("Vendor Email: <input type='text' name='vendorEmail'><br><br>");
        out.println("Phone Number: <input type='text' name='vendorPhone'><br><br>");
        out.println("User ID (linked to users): <input type='text' name='userId'><br><br>");
        out.println("<input type='submit' name='action' value='Add Vendor'>");
        out.println("</form><hr>");

        // Update form
        out.println("<h3>Update Vendor</h3>");
        out.println("<form method='post'>");
        out.println("Vendor ID: <input type='text' name='vendorId'><br><br>");
        out.println("New Name: <input type='text' name='vendorName'><br><br>");
        out.println("New Email: <input type='text' name='vendorEmail'><br><br>");
        out.println("New Phone: <input type='text' name='vendorPhone'><br><br>");
        out.println("<input type='submit' name='action' value='Update Vendor'>");
        out.println("</form><hr>");

        // Delete form
        out.println("<h3>Delete Vendor</h3>");
        out.println("<form method='post'>");
        out.println("Vendor ID: <input type='text' name='vendorId'><br><br>");
        out.println("<input type='submit' name='action' value='Delete Vendor'>");
        out.println("</form><hr>");

        // Show all vendors
        out.println("<h3>Current Vendors</h3>");
        try (Connection con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            Class.forName("com.mysql.cj.jdbc.Driver");
            PreparedStatement ps = con.prepareStatement("SELECT * FROM vendors");
            ResultSet rs = ps.executeQuery();

            out.println("<table border='1'><tr><th>ID</th><th>Name</th><th>Email</th><th>Phone</th><th>User ID</th></tr>");
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("vendor_id") + "</td>");
                out.println("<td>" + rs.getString("vendor_name") + "</td>");
                out.println("<td>" + rs.getString("vendor_email") + "</td>");
                out.println("<td>" + rs.getString("vendor_phoneNumber") + "</td>");
                out.println("<td>" + rs.getInt("user_id") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }

        out.println("</body></html>");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        try (Connection con = DriverManager.getConnection(jdbcURL, dbUser, dbPassword)) {
            Class.forName("com.mysql.cj.jdbc.Driver");

            if ("Add Vendor".equals(action)) {
                String name = request.getParameter("vendorName");
                String email = request.getParameter("vendorEmail");
                String phone = request.getParameter("vendorPhone");
                int userId = Integer.parseInt(request.getParameter("userId"));

                PreparedStatement ps = con.prepareStatement(
                        "INSERT INTO vendors (vendor_name, vendor_email, vendor_phoneNumber, user_id) VALUES (?, ?, ?, ?)");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setInt(4, userId);
                ps.executeUpdate();

                out.println("<p style='color:green;'>Vendor Added Successfully!</p>");

            } else if ("Update Vendor".equals(action)) {
                int id = Integer.parseInt(request.getParameter("vendorId"));
                String name = request.getParameter("vendorName");
                String email = request.getParameter("vendorEmail");
                String phone = request.getParameter("vendorPhone");

                PreparedStatement ps = con.prepareStatement(
                        "UPDATE vendors SET vendor_name=?, vendor_email=?, vendor_phoneNumber=? WHERE vendor_id=?");
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, phone);
                ps.setInt(4, id);
                ps.executeUpdate();

                out.println("<p style='color:green;'>Vendor Updated Successfully!</p>");

            } else if ("Delete Vendor".equals(action)) {
                int id = Integer.parseInt(request.getParameter("vendorId"));
                PreparedStatement ps = con.prepareStatement("DELETE FROM vendors WHERE vendor_id=?");
                ps.setInt(1, id);
                ps.executeUpdate();

                out.println("<p style='color:red;'>Vendor Deleted Successfully!</p>");
            }

        } catch (Exception e) {
            out.println("<p style='color:red;'>Error: " + e.getMessage() + "</p>");
        }

        /*// Redirect back to GET page to refresh vendor list
        response.sendRedirect("manageVendors");*/
    }
}
