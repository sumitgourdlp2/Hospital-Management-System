package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/home")
public class home extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		PrintWriter out = resp.getWriter();
		out.println("<html>");
		out.println("<body style='background-color:#040720;' >");
		out.println("<style>");
		out.println("	*{\r\n"
				+ "		margin: 0;\r\n"
				+ "		padding: 0;\r\n"
				+ "		box-sizing: border-box;\r\n"
				+ "	}\r\n"
				+ "");
		out.println("	body{\r\n"
				+ "		background-color: olive;\r\n"
				+ "		display: flex;\r\n"
				+ "		justify-content: center;\r\n"
				+ "		align-items: center;\r\n"
				+ "	}\r\n"
				+ "");
		out.println("	#container{\r\n"
				+ "		width: 400px;\r\n"
				+ "		height: 500px;\r\n"
				+ "		background-color: gray;\r\n"
				+ "		text-align: center ;\r\n"
				+ "		margin: 50px;\r\n"
				+ "		padding: 50px;		\r\n"
				+"		box-shadow: 2px 2px 5px gainsboro , -2px -2px 5px gainsboro; \r\n"
				+"		border-radius: 20px; \r\n"
				+ "	}\r\n"
				+ "");
			out.println("button{ margin:15px; "
					+ "width: 90%; height:30px; border-radius: 10px; border:none; background-color: lightblue; color:white; transition: transform .7s;	 }");
			out.println("button:hover{ "
					+ "transform: scale(1.2); background-color:blue; }");

		out.println("</style>");

		out.println("<div id=\"container\">");
		out.println("<h1>Home Page</h1>");
		out.println("<a href=\"profile\"><button>Profile</button></a> <br>");
		out.println("<a href=\"add\"><button>Add Patient</button></a> <br>");
		out.println("<a href=\"removePatient\"><button>Remove Patient</button></a> <br>");
		out.println("<a href=\"find\"><button>Find Patient By ID</button></a> <br>");
		out.println("<a href=\"displayAll\"> <button>Display All</button></a> <br>");
		out.println("<a href=\"logout\"> <button>LogOut</button> </a> <br>");
		out.println("</div>");
		out.println("</body>");
		out.println("</html>");

	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

}
