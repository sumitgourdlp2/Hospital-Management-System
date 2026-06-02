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

@WebServlet("/displayPatient")
public class DisplayPatient extends HttpServlet{

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		HttpSession session = req.getSession();
		String pemail = req.getParameter("pemail");
		session.setAttribute("pemail", pemail);
		session.setAttribute("updateType", "displayPatient");
		session.setAttribute("deleteType", "find");
//		System.out.println(pemail);
		PrintWriter out = res.getWriter();

		out.println("<html>");
		out.println("<body>");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			Connection connection = DriverManager.getConnection(url , "root" , "admin");

			String query = "SELECT * FROM patient WHERE pemail=?";
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, pemail);

			ResultSet set = statement.executeQuery();

			out.println("<h2 style=\"text-align: center; color:\"red;\" \" >Patient Details</h2>");
			out.println("<table border=2; align=\"center\" style=\"border-collapse: collapse; \" cellpadding=\"10\" >");
			out.println("<tr>");
			out.println("<th>Name</th>");
			out.println("<th>Email</th>");
			out.println("<th>Password</th>");
			out.println("<th>Number</th>");
			out.println("<th>Operations</th>");
			out.println("</tr>");

			while (set.next())
			{
				String name = set.getString("pname");
				String email = set.getString("pemail");
				String password = set.getString("ppassword");
				long number = set.getLong("pnumber");
//				System.out.println(name);
//				System.out.println(email);
//				System.out.println(password);
//				System.out.println(number);

				out.println("<td>");
				out.println(name);
				out.println("</td> ");

				out.println("<td>");
				out.println(email);
				out.println("</td>");

				out.println("<td>");
				out.println(password);
				out.println("</td>");

				out.println("<td>");
				out.println(number);
				out.println("</td>");

				out.println("<td> <a href='update'> <button>Update</button> </a> "
						+ "<a href=\"deletePatient\"> <button>Delete</button> </a>  </td>");

			}
			out.println("</table>");
			out.println("<a href=\"home\"><button>Home</button></a>");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		out.println("</body>");
		out.println("</html>");
	}

}
