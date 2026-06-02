/*
  CREATE TABLE `hospital`.`patient` (
  `pname` VARCHAR(30) NOT NULL,
  `pemail` VARCHAR(45) NOT NULL,
  `ppassword` VARCHAR(45) NULL,
  `pnumber` BIGINT(10) NULL,
  PRIMARY KEY (`pemail`));
 */
package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/removePatient")
public class RemovePatient extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Connection connection = null;
		PreparedStatement statement = null;

		PrintWriter out = resp.getWriter();

		out.println("<html>");
		out.println("<style>");
		out.println("button{ "
				+ "height:20px; border-radius: 10px; border:none; background-color: lightblue; color:white; transition: transform .7s;	 }");
		out.println("button:hover{ "
				+ "transform: scale(1.1); background-color:blue; }");
		out.println("div{ background-color:white; color:black;   }");
		out.println("</style>");
		out.println("<body style='background-color:#040720;' >");

		out.println("<h1 style=\"text-align: center; color: white\">Remove</h1>");
		out.println("<table align=\"center\" style=\"border-collapse: collapse; background-color:  rgb(128, 128, 128);	; \" cellpadding=\"10\" >");

		out.println("<form action=\"removePatient\" method=\"post\">");
		out.println("<tr> <td>Email:</td> </tr>");
		out.println("<tr> <td> <input type=\"email\" placeholder=\"example@gmail.com\" name=\"pemail\"> </td> </tr>");
		out.println("<td> </td> <td> <button>Remove</button> </td>");
	    out.println("</form>");
	    out.println("<a href=\"home\"><button style=\"width:70px; height:30px; margin-left:100px; \" >Home</button></a>");
	    out.println("</table>");

		String pemail = req.getParameter("pemail");

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			connection = DriverManager.getConnection(url,"root","admin");

			String query = "DELETE FROM patient WHERE pemail=?";
			statement = connection.prepareStatement(query);
			statement.setString(1, pemail);

			int res = statement.executeUpdate();

			if(res>0)
			{
				out.println("<h1 style='text-align: center;'>Patient Removed</h1>");

				RequestDispatcher dispatcher = req.getRequestDispatcher("home");
				dispatcher.forward(req, resp);
			}
//			else
//				out.println("<h1 style='text-align: center;'>Patient Not Found</h1>");

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		finally
		{
			try {
				if(connection!=null) {
					connection.close();
				}
				if(statement!=null) {
					statement.close();
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
