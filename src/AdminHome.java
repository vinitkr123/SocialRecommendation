import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@WebServlet("/AdminHome")

public class AdminHome extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		
		String operation = request.getParameter("operation");
		if(operation != null && operation.equals("changesource")) {
			MySQLDataStoreUtilities.insertApplicationConfiguration(request.getParameter("type"));
		}
		review(request, response);
	}

	/* @Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String username = request.getParameter("username");

	} */

	protected void review(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			if (!utility.isLoggedin()) {
				HttpSession session = request.getSession(true);
				session.setAttribute("login_msg", "Please Login to view Review");
				response.sendRedirect("Login");
				return;
			}
			String source = MySQLDataStoreUtilities.selectAppConfiguration();
			utility.printHtml("Header.html");
			pw.print("<div id=''><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Admin</a>");
			pw.print("</h2><div class='entry'>");
			// pw.print("<form method='post' action='Login'>");
			pw.print("<div>"+ source +"</div>");
			pw.print("<table style='width:100%'>");
			String val_python = "/SocialRecommendationSystem/AdminHome?type=surprise&operation=changesource";
			String val_recombee = "/SocialRecommendationSystem/AdminHome?type=recombee&operation=changesource";
			pw.print("<td><a href="+ val_python +">Use Surprise</a></td>");
			pw.print("<td><a href="+ val_recombee +">Use Recombee</a></td>");
			// pw.print("<tr><td> <input type='submit' class='btnbuy' value='surprise'></input> </td></tr>");
			// pw.print("<tr><td> <input type='submit' class='btnbuy' value='recombee'></input> </td></tr>");
			pw.print("</table>");
			// pw.print("</form>");
			pw.print("</div></div></div>");
			// utility.printHtml("Footer.html");

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}