package sa.searcher.composite;


import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.Period;
import sa.properties.Property;

import sa.searcher.simpleQuery.City;
import sa.searcher.simpleQuery.MaxGuest;
import sa.searcher.simpleQuery.MinPrice;
import sa.searcher.simpleQuery.ReservePeriod;

class AndTest {

	private And querytest1;
	private And querytest2;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	
	
	private City cityQuery;
	private MaxGuest maxGuestQuery;
	private MinPrice minPriceQuery;
	private ReservePeriod reservePeriodQuery;
	private Property house;
	
	private LocalDate startDate;
	private Period bookingPeriod;
	private LocalDate checkInDate;
	private LocalDate checkOutDate;
	
	private City spyCityQuery;
	private MaxGuest spyMaxGuestQuery;
	private MinPrice spyMinPriceQuery;
	private ReservePeriod spyReservePeriodQuery;
	
	
	@BeforeEach
	void setUp() throws Exception {
		cityQuery = new City("Buenos Aires");
		spyCityQuery = spy(cityQuery);
		
		maxGuestQuery = new MaxGuest(5);
		spyMaxGuestQuery = spy(maxGuestQuery);
		
		minPriceQuery = new MinPrice(1238.0,checkInDate);
		spyMinPriceQuery =spy(minPriceQuery);
		
		reservePeriodQuery = new ReservePeriod(checkInDate, checkOutDate);
		spyReservePeriodQuery = spy(reservePeriodQuery);
		
		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		
		house = mock(Property.class);
		
		bookingPeriod = mock(Period.class);
		startDate = LocalDate.of(2024, 11, 20);
		checkInDate = LocalDate.of(2024, 11, 19);
		checkOutDate = checkInDate.plusDays(1);
		
		when(bookingPeriod.start()).thenReturn(startDate); 
		
		when(house.getCity()).thenReturn("Buenos Aires");
		when(house.getMaxGuests()).thenReturn(5);
		
		when(bookingMock.getProperty()).thenReturn(house);
		when(bookingMock.getPeriod()).thenReturn(bookingPeriod);
		when(bookingMock.price(checkInDate)).thenReturn(1239.0);
		when(bookingMock.price(checkOutDate)).thenReturn(1239.0);

		
		bookings.add(bookingMock);

		// SUT
		querytest1 = new And(spyCityQuery,spyMaxGuestQuery);
		querytest2 = new And(spyMinPriceQuery,spyReservePeriodQuery);		
	}

	@Test
	void newAndQueryTest() {
		assertNotNull(querytest1);
		assertNotNull(querytest2);
	} 
	@Test
	void successfulQuerySearchTest() {
		assertEquals(querytest1.search(bookings).size(),1);
	}
	@Test
	void failedQuerySearchTest() {
		assertEquals(querytest2.search(bookings).size(),0);
	}
		
	

		
		

		
		
		
		




		
		
	}


