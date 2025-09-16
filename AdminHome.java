package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/admin")

public class AdminHome extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Forward the request to doPost()
        doPost(request, response);
    }
	
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException,IOException{
		try {
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			
			
			HttpSession session = request.getSession(false);
			
			 if (session == null || session.getAttribute("user") == null) {
		            response.sendRedirect("index.html"); // or use your login servlet mapping
		            return;
		        }
			 
			String name = (String) session.getAttribute("user");
			
			out.println("<header>");
		    out.println("<div class='logo'>Spice Net</div>");
		    out.println("<nav>");
		    out.println("<ul>");
		    out.println("<li><a href='admin'>Home</a></li>");
		    out.println("<li><a href='viewProducts'>Products</a></li>");
		    out.println("<li><a href='manageVendors'>Vendors</a></li>");
		    out.println("<li><a href='viewOrders'>Orders</a></li>");
		    out.println("<li><a href='reports'>Reports</a></li>");
		    out.println("<li><a href='logout' class='logout-btn'>Logout</a></li>");
		    out.println("</ul>");
		    out.println("</nav>");
		    out.println("</header>");
			
			out.println("<html><body>");
			out.println("<h1>Welcome " + name+"</h1><br><br>");
			
			out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");

			
			out.println("<a href='addProduct'>Add New Cardamom</a><br>");
			out.println("<a href='viewProducts'>View All Products</a><br>");
			out.println("<a href='manageVendors'>Manage Vendors</a><br>");
			out.println("<a href='viewOrders'>View All Orders</a><br>");
			out.println("<a href='viewCustomers'>View Customers</a><br>");
			out.println("<a href='reports'>Generate Sales Reports</a><br>");
			out.println("<a href='logout'>Logout</a><br>");
			
			out.println("<img src='images/card1.jpeg' width='225px' height='225px' alt='cardamom'/>");
		
			out.println("</body></html>");
			
			
		} catch (IOException  ex) {
			ex.printStackTrace();
		}
		
	}

}

