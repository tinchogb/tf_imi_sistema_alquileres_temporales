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

class MaxPriceTest {

	private MaxPrice querytest1;
	private MaxPrice querytest2;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	
	private LocalDate dateReserve;
	@BeforeEach
	void setUp() throws Exception {
		dateReserve = LocalDate.of(2024, 11, 25);
		
		querytest1 = new MaxPrice(1234,dateReserve);
		querytest2 = new MaxPrice(1200,dateReserve); 
		
		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		
		when(bookingMock.price(dateReserve)).thenReturn((double) 1233);
		
		bookings.add(bookingMock);
	}

	@Test
	void newMaxPriceQueryTest() {
		querytest1 = new MaxPrice(1234,dateReserve);
	}
	@Test
	void getMaxPriceTest() {
		 assertEquals(querytest1.getMaxPrice(),1234);
		 assertEquals(querytest2.getMaxPrice(),1200);
	}
	@Test 
	void successfullQuerySearchTest() {
		assertEquals(querytest1.search(bookings).size(),1);
	}
	
	@Test
	void failedlQuerySearchTest() {
		assertEquals(querytest2.search(bookings).size(),0);
	}

}
