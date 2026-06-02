package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/displayAll")
public class DisplayAll extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		HttpSession session = req.getSession();
		PrintWriter out = res.getWriter();
		session.setAttribute("updateType", "displayAll");
		session.setAttribute("deleteType", "displayAll");

		out.println("<html>");

		out.println("<style>");
		out.println("button{ "
				+ "height:20px; border-radius: 10px; border:none; background-color: lightblue; color:white; transition: transform .7s;	 }");
		out.println("button:hover{ "
				+ "transform: scale(1.1); background-color:blue; }");
		out.println("table{ border-collapse: collapse; background-color:gainsboro; color:black;   }");
		out.println("#tr1{  background-color:blue; color:white;  }");
		out.println("</style>");
		out.println("<body style='background-color:#040720;' >");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			Connection connection = DriverManager.getConnection(url , "root" , "admin");

			Statement statement = connection.createStatement();
			String query = "SELECT * FROM patient";

			ResultSet set = statement.executeQuery(query);

			out.println("<h2 style=\"text-align: center; color:white; \" >All Patients</h2>");
			out.println("<a href=\"home\"><button style=\"width:70px; height:30px; margin-left:100px; \" >Home</button></a>");
			out.println("<table border=2; align=\"center\"  cellpadding=\"10\" >");
			out.println("<tr id='tr1'; >");
			out.println("<th>Name</th>");
			out.println("<th>Email</th>");
			out.println("<th>Password</th>");
			out.println("<th>Number</th>");
			out.println("<th>Operations</th>");
			out.println("</tr>");

			while (set.next())
			{
				String pname = set.getString("pname");
				String pemail = set.getString("pemail");
				String ppassword = set.getString("ppassword");
				long pnumber = set.getLong("pnumber");
				out.println("<tr>");
				out.println("<td>");
				out.println(pname);
				out.println("</td> ");

				out.println("<td>");
				out.println(pemail);
				out.println("</td>");

				out.println("<td>");
				out.println(ppassword);
				out.println("</td>");

				out.println("<td>");
				out.println(pnumber);
				out.println("</td>");

				out.println("<td>"
						+ "<a href=\"update?pemail="+pemail+"\"><button>Update</button></a>"
						+ " <a href=\"deletePatient\"> <button>Delete</button> </a> </td>");
				out.println("</tr>");
				out.println("  ");
			}
			out.println("</table>");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		out.println("</body>");
		out.println("</html>");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}

}
