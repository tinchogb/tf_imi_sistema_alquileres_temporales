package sa.properties;

public class Review {
	private String review;
	private int rating;
	
	public Review(String review, int rating) {
		this.review = review;
		this.rating = rating;
	}
	
	public String getReview() {
		return this.review;
	}
	
	public int getRating() {
		return this.rating;
	}
	
	
}
