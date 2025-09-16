package com.spicenet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/customer")
public class CustomerHome extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Handle GET requests (redirect to doPost)
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    // Handle POST requests
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            response.setContentType("text/html");
            PrintWriter out = response.getWriter();

            // Get current session
            HttpSession session = request.getSession(false);

            // If user is not logged in, redirect to login page
            if (session == null || session.getAttribute("user") == null) {
                response.sendRedirect("index.html");
                return;
            }

            // Get logged-in username from session
            String name = (String) session.getAttribute("user");
            
            out.println("<header>");
		    out.println("<div class='logo'>Spice Net</div>");
		    out.println("<nav>");
		    out.println("<ul>");
		    
		    out.println("<li><a href='viewProducts'>Products</a></li>");
		    out.println("<li><a href='placeOrder'>PlacedOrders</a></li>");
		    out.println("<li><a href='viewOrders'> MyOrders</a></li>");
		    out.println("<li><a href='profile'>Profile</a></li>");
		    out.println("<li><a href='logout' class='logout-btn'>Logout</a></li>");
		    out.println("</ul>");
		    out.println("</nav>");
		    out.println("</header>");

            // Generate HTML content
            out.println("<html><body>");
            out.println("<h1>Welcome, " + name + "</h1><br><br>");
            
            out.println("<link rel='stylesheet' type='text/css' href='css/style.css'>");

            // Customer-specific menu options
            out.println("<a href='viewProduct'>View Products</a><br>");
            out.println("<a href='placeOrder'>Place an Order</a><br>");
            out.println("<a href='viewOrder'>View My Orders</a><br>");
            out.println("<a href='profile'>My Profile</a><br>");
            out.println("<a href='logout'>Logout</a><br><br>");

            // Add a cardamom image for design consistency
            out.println("<img src='images/card2.jpeg' width='276px' height='183px' alt='cardamom'/>");

            out.println("</body></html>");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
