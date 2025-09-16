package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;
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

@WebServlet("/product")
public class Products extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	final String driver = "com.mysql.cj.jdbc.Driver";
	final String url = "jdbc:mysql://localhost:3306/spice_net";
	final String user = "root";
	final String password = "Joel@2001";

	Connection con = null;
	PreparedStatement pst = null;
	ResultSet rs = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	        throws ServletException, IOException {
	    doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			HttpSession session = request.getSession(false);
			
			/*if (session == null || session.getAttribute("user") == null) {
		            response.sendRedirect("index.html"); // or use your login servlet mapping
		            return;
			}*/
			 
			String name = (String) session.getAttribute("user");
			
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			pst=con.prepareStatement("select * from product");
			rs=pst.executeQuery();
			
			out.println("<html><body>");
			out.println(" <h3>Welcome " + name + "</h3><br><br>");
			out.println(" <h3>Product</h3><br><br>");
			
			out.println("<form name='product' method='post' action='productSave'>"
			+ "<input type='text' name='productName' >"
			+ "<input type='text' name='productPrice' >"
			+ "<input type='submit' value='Save' />"
			+ "</form>");
			
			out.println("<table border='1'>");
				out.println("<tr>");
				out.println("<th>SLNO</th>");
				out.println("<th>PRODUCT NAME</th>");
				out.println("<th>PRODUCT PRICE</th>");
				out.println("<th>ACTION</th>");
				out.println("<th>ACTION</th>");
				out.println("</tr>");
			
			int i=1;
			while(rs.next()) {
				out.println("<tr>");
				out.println("<td>"+i+"</td>");
				out.println("<td>"+rs.getString(2)+"</td>");
				out.println("<td>"+rs.getFloat(3)+"</td>");
				out.println("<td><a href='editProduct?pid="+rs.getInt(1)+"'>Edit</a></td>");
				out.println("<td><a href='deleteProduct?pid="+rs.getInt(1)+"'>Delete</a></td>");
				out.println("</tr>");
				i++;
			}
			out.println("</table>");
			out.println("</body></html>");
			out.close();
			
				
		}catch(IOException | SQLException | ClassNotFoundException ex) {
			ex.printStackTrace();	
			
		}
	
	}

}
 