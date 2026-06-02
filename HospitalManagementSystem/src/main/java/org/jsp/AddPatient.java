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

@WebServlet("/add")
public class AddPatient extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		Connection connection = null;
		PreparedStatement statement = null;

		PrintWriter out = resp.getWriter();
		out.println("<html>");

		out.println("<style>");
		out.println("button{ margin:15px; "
				+ "width: 90%; height:30px; border-radius: 10px; border:none; background-color: lightblue; color:white; transition: transform .7s;	 }");
		out.println("button:hover{ "
				+ "transform: scale(1.2); background-color:blue; }");
		out.println("</style>");

		out.println("<body style='background-color:#040720;'>");
		out.println("<h1 style=\"text-align: center; color: white\">Add Patient</h1>");
		out.println("<a href=\"home\"><button style=\"width:70px; height:30px; margin-left:100px; \" >Home</button></a>");
		out.println("<table align=\"center\" style=\"border-collapse: collapse; background-color:  rgb(128, 128, 128);	; \" cellpadding=\"10\" >");

		out.println("<form action=\"add\" method=\"post\">");

		out.println("<tr> <td><b>Name</b></td> "
				+ "<td><input type='text' placeholder='Username' required name='pName'>"
				+ "</td> </tr>");
	    out.println("<tr> <td><b>Email</b></td> "
	    		+ "<td><input type='email' placeholder='example@gmail.com' required name='pEmail'> "
	    		+ "</td> </tr>");
	    out.println(" <tr>\r\n"
	    		+ "	      <td><b><label for=\"\">Password</label></b></td>\r\n"
	    		+ "	      <td><input type=\"password\" placeholder=\"example@1234\" required name=\"pPassword\"></td>\r\n"
	    		+ "	   </tr>\r\n"
	    		+ "");
	    out.println("	        <tr>\r\n"
	    		+ "	            <td><b>Mobile</b></td>\r\n"
	    		+ "	            <td><input type=\"tel\" pattern=\"[0-9]{10}\" placeholder=\"mobile\" name=\"pNumber\" required></td>\r\n"
	    		+ "	        </tr>\r\n"
	    		+ "");
	    out.println("	        <tr>\r\n"
	    		+ "	            <td >\r\n"
	    		+ "	            </td>\r\n"
	    		+ "	            <td>\r\n"
	    		+ "	                <button id=\"btn\">Add</button>      \r\n"
	    		+ "	            </td>\r\n"
	    		+ "	        </tr>\r\n"
	    		+ "");
	        out.println("</form>");
//	        out.println("<a href=\"home\"><button>Home</button></a>");
	        out.println("</table>");



		String pName = req.getParameter("pName");
		String pEmail = req.getParameter("pEmail");
		String pPassword = req.getParameter("pPassword");
		String number = req.getParameter("pNumber");
		Long pNumber= Long.parseLong(number);

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3306/hospital";
			connection = DriverManager.getConnection(url,"root","admin");

			String query = "INSERT INTO PATIENT (pname,pemail,ppassword,pnumber) VALUES (?,?,?,?)";
			statement = connection.prepareStatement(query);
			statement.setString(1, pName);
			statement.setString(2, pEmail);
			statement.setString(3, pPassword);
			statement.setLong(4, pNumber);

			statement.executeUpdate();

			out.println("<h1>Patient Added</h1>");

			RequestDispatcher dispatcher = req.getRequestDispatcher("home");
			dispatcher.forward(req, resp);

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
