package com.spicenet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteProduct")
public class ProductDelete extends HttpServlet {

	private static final long serialVersionUID = 1L;
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/spice_net";
	final String user = "root";
	final String password = "Joel@2001";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	RequestDispatcher dis = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Forward the request to doPost()
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {

			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			int productId = Integer.parseInt(request.getParameter("pid"));
			pst = con.prepareStatement("delete from product where product_id=?");
			pst.setInt(1, productId);

			pst.executeUpdate();
			con.close();
			dis = request.getRequestDispatcher("product");
			dis.forward(request, response);

		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}
}
