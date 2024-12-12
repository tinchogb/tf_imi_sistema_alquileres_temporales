package sa.searcher.simpleQuery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;

class MinPriceTest {

	private MinPrice querytest1;
	private MinPrice querytest2;
	
	private LocalDate dateReserve;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	@BeforeEach
	void setUp() throws Exception {
		dateReserve = LocalDate.of(2024, 11, 20);
		
		querytest1 = new MinPrice(1234,dateReserve);
		querytest2 = new MinPrice(1240,dateReserve); 
		
		
		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		
		when(bookingMock.price(dateReserve)).thenReturn((double) 1235);
		
		bookings.add(bookingMock);
	}

	@Test
	void newMinPriceQueryTest() {
		querytest1 = new MinPrice(4321,dateReserve);
	}
	@Test
	void getMinPriceTest() {
		assertEquals(querytest1.getMinPrice(),1234);
		assertEquals(querytest2.getMinPrice(),1240);
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
