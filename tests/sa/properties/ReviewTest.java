package sa.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReviewTest {
	
	String review;
	int rating;
	Review reviewInstance;

	@BeforeEach
	void setUp() throws Exception {
		
		review = "votacion propiedad";
		rating = 10;
		reviewInstance = new Review(review, rating);
	}

	@Test
	void getReviewTest() {
		assertEquals("votacion propiedad", reviewInstance.getReview());
	}
	
	@Test
	void getRatingTest() {
		assertEquals(10, reviewInstance.getRating());
	}

}
