package org.jsp;

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

@WebServlet("/login")
public class LogIn extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String email = req.getParameter("email");
		String password = req.getParameter("password");

		Connection connection = null;
		PreparedStatement ps = null;
		ResultSet set = null;

		HttpSession session = req.getSession();

		PrintWriter out = res.getWriter();
		out.println("<html>");
		out.println("<body>");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			connection = DriverManager.getConnection(url, "root", "admin");

			String query = "SELECT * FROM staff WHERE email=?";
			ps = connection.prepareStatement(query);
			ps.setString(1, email);

			set = ps.executeQuery();


			if(set.next())
			{
				String db_pass = set.getString("password");
				if(password.equals(db_pass))
				{
					//home page....
					System.out.println("Login Succesful.....");
//					res.sendRedirect("home");
					String name = set.getString("name");
					String number = set.getString("number");
					session.setAttribute("name", name);
					session.setAttribute("number", number);
					session.setAttribute("email", email);
					session.setAttribute("password", password);

					RequestDispatcher dispatcher = req.getRequestDispatcher("home");
					dispatcher.forward(req, res);
				}
				else
				{
					//invalid password....
					RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
					out.println("<h1 style=\"text-align: center;\" >Invalid Password....</h1>");
					dispatcher.include(req, res);
				}
			}
			else
			{
				//invalid email....
				RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
				out.println("<h1 style=\"text-align: center;\" >Invalid Email....</h1>");
				dispatcher.include(req, res);
			}

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(connection!=null) {
					connection.close();
				}
				if(ps!=null) {
					connection.close();
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		out.println("</body>");
		out.println("</html>");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
