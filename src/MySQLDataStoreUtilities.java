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
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/SocialRecommendation?useUnicode=true&characterEncoding=utf-8", "root",
					"root");
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

	public static boolean insertUser(String username, String email,String preference,String password, String rePassword, String  streetaddress,
			String cityname,String state,String zipcode,String country,String latlong) {
		try {
			HashMap<String, User> map_Insertuser= new HashMap<String, User>();
			getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO registration VALUES ("
					+ "?,?,?,?,?,?,?,?,?,?,?,?);";
			String pattern = "MM-dd-yyyy";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

			String date = simpleDateFormat.format(new Date());
			System.out.println(date);
			
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1, username);
			pst.setString(2, email);
			pst.setString(3, email);
			pst.setString(4, password);
			pst.setString(5, streetaddress);
			pst.setString(6, cityname);
			pst.setString(7, state);
			pst.setString(8, zipcode);
			pst.setString(9, country);
			pst.setString(10, preference);
			pst.setString(11, String.valueOf(date));
			pst.setString(12, latlong);
			pst.execute();
			User user = new User(latlong);
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
				User user = new User(rs.getString("username"), rs.getString("password"), rs.getString("email"),rs.getString("longlat"),rs.getString("userid"));
				hm.put(rs.getString("userid"), user);
			}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return hm;
	}
	
	/* Nitika */
	public static String insertReview(String name,String streetaddress,String reviewrating,String comment,String userid) {
		try {
			HashMap <String, Review> hm = new HashMap<String, Review>();
			getConnection();
			String insertreviewquery = "INSERT INTO Review (name, streetaddress,reviewrating,comment,userid)"
					+ "VALUES( ?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertreviewquery);
			pst.setString(1, name);
			pst.setString(2, streetaddress);
			pst.setString(3, reviewrating);
			pst.setString(4, comment);
			pst.setString(5, userid);
			pst.execute();
			return "Successfull"; 
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			return "UnSuccessfull";
		}
	}
	
	public static HashMap<String, ArrayList<Review>> selectReview(String Name)
	{	
		HashMap<String, ArrayList<Review>> reviews=new HashMap<String, ArrayList<Review>>();;
		try
		{
		getConnection();
		Statement stmt = conn.createStatement();
		String selectReviewQuery = "select * from Review where name = '"+Name+"'";
		System.out.println("selectReviewQuery" + selectReviewQuery);
		ResultSet rs = stmt.executeQuery(selectReviewQuery);
		ArrayList<Review> listReview = new ArrayList<Review>();
		while (rs.next()) {			
//			   if(!reviews.containsKey(rs.getString("Name")))
//				{	
//					ArrayList<Review> arr = new ArrayList<Review>();
//					reviews.put(rs.getString("Name"), arr);
//				}
				//ArrayList<Review> listReview = reviews.get(rs.getString("Name"));	
				
				Review review = new Review(rs.getString("Name"), rs.getString("streetaddress"),rs.getString("reviewrating"),rs.getString("comment"),rs.getString("userid"));
				listReview.add(review);	
				reviews.put(rs.getString("Name"), listReview);
				
				}
		//return reviews;
			}
			catch(Exception e)
			{	
//				reviews=null;
//				return reviews;
				System.out.println(e.getMessage());
			}	
			return reviews;
	}
	

	
}