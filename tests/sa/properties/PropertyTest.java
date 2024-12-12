package sa.properties;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.users.Owner;

class PropertyTest {

	private Property property;
	private Property propertyReal;
	private double area;
	private String country;
	private String city;
	private String address;
	private int maxGuests;
	private String description;
	private int maxPicture;
	private List<AmenityType> amenities;
	private PropertyType type;
	private List<Picture> pictures;
	private Owner owner;
	private List<Review> reviews;
	private Review rv1;
	private Review rv2;
	List<Review> reviews2; 


	@BeforeEach
	void setUp() throws Exception {

		rv1 = mock(Review.class);
		rv2 = mock(Review.class);
		
		reviews = new ArrayList<Review>();
		reviews.add(rv1);
		reviews.add(rv2);
		reviews2 = new ArrayList<Review>();
	
		when(rv1.getRating()).thenReturn(2);
		when(rv2.getRating()).thenReturn(3);
		
		// SUT con DOCs
		this.property = new Property(area, country, city, address, maxGuests, description, amenities, maxPicture, type, owner, pictures, reviews2);
		
		// SUT real
		this.propertyReal = new Property(area, country, city, address, maxGuests, description, amenities, maxPicture, type, owner);
	}

	@Test
	void testConstructor() {
		assertNotNull(property);
	}

	@Test
	void testConstructorReal() {
		assertNotNull(propertyReal);
	}

	@Test
	void testSummary() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		property.summary();
		assertEquals("Property Summary:" + System.lineSeparator() +
					 "Area: 0.0" + System.lineSeparator() +
					 "Country: null" + System.lineSeparator() +
					 "City: null" + System.lineSeparator() +
					 "Address: null" + System.lineSeparator() +
					 "Max Guests: 0" + System.lineSeparator() +
					 "Description: null" + System.lineSeparator() +
					 "Max Pictures: 0" + System.lineSeparator() +
					 "Type: null" + System.lineSeparator() +
					 "Owner: null" + System.lineSeparator() +
					 "Amenities: null" + System.lineSeparator() +
					 "Pictures: null" + System.lineSeparator() +
					 "Reviews: []"
					 + System.lineSeparator(), outContent.toString());
	}

	@Test
	void testGetCity() {
		assertEquals(city, property.getCity());
	}
	
	@Test
	void testGetRank() {
		assertEquals(0.0, property.getRank());
	}
	
	@Test
	void testGetMaxGuests() {
		assertEquals(maxGuests, property.getMaxGuests());
	}
	
	@Test
	void testGetAmenities() {
		assertEquals(amenities, property.getAmenities());
	}
	
	@Test
	void testGetPropertyType() {
		assertEquals(type, property.getPropertyType());
	}
	
	@Test
	void testGetOwner() {
		assertEquals(owner, property.getOwner());
	}

	@Test
	void testGetReviews() {
		assertEquals(reviews2, property.getReviews());
	}
	
	@Test
	void getAreaTest() {
		assertEquals(this.area, property.getArea());
	}
	
	@Test
	void getCountryTest() {
		assertEquals(this.country, property.getCountry());
	}
	
	@Test
	void getAddress() {
		assertEquals(this.address, property.getAddress());
	}
	
	@Test
	void getDescription() {
		assertEquals(this.description, property.getDescription());
	}
	
	@Test
	void getMaxPictureTest() {
		assertEquals(this.maxPicture, property.getMaxPicture());
	}
	
	@Test
	void getTypeTest() {
		assertEquals(this.type, property.getType());
	}
	
	@Test
	void getPicturesTest() {
		assertEquals(this.pictures, property.getPictures());
	}
	
}
