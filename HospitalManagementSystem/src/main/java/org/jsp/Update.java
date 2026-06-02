package org.jsp;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/update")
public class Update extends HttpServlet {

    // STEP 1: Fetch data from DB and display in HTML input fields
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        HttpSession session = req.getSession();
        String pemail = (String)session.getAttribute("pemail");

        pemail = req.getParameter("pemail");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/hospital";
            Connection con = DriverManager.getConnection(url, "root", "admin");

            PreparedStatement ps = con.prepareStatement("SELECT * FROM PATIENT WHERE pemail=?");
            ps.setString(1, pemail);
            ResultSet set = ps.executeQuery();

            if (set.next()) {
                String name = set.getString("pname");
                String email = set.getString("pemail");
                String pass = set.getString("ppassword");
                long num = set.getLong("pnumber");

                out.println("<html><head><title>Update Patient</title></head><body>");
                out.println("<h1>Update Patient Details</h1><hr>");
                out.println("<form action='update' method='post'>");

                out.println("<input type='hidden' name='oldEmail' value='" + email + "'>");

                out.println("Name: <input type='text' name='name' value='" + name + "'><br><br>");
                out.println("Email: <input type='email' name='email' value='" + email + "'><br><br>");
                out.println("Password: <input type='password' name='password' value='" + pass + "'><br><br>");
                out.println("Number: <input type='tel' name='number' value='" + num + "'><br><br>");

                out.println("<button type='submit'>Update Now</button>");
                out.println("</form> <a href='home'><button>Home</button> </a> </body></html>");
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String newName = req.getParameter("name");
        String newEmail = req.getParameter("email");
        String newPass = req.getParameter("password");
        String newNum = req.getParameter("number");
        String oldEmail = req.getParameter("oldEmail");

        HttpSession session = req.getSession();
        String type = (String)session.getAttribute("updateType");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital", "root", "admin");

            String updateQuery = "UPDATE PATIENT SET pname=?, pemail=?, ppassword=? , pnumber=? WHERE pemail=?";
            PreparedStatement ps = con.prepareStatement(updateQuery);

            ps.setString(1, newName);
            ps.setString(2, newEmail);
            ps.setString(3, newPass);
            ps.setString(4, newNum);
            ps.setString(5, oldEmail);

            int result = ps.executeUpdate();
            res.setContentType("text/html");
            if (result > 0) {
                res.getWriter().println("<h1>Success: Data Updated in Database!</h1>");
                RequestDispatcher dispatcher = null;
                if(type.equals("displayAll")) {
					dispatcher = req.getRequestDispatcher(type);
				} else {
					dispatcher = req.getRequestDispatcher(type+"?pemail="+newEmail);
				}
                dispatcher.forward(req, res);
//                res.getWriter().println("<a href="+type+"> <button> GoBack </button> </a>");
//                req.getRequestDispatcher("find").forward(req, res);
            }
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
            res.getWriter().println("<h1>Error occurred: " + e.getMessage() + "</h1>");
        }
    }
}