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
import javax.servlet.http.HttpSession;

@WebServlet("/deletePatient")
public class DeletePatient extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();
        String pemail = (String)session.getAttribute("pemail");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hospital";
            Connection con = DriverManager.getConnection(url, "root", "admin");

            PreparedStatement ps = con.prepareStatement("DELETE FROM PATIENT WHERE pemail=?");
            ps.setString(1, pemail);

            int result = ps.executeUpdate();

            String deleteType = (String)session.getAttribute("deleteType");

            System.out.println(res);
            if(result>0) {
                res.getWriter().println("<h1>Success: Record Deleted!</h1>");
//                res.getWriter().println("<a href=\"find\"> <button> GoBack </button> </a>");
                RequestDispatcher dispatcher = req.getRequestDispatcher(deleteType);
        			dispatcher.forward(req, res);


            }
         con.close();
        }
        catch (ClassNotFoundException | SQLException e) {
        		e.printStackTrace();
        }
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}
}
