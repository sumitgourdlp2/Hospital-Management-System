package org.jsp;

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

@WebServlet("/profile")
public class Profile extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();

		String email = (String)session.getAttribute("email");

		PrintWriter out = res.getWriter();

		out.println("<html>");
		out.println("<style>");
		out.println("button{ "
				+ "height:20px; border-radius: 10px; border:none; background-color: lightblue; color:white; transition: transform .7s;	 }");
		out.println("button:hover{ "
				+ "transform: scale(1.1); background-color:blue; }");
		out.println("div{ background-color:white; color:black;   }");
		out.println("</style>");
		out.println("<body style='background-color:#040720;' >");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			Connection connection = DriverManager.getConnection(url , "root", "admin");

//			System.out.println("Profile:- "+email);

			String query = "SELECT * FROM staff WHERE email=?;";
			PreparedStatement ps = connection.prepareStatement(query);
			ps.setString(1, email);
			out.println("<div style=\"border: 2px solid black; width: 40%; height: 300px; margin: auto; text-align: center; \">");
			out.println("<h1 style=\"color: red; margin:0; padding:0; \">Profile</h1>");
			out.println("<a href=\"home\"><button style=\"width:70px; height:30px; margin-left:250px; \" >Home</button></a> <hr> ");

			ResultSet set = ps.executeQuery();

			if(set.next()) {
				String name = set.getString("name");
				String db_email = set.getString("email");
				String password = set.getString("password");
				long number = set.getLong("number");
				out.println("Name: <input type=\"text\" value="+name+"> <br> <br>");
				out.println("Email: <input type=\"email\" value="+db_email+"> <br> <br>");
				out.println("Password: <input type=\"password\" value="+password+"> <br> <br> ");
				out.println("Number: <input type=\"tel\" value="+number+"> <br> <br>");
				out.println("<button>Update</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
			}

		out.println("</div>");
		System.out.println("Data Fetched......");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		out.println("</body>");
		out.println("</html>");
	}



}
