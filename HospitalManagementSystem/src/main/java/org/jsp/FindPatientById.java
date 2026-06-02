package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/find")
public class FindPatientById extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		PrintWriter out = resp.getWriter();

		out.println("<html>");
		out.println("<style>");
		out.println("button{ "
				+ "height:20px; border-radius: 10px; border:none; background-color: lightblue; color:white; transition: transform .7s;	 }");
		out.println("button:hover{ "
				+ "transform: scale(1.1); background-color:blue; }");
		out.println("table{ border-collapse: collapse; width:200px; height:200px; border-radius:20px; background-color:gainsboro; color:black;   }");
		out.println("</style>");
		out.println("<body style='background-color:#040720;' >");

//		out.println("<body>");
		out.println("<h1 style=\"text-align: center; color: white\">Find Patient</h1>");
		out.println("<a href=\"home\"><button style=\"width:70px; height:30px; margin-left:100px; \" >Home</button></a>");
		out.println("<table align=\"center\" style=\"border-collapse: collapse; background-color:  rgb(128, 128, 128);	; \" cellpadding=\"10\" >");

		out.println("<form action=\"displayPatient\" method=\"post\">");
		out.println("<tr> <td>Email:</td> </tr>");
		out.println("<tr> <td> <input type=\"email\" placeholder=\"example@gmail.com\" name=\"pemail\"> </td> </tr>");
		out.println("<td colspan:2 > </td> <td> <button>Find</button> </td>");
	    out.println("</form>");
	    out.println("</table>");

//	    out.println("<a href=\"home\"><button>Home</button></a>");


	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
