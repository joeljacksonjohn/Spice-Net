# 🌿 Spice-Net

Spice-Net is a **Servlet-based Web Application** developed for managing and promoting **Cardamom spice products**.  
The project is built using **Java Servlets, HTML, and CSS (no JSP, no frameworks)** to keep it lightweight and educational.  

It provides a simple admin, vendor, and customer portal to handle cardamom products, vendor details, and order management.

---

## 🚀 Features

### 👨‍💼 Admin
- Admin login and authentication.
- Manage vendors and customers.
- Add, update, or delete cardamom products.
- View and manage orders.
- Generate simple sales reports.

### 🏪 Vendor
- Vendor registration and login.
- Manage their cardamom product listings.
- Update prices, stock availability, and product details.
- View customer orders.

### 👥 Customer
- Customer registration and login.
- Browse cardamom products.
- Place orders.
- View order history.

---

## 🛠️ Tech Stack

- **Language**: Java (Servlets)
- **Frontend**: HTML + CSS (basic styling)
- **Backend**: MySQL (JDBC for DB connection)
- **Server**: Apache Tomcat 9.0
- **IDE**: Eclipse

---

## 📂 Project Structure

Spice-Net/
│── src/
│ └── com.farmflavour/
│ ├── LoginServlet.java
│ ├── AdminHome.java
│ ├── VendorHome.java
│ ├── CustomerHome.java
│ └── (other servlets)
│
│── WebContent/
│ ├── css/
│ │ └── style.css
│ ├── images/
│ │ └── cardamom.jpg
│ ├── login.html
│ ├── register.html
│ └── (other HTML pages)
│
└── README.md


---

## ⚙️ Setup Instructions

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/Spice-Net.git
2.Import into Eclipse
    Open Eclipse → File → Import → Existing Projects into Workspace.
    Select the Spice-Net folder.

3.Configure Server
    Install Apache Tomcat 9.0.
    Add project to Tomcat server in Eclipse.

4.Database Setup
    Create a MySQL database (e.g., spice_net).
    Import the provided SQL script (spice_net.sql) with tables:
      users (admin, vendors, customers)
      products
      orders
      vendors

    Update DB credentials in your Servlet JDBC connection.

    String url = "jdbc:mysql://localhost:3306/spice_net";
String user = "root";
String password = "Joel@2001";

5.Run the Application
 * Start Tomcat server.
 * Visit: http://localhost:8080/Spice-Net/

 📸 Screenshots
     Add screenshots of:
     * Login Page
     * Admin Dashboard
     * Vendor Dashboard
     * Customer Product Page


  🎯 Future Enhancements
     * Add payment gateway integration.
     * Add search and filter for products.
     * Improve UI with modern CSS/JS framework (Bootstrap/React).
     * Implement REST API layer for mobile app integration.
