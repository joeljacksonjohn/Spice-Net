package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;
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
import javax.servlet.http.HttpSession;

@WebServlet("/editProduct")
public class ProductEdit extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/spice_net";
	final String user = "root";
	final String password = "Joel@2001";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;
	
	RequestDispatcher dis=null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Forward the request to doPost()
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();

			HttpSession session = request.getSession(false);

			if (session == null || session.getAttribute("user") == null) {
				response.sendRedirect("index.html"); // or use your login servlet mapping
				return;
			}

			String name = (String) session.getAttribute("user");
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);

			int pid=Integer.parseInt(request.getParameter("pid"));
			
			pst=con.prepareStatement("select * from product where product_id=?");
			pst.setInt(1, pid);
			rs=pst.executeQuery();		
			
			
			out.println("<html><body>");
			out.println("<h3>Welcome " + name + "</h3><br><br>");
			
			/*int pid=Integer.parseInt(request.getParameter("pid"));
			
			pst=con.prepareStatement("select*from product where p_id=?");
			pst.setInt(1, pid);
			rs=pst.executeQuery();	*/ //we can put here also	
			
			
			out.println("<form name='productUpdate' method='post' action='productUpdate'>");
			
			while(rs.next()) {
			out.println("<input type='hidden' name='productId' value='"+rs.getInt(1)+"'><br><br>");		
			out.println("<input type='text' name='productName' value='"+rs.getString(2)+"'><br><br>");
			out.println("<input type='text' name='productPrice' value='"+rs.getFloat(3)+"'><br><br>");
			}
			out.println("<input type='submit' value='Update' />");
			out.println("</form>");
			out.println("</body>");
			out.println("</html>");
			out.close();
			
		} catch (IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();
		}

	}

}
