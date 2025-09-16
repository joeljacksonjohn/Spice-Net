package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet("/vendor")

public class VendorHome extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
			
			 out.println("<header>");
			    out.println("<div class='logo'>Spice Net</div>");
			    out.println("<nav>");
			    out.println("<ul>");
			    out.println("<li><a href='samples'>Samples</a></li>");
			    out.println("<li><a href='product'>ProductEntry</a></li>");
			    out.println("<li><a href='invoice'>ViewInvoice</a></li>");
			    out.println("<li><a href='profile'>Profile</a></li>");
			    out.println("<li><a href='logout' class='logout-btn'>Logout</a></li>");
			    out.println("</ul>");
			    out.println("</nav>");
			    out.println("</header>");

			out.println("<html><body>");
			out.println("<h3>Welcome " + name + "</h3><br><br>");
			
			out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");

			out.println("<a href='profiles'>Profile Updation</a><br>");
			out.println("<a href='samples'>View Samples</a><br>");
			out.println("<a href='product'>Product Entry</a><br>");
			out.println("<a href='invoice'>View Invoices</a><br>");
			out.println("<a href='logout'>Logout</a><br>");

			out.println("<img src='images/cardamom.jpeg' width='249px' height='203px' alt='cardamom'/>");
			out.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}
}
