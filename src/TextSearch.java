import com.google.gson.annotations.SerializedName;

public class TextSearch {
	
	@SerializedName("formatted_address")
	private String formatted_address;
	
	@SerializedName("icon")
	private String icon;
	
	@SerializedName("name")
	private String name;
	
	@SerializedName("rating")
	private String rating;
	
	@SerializedName("user_ratings_total")
	private String user_ratings_total;
	
	
	public String getFormatted_address() {
		return formatted_address;
	}
	public void setFormatted_address(String formatted_address) {
		this.formatted_address = formatted_address;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRating() {
		return rating;
	}
	public void setRating(String rating) {
		this.rating = rating;
	}
	public String getUser_ratings_total() {
		return user_ratings_total;
	}
	public void setUser_ratings_total(String user_ratings_total) {
		this.user_ratings_total = user_ratings_total;
	}
	
	
}
