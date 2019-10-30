import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

@WebServlet("/Registration")

public class Registration extends HttpServlet {
	private String error_msg;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayRegistration(request, response, pw, false);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String repassword = request.getParameter("repassword");
		String email = request.getParameter("email");
		String streetaddress = request.getParameter("streetaddress");
		String aptno = request.getParameter("aptno");
		streetaddress= streetaddress.concat(" "+aptno);
		String cityname = request.getParameter("cityname");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");
		String latlong = request.getParameter("latlong");
		String preference = null;
		String usertype = "customer";
		if (!utility.isLoggedin())
			usertype = request.getParameter("email");

		if (!password.equals(repassword)) {
			error_msg = "Passwords doesn't match!";
		} else {

			HashMap<String, User> hm = new HashMap<String, User>();
			try {
				hm = MySQLDataStoreUtilities.selectUser();
			} catch (Exception e) {
			}
			if (hm.containsKey(username))
				error_msg = "Username already exist as " + usertype;
			else {

				User user = new User(username, password, usertype);
				hm.put(username, user);
				if (MySQLDataStoreUtilities.insertUser(username,   email,preference,password, repassword, streetaddress,
						cityname,state,zipcode,country,latlong)) {
					HttpSession session = request.getSession(true);
					session.setAttribute("login_msg", "Your " + usertype + " account has been created. Please login");
					if (!utility.isLoggedin()) {
						response.sendRedirect("Login");
						return;
					} else {
						response.sendRedirect("Account");
						return;
					}
				}
			}
		}
		if (utility.isLoggedin()) {
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", error_msg);
			response.sendRedirect("Account");
			return;
		}
		displayRegistration(request, response, pw, true);
	}

	protected void displayRegistration(HttpServletRequest request, HttpServletResponse response, PrintWriter pw,
			boolean error) throws ServletException, IOException {
		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.println("<script type='text/javascript' src='geocode.js'></script>");
		pw.print("<div class='post' style='float: left; width: 85%;margin-left: 78px;'>");
		pw.print("<h2 class='title meta' style='text-align: center;height: auto;'><a style='font-size: 28px;font-weight: bold;color:#800000 !important;'>Registration</a></h2>" + "<div class='entry'>"
				+ "<div style='margin-left: auto;margin-right: auto;'>");
		if (error)
			pw.print("<h4 style='color:red'>" + error_msg + "</h4>");
		pw.println(
				"<script type='text/javascript' src ='geocode.js'>                                                                        ");
		pw.println("</script>                                                                     ");
		pw.print("<form method='post' action='Registration'>" + "<table style='width:100%'><tr><td>"
				+ "<h3>Username</h3></td><td><input type='text' name='username' style='border: 1px solid darkgray;box-shadow: none;border-radius: 4px;height: 34px;border-shadow: none' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Password</h3></td><td><input type='password' name='password' style='border: 1px solid darkgray;box-shadow: none;border-radius: 4px;height: 34px;border-shadow: none' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Confirm Password</h3></td><td><input type='password' name='repassword' style='border: 1px solid darkgray;box-shadow: none;border-radius: 4px;height: 34px;border-shadow: none' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Email(Username)</h3></td><td><input type='text' style='border: 1px solid darkgray;box-shadow: none;border-radius: 4px;height: 34px;border-shadow: none;' name='email' value='' class='input' required></input>"
				+ "</td></tr><tr><td>"
				+ "<h3>Address</h3></td><td style='padding-bottom: 5px;'><input id='autocomplete' placeholder='Enter your address'  onFocus='geolocate()' type='text' class='form-control' style='height: 34px;box-shadow: none;border: 1px solid darkgray;border-radius: 4px;padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>Street No</h3></td><td style='padding-bottom: 5px'><input class='form-control' required id='street_number' name='streetaddress' disabled='true' style='padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>Street</h3></td><td style='padding-bottom: 5px'><input class='form-control' id='route' disabled='true' name ='aptno' style='padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>City</h3></td><td style='padding-bottom: 5px'><input class='form-control field' id='locality' name ='cityname' disabled='true' style='padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>State</h3></td><td style='padding-bottom: 5px'><input class='form-control' id='administrative_area_level_1' name ='state' disabled='true' style='padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>Zipcode</h3></td><td style='padding-bottom: 5px'><input class='form-control' id='postal_code' disabled='true' name ='zipcode' style='padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>Country</h3></td><td style='padding-bottom: 5px'><input class='form-control' id='country' disabled='true' name ='country' style='padding: 0px;'>"
				+ "</td></tr><tr><td>"
				+ "<h3>Latlong</h3></td><td style='padding-bottom: 5px'><input class='form-control'  type = 'text' id='longlat' name ='latlong' style='padding: 0px;'>"
				+ "</td></tr><tr><td style='padding-top: 20px;padding-bottom: 10px;'>"
				+ "<h3>Preferance</h3></td><td>"
				+ "</td></tr><tr><td style='font-size: 18px'>"
				+ "<input type='checkbox' name='amusement_park' value='amusement_park'>Amusement Park"
				+ "</td>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='restaurant' value='restaurant'>Restaurant"
				+ "</td>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='cafe' value='cafe'>Cafe"
				+ "</td></tr><tr>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='shoping_mall' value='shoping_mall'>Shoping Mall"
				+ "</td>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='tourist_attraction' value='tourist_attraction'>Tourist Attraction"
				+ "</td>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='bar' value='bar'>Bar"
				+ "</td></tr><tr>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='nightclub' value='nightclub'>Night Club"
				+ "</td>"
				+ "<td style='font-size: 18px'><input type='checkbox' name='museum' value='museum'>Museum"
				+ "</td></tr></table>"
				+ "<input type='submit' class='btnbuy' name='ByUser' value='Create User' style='font-size: 20px;margin-top: 10px;margin-left: 320px;float: left;height: 25px;width: 165px;background-color: #800000;'></input>");
		pw.println("<script type='text/javascript' src='geocode.js'></script>");
		//pw.println("<div class='container'><div class='panel panel-primary'><div class='panel-heading'>");
		//pw.println(
		//		"			<h3 class='panel-title'>Address</h3></div><div class='panel-body'><input id='autocomplete' placeholder='Enter your address'");
		//pw.println(
		//		"      onFocus='geolocate()' type='text' class='form-control'>                                                                          ");
		//pw.println(
		//		"      <br>                                                                                                                             ");
		//pw.println(
		//		"   <div id='address'>                                                                                                                  ");
		//pw.println(
		//		"      <div class='row'>                                                                                                                ");
		//pw.println(
		//		"         <div class='col-md-6'>                                                                                                        ");
		//pw.println(
		//		"            <label class='control-label'>Street address</label>                                                                        ");
		//pw.println(
		//		"            <input class='form-control' id='street_number' disabled='true'>                                                            ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"         <div class='col-md-6'>                                                                                                        ");
		//pw.println(
		//		"            <label class='control-label'>Route</label>                                                                                 ");
		//pw.println(
		//		"            <input class='form-control' id='route' disabled='true'>                                                                    ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"      </div>                                                                                                                           ");
		//		pw.println(
		//		"      <div class='row'>                                                                                                                ");
		//pw.println(
		//		"         <div class='col-md-6'>                                                                                                        ");
		//pw.println(
		//		"            <label class='control-label'>City</label>                                                                                  ");
		//pw.println(
		//		"            <input class='form-control field' id='locality' disabled='true'>                                                           ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"         <div class='col-md-6'>                                                                                                        ");
		//pw.println(
		//		"            <label class='control-label'>State</label>                                                                                 ");
		//pw.println(
		//		"            <input class='form-control' id='administrative_area_level_1' disabled='true'>                                              ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"      </div>                                                                                                                           ");
		//pw.println(
		//		"      <div class='row'>                                                                                                                ");
		//pw.println(
		//		"         <div class='col-md-6'>                                                                                                        ");
		//pw.println(
		//		"            <label class='control-label'>Zip code</label>                                                                              ");
		//pw.println(
		//		"            <input class='form-control' id='postal_code' disabled='true'>                                                              ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"         <div class='col-md-6'>                                                                                                        ");
		//pw.println(
		//		"            <label class='control-label'>Country</label>                                                                               ");
		//pw.println(
		//		"            <input class='form-control' id='country' disabled='true'>                                                                  ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"                                                                                                                                       ");
		//pw.println(
		//		"          <div class='col-md-6'>                                                                                                       ");
		//pw.println(
		//		"            <label class='control-label'>LongLat</label>                                                                               ");
		//pw.println(
		//		"            <input class='form-control' id='longlat' disabled='true'>                                                                  ");
		//pw.println(
		//		"         </div>                                                                                                                        ");
		//pw.println(
		//		"      </div>                                                                                                                           ");
		//pw.println(
		//		"   </div>                                                                                                                              ");
		//pw.println(
		//		"</div>                                                                                                                                 ");
		//pw.println(
		//		"  </div>                                                                                                                               ");
		//pw.println(
		//		"</div>                                                                                                                                 ");
		pw.println(
				"<script src='https://maps.googleapis.com/maps/api/js?key=AIzaSyAbgbsd1R1T4yzOzyJrp5uC3YTy1jIWgHg&libraries=places&callback=initAutocomplete' async defer></script> ");
		pw.println("</form>" + "</div></div></div>");
		utility.printHtml("Footer.html");
	}
}
