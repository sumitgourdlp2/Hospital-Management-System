package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.GenericServlet;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SignUp extends GenericServlet {

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {

		String name = req.getParameter("name");
		String email = req.getParameter("email");
		String password = req.getParameter("password");
		String num = req.getParameter("number");

		if(name!=null && email!=null && password!=null && num!=null)
		{
			long number = Long.parseLong(num);
		
//		System.out.println(name);
//		System.out.println(email);
//		System.out.println(password);
//		System.out.println(number);

		PrintWriter out = res.getWriter();

		out.println("<html>");
		out.println("<body style='background-color:#040720;' >");

		Connection connection = null;
		PreparedStatement ps = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			connection = DriverManager.getConnection(url, "root","admin");

			String query = "INSERT INTO staff (name,email,password,number) VALUES(?,?,?,?)";
			ps = connection.prepareStatement(query);

			ps.setString(1,name);
			ps.setString(2,email);
			ps.setString(3,password);
			ps.setLong(4,number);

			ps.executeUpdate();

			out.println("<h1>SingUp Successfull....</h1>");
			RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
			dispatcher.forward(req, res);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(connection!=null) {
					connection.close();
				}
				if(ps!=null) {
					ps.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		out.println("</body>");
		out.println("</html>");
	}
	}
}
