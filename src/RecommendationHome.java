import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import java.io.*;

@WebServlet("/RecommendationHome")

public class RecommendationHome extends HttpServlet {
	private String error_msg;
	HttpSession session;
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.setContentType("text/html");
		// PrintWriter pw = response.getWriter();
		// displayRegistration(request, response, pw, false);
		this.session = request.getSession(true);
		if (session.getAttribute("username") == null) {
			Cookie[] cookies = request.getCookies();
			// iterate each cookie
			for (Cookie cookie : cookies) {
				// display only the cookie with the name 'website'
				if (cookie.getName().equals("username")) {
					System.out.println(cookie.getValue());
					cookie.setValue("");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
			}
		}
		String value = request.getParameter("searchdata");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("Content.html");
		utility.printHtml("Footer.html");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		String value = request.getParameter("searchdata");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);
		String json = "[" + "{" + "'formatted_address' : 'test add',"
				+ "'icon' : 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png',"
				+ "'name' : 'TEst Name'," + "'rating' : '5'," + "'user_ratings_total' : 'user_ratings_total - 67' "
				+ "}, " + "{" + "'formatted_address' : 'test add1',"
				+ "'icon' : 'https://maps.gstatic.com/mapfiles/place_api/icons/restaurant-71.png',"
				+ "'name' : 'TEst Name1'," + "'rating' : '4'," + "'user_ratings_total' : 'user_ratings_total - 89' "
				+ "}" + "]";

		json = value;
		ArrayList<TextSearch> arrayListTextSearch = parseJsonTextSearchData(json);

		// displayRegistration(request, response, pw, false, arrayListTextSearch);

		writeAjaxResponse(request, response, "Feedback received succesfully!", arrayListTextSearch);
	}

	protected void writeAjaxResponse(HttpServletRequest req, HttpServletResponse resp, String result,
			ArrayList<TextSearch> arrayListTextSearch) throws ServletException {
		PrintWriter writer = null;
		StringWriter stringWriter = new StringWriter();
		try {
			writer = resp.getWriter();

			PrintWriter pw = new PrintWriter(stringWriter);
			displayRegistration(req, resp, pw, false, arrayListTextSearch);

			// ...code where you write to writer...
			System.out.println(stringWriter.toString());

		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println(stringWriter.toString());
		return;

	}

	protected ArrayList<TextSearch> parseJsonTextSearchData(String jsonResponse) {

		try {
			// TextSearchList data = new Gson().fromJson(jsonResponse,
			// TextSearchList.class);
			TextSearch[] data = new Gson().fromJson(jsonResponse, TextSearch[].class);
			ArrayList<TextSearch> arrayList = new ArrayList<TextSearch>();
			for (int i = 0; i < data.length; i++) {
				arrayList.add(data[i]);
			}

			// Show it.
			System.out.println(data);
			System.out.println(arrayList);
			return arrayList;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		// Now do the magic.

	}

	protected void displayRegistration(HttpServletRequest request, HttpServletResponse response, PrintWriter pw,
			boolean error, ArrayList<TextSearch> arrayListTextSearch) throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		//utility.printHtml("Header.html");
		// utility.printHtml("Content.html");
		if (error)
			pw.print("<h4 style='color:red'>" + error_msg + "</h4>");

		for (int i = 0; i < arrayListTextSearch.size(); i++) {

			pw.println("<div class='container'>");
			pw.println("<div class='post' style='widht:105%;'>	");
			pw.println(" <table id='place' style='width: 100%;'>");
			pw.println(" 	<tbody>");
			pw.println("  		<tr style='height: 300px;width: 100%;border: 2px solid #800000;'>");
			pw.println("  			<td style='width: auto;'>");
			pw.println(
					"   				<div id='cardviewholder' style='height: 300px;margin-left: 20px;width: 95%;margin-right: 20px;'>");
			pw.println(
					"   					<div id='photoholder' style='width: 250px;height: 260px;background-color: green;vertical-align: middle;margin-left: 20px;margin-top: 20px;float: left;'>");
			pw.println("                    <img style='width: 250px; height: 260px;' src='" + arrayListTextSearch.get(i).getPhoto_url() + "'>");
			pw.println(" 					</div>");
			pw.println(
					"      			<div id='addresstag' style='width: auto;float:left;margin-top: 20px;width: 580px;font-size: 20px;font-weight: bolder;color: black;margin-left: 30px;height:40px;'>"
							+ "					Name: " + arrayListTextSearch.get(i).getName() + " </div>");
			pw.println(
					"      			<div id='completeaddress' style='width: 580px;float:left;font-size: 16px;color: black;margin-left: 30px;overflow-wrap: break-word;'>");
			pw.println(
					"      				<div id='streetaddress' style='width: 580px;float:left;font-size: 16px;color: black;overflow-wrap: break-word;height: 35px;'>"
							+ "						Street Address: "
							+ arrayListTextSearch.get(i).getFormatted_address() + "</div>");
			pw.println(
					"       				<div id='city' style='width: 580px;float:left;font-size: 16px;color: black;overflow-wrap: break-word;height: 35px;'>"
							+ "Rating : " + arrayListTextSearch.get(i).getRating() + "</div>");
			pw.println(
					"					<div id='country' style='width: 580px;float:left;font-size: 16px;color: black;overflow-wrap: break-word;height: 35px;'>"
							+ "						Users Total Rating : "
							+ arrayListTextSearch.get(i).getUser_ratings_total() + " </div>  ");
			pw.println("      			</div>");
			//pw.println(
			//		"				<div id='viewreview'><input type='submit' value='View Review' class='btnreview' style='width:160px;background-color: #800000;margin-top: 20px;float: left;margin-left: 65px;height: 40px;font-size: 20px;border: none;'></div>");
			//pw.println(
			//		"				<div id='writereview'><input type='submit' value='Write Review' class='btnreview' style='width:160px;margin-top: 20px;float: left;margin-left: 100px;height: 40px;font-size: 20px;background-color: #800000;border: none;'></div>");
			
			pw.print("<div id='viewreview'><form method='post' action='ViewReview'>"+
					"<input type='hidden' name='name' value='"+arrayListTextSearch.get(i).getName()+"'>"+
					"<input type='hidden' name='streetaddress' value='"+arrayListTextSearch.get(i).getFormatted_address()+"'>"+
					"<input type='hidden' name='rating' value='"+arrayListTextSearch.get(i).getRating()+"'>"+
					"<input type='hidden' name='userstotalrating' value='"+arrayListTextSearch.get(i).getUser_ratings_total()+"'>"+
					"<input type='submit' value='View Review' class='btnreview' style='width:160px;background-color: #800000;margin-top: 20px;float: left;margin-left: 65px;height: 40px;font-size: 20px;border: none;' ></form></div>");
			
			pw.print("<div id='writereview'><form method='post' action='WriteReview'>"+
					"<input type='hidden' name='name' value='"+arrayListTextSearch.get(i).getName()+"'>"+
					"<input type='hidden' name='streetaddress' value='"+arrayListTextSearch.get(i).getFormatted_address()+"'>"+
					"<input type='hidden' name='lat' id ='lat' value='" + arrayListTextSearch.get(i).getLat() + "'>"+
					"<input type='hidden' name='lng' id ='lng' value='" + arrayListTextSearch.get(i).getLng() + "'>"+
					"<input type='hidden' name='rating' value='"+arrayListTextSearch.get(i).getRating()+"'>"+
					"<input type='hidden' name='userstotalrating' value='"+arrayListTextSearch.get(i).getUser_ratings_total()+"'>"+
					"<input type='hidden' name='photoUrl' value='" + arrayListTextSearch.get(i).getPhoto_url() + "'>"+
					"<input type='submit' value='Write Review' class='btnreview' style='width:160px;margin-top: 20px;float: left;margin-left: 100px;height: 40px;font-size: 20px;background-color: #800000;border: none;' ></form></div>");
			
			
			pw.println("      		</td>");
			pw.println("  		</tr>");
			pw.println(" 	</tbody>");
			pw.println(" </table>");
			pw.println("</div>");
			pw.println("</div>");
		}

		// utility.printHtml("Footer.html");

	}

}
