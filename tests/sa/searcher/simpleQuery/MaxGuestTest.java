package sa.searcher.simpleQuery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.properties.Property;


class MaxGuestTest {

	private MaxGuest querytest1;
	private MaxGuest querytest2;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	
	private Property house;
	
	
	@BeforeEach
	void setUp() throws Exception {
		querytest1 = new MaxGuest(3);
		querytest2 = new MaxGuest(5);
		
		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		house = mock(Property.class);
		
		when(bookingMock.getProperty()).thenReturn(house);
		when(house.getMaxGuests()).thenReturn(4);
		
		bookings.add(bookingMock);
	}

	@Test
	void newMaxGuestQuery() {
		assertNotNull(querytest1);
		assertNotNull(querytest2);
	}
	@Test
	void getMaxGuestsTest() {
		assertEquals(querytest1.getMaxGuests(),3);
		assertEquals(querytest2.getMaxGuests(),5);
	}
	@Test
	void successfullQuerySearchTest() {
		assertEquals(querytest1.search(bookings).size(),1);
	}
	@Test
	void failedQuerySearchTest() {
		assertEquals(querytest2.search(bookings).size(),0);
	}

	
	
	
	
	
	

}
