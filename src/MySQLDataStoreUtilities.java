import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class MySQLDataStoreUtilities {
	public static Connection conn = null;
	public String query = null;
	public static String message = "";

	public static String getConnection() {

		try {
			Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SocialRecommendation?useUnicode=true&characterEncoding=utf-8", "root",
					"vinit");
			message = "Connection Successfull";
			return message;
		} catch (SQLException ex) {
			message = "NoConnection";
			return message;

		} catch (Exception e) {
			message = e.getMessage();
			return message;

		}
	}

	public static boolean insertUser(String username, String password, String rePassword, String userType) {
		try {

			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO registration(username,password,repassword,usertype) "
					+ "VALUES (?,?,?,?);";

			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1, username);
			pst.setString(2, password);
			pst.setString(3, rePassword);
			pst.setString(4, userType);
			pst.execute();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}

		return true;
	}

	public static HashMap<String, User> selectUser() {
		HashMap<String, User> hm = new HashMap<String, User>();
		try {
			getConnection();
			Statement stmt = conn.createStatement();
			String selectCustomerQuery = "select * from registration";
			ResultSet rs = stmt.executeQuery(selectCustomerQuery);
			while (rs.next()) {
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("usertype"));
				hm.put(rs.getString("username"), user);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hm;
	}

	
}