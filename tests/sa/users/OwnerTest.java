package sa.users;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import sa.booking.Period;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.Reserve;
import sa.properties.Review;
import sa.properties.Property;

class OwnerTest {
	
	Review rv1;
	Review rv2;
	List<Review> reviews;
	Owner owner;
	Reserve requestedReserve;
	Booking booking;
	Tenant tenant;
	Period period;
	List<Property> properties;

	@BeforeEach
	void setUp() throws Exception {
		
		
		rv1 = mock(Review.class);
		rv2 = mock(Review.class);

		when(rv1.getRating()).thenReturn(2);
		when(rv2.getRating()).thenReturn(3);
		
		owner = new Owner("nacho", 123, "bar@gmail.com");
		tenant = spy(new Tenant("martin", 234, "@gmail.com"));
		
		owner.getReviews().add(rv1);
		owner.getReviews().add(rv2);
		
		booking = mock(Booking.class);
		tenant = mock(Tenant.class);
		period = mock(Period.class);
		
		requestedReserve = spy(new Reserve(booking, tenant, period));
		properties = new ArrayList<Property>();
	}

	@Test
	void testRank() {
		
		assertEquals(2.5, owner.getRank());
	}

	@Test
	void summaryTest() {
		
		owner.summary();
	}
	
	@Test
	void getPropertiesTest() {
		
		assertEquals(properties, owner.getProperties());
	}
	
	@Test
	void reserveRequestedTest() {
		
		owner.reserveRequested(requestedReserve);
		assertEquals(owner.getReserveRequested(), requestedReserve);
		verify(requestedReserve, times(1)).getTenant();
		
	}
	
	@Test
	void setReserveRequested() {
		
		owner.setReserveRequested(requestedReserve);
		assertEquals(requestedReserve, owner.getReserveRequested());
	}
	
	@Test
	void getReserveRequested() {
		
		owner.setReserveRequested(requestedReserve);
		assertEquals(requestedReserve, owner.getReserveRequested());
	}
	
	@Test
	void cleanRequestedReserve() {
		
		owner.setReserveRequested(requestedReserve);
		owner.cleanRequestedReserve();
		assertNull(owner.getReserveRequested());
	}
	
	@Test
	void qualifyTenantTest() {
		owner.qualify(tenant);
	}
	
	@Test
	void sendEmailTest() {
	tenant.sendEmail(requestedReserve, "mensaje");
	}
}
