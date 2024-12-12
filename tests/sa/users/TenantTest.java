package sa.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Reserve;
import sa.properties.Property;
import sa.properties.Review;

class TenantTest {
		
		Review rv1;
		Review rv2;
		List<Review> reviews;
		Tenant tenant;
		List<Reserve> reserves;
		Reserve declinedReserve;
		Reserve reserve;
		Property property;
		Owner owner;

		@BeforeEach
		void setUp() throws Exception {
			
			
			rv1 = mock(Review.class);
			rv2 = mock(Review.class);

			when(rv1.getRating()).thenReturn(2);
			when(rv2.getRating()).thenReturn(3);
			
			tenant = new Tenant("nacho", 123, "bar@gmail.com");
			
			tenant.getReviews().add(rv1);
			tenant.getReviews().add(rv2);
			
			reserves = new ArrayList<Reserve>();
		
			declinedReserve = mock(Reserve.class);
			
			reserve = mock(Reserve.class);
			
			property = mock(Property.class);
			
			owner = mock(Owner.class);
		}

		
	@Test
	void testRank() {
		assertEquals(2.5, tenant.getRank());
	}
	
	@Test
	void testSummary() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		tenant.summary();
		assertEquals("Tenant Summary:" + System.lineSeparator()
                 	+  "name: nacho" + System.lineSeparator()
                 	+ "telephone: 123" + System.lineSeparator()
                 	+ "mail: bar@gmail.com" + System.lineSeparator(), outContent.toString());
	}
	
	@Test
	void getReservesTest() {
		assertEquals(reserves, tenant.getReserves());
	}

	@Test
	void reserveDeclinedTest() {
		tenant.reserveDeclined(declinedReserve);
	}
	
	@Test
	void reserveApprovedTest() {
		tenant.reserveApproved(reserve);
		assertEquals(1, tenant.getReserves().size());
	}
	
	@Test
	void qualifyPropertyTest() {
		tenant.qualify(property);
	}
	
	@Test
	void qualifyOwnerTest() {
	tenant.qualify(owner);
	}

	@Test
	void sendEmailTest() {
	tenant.sendEmail(reserve, "mensaje");
	}
}
