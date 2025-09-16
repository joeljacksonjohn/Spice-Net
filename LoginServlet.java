package com.spicenet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/login")

public class LoginServlet extends HttpServlet {

	/**
	 * @author joel
	 * @date : 25-08-2025
	 * @version : 1.0
	 */
	private static final long serialVersionUID = 1L;

	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/spice_net";
	final String user = "root";
	final String password = "Joel@2001";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	String userName = "";
	String userRole = "";
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to doPost()
        doPost(request, response);
    }

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			String username = request.getParameter("username");
			String password = request.getParameter("password");
			
			System.out.println("=======am here.......username: " + username);
			System.out.println("=======am here.......password: " + password);
			
			pst = con.prepareStatement("select user_name,user_role from users where user_name=? and user_password=?");

			pst.setString(1, username);
			pst.setString(2, password);

			rs = pst.executeQuery();

			while (rs.next()) {
				userName = rs.getString(1);
				userRole = rs.getString(2);
			}

			HttpSession session = request.getSession();
			session.setAttribute("user", userName);

			if (userRole.equals("admin")) {
				response.sendRedirect("admin");
			} else if (userRole.equals("vendor")) {
				response.sendRedirect("vendor");
			} else if (userRole.equals("customer")) {
				response.sendRedirect("customer");
			} else {
				response.sendRedirect("noUser");
			}

		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
