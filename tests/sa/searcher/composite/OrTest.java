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

import sa.searcher.simpleQuery.CheckIn;
import sa.searcher.simpleQuery.City;

import sa.searcher.simpleQuery.MaxGuest;

import sa.searcher.simpleQuery.MinPrice;

class OrTest {

	private Or querytest1;
	private Or querytest2;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	
	private City cityQuery;
	private MaxGuest maxGuestQuery;
	private MinPrice minPriceQuery;
	private CheckIn checkInQuery;
	private Property house;
	
	private LocalDate startDate;
	private Period bookingPeriod;
	private LocalDate checkInDate;
	
	private City spyCityQuery;
	private MaxGuest spyMaxGuestQuery;
	private MinPrice spyMinPriceQuery;
	private CheckIn spyCheckInQuery;
	@BeforeEach
	void setUp() throws Exception {
		cityQuery = new City("Buenos Aires");
		spyCityQuery = spy(cityQuery);
		
		maxGuestQuery = new MaxGuest(5);
		spyMaxGuestQuery = spy(maxGuestQuery);
		
		
		startDate = LocalDate.of(2024, 11, 20);
		checkInDate = LocalDate.of(2024, 11, 20);
		
		
		minPriceQuery = new MinPrice(1238.0,checkInDate);
		spyMinPriceQuery =spy(minPriceQuery);
		
		checkInQuery = new CheckIn(checkInDate);
		spyCheckInQuery = spy(checkInQuery);
		
		querytest1 = new Or(spyCityQuery,spyMaxGuestQuery);
		querytest2 = new Or(spyMinPriceQuery,spyCheckInQuery);
		
		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		
		house = mock(Property.class);
		
		bookingPeriod = mock(Period.class);
		
		
		when(bookingPeriod.start()).thenReturn(startDate); 
		
		when(house.getCity()).thenReturn("Buenos Aires");
		when(house.getMaxGuests()).thenReturn(5);
		
		when(bookingMock.getProperty()).thenReturn(house);
		when(bookingMock.getPeriod()).thenReturn(bookingPeriod);
		when(bookingMock.price(checkInDate)).thenReturn(1230.0);
		when(bookingMock.isAvailableDate(checkInDate)).thenReturn(true);
		when(bookingPeriod.belongs(checkInDate)).thenReturn(true);
		
		bookings.add(bookingMock);
	}

	@Test
	void newOrQueryTest() {
		assertNotNull(querytest1);
		assertNotNull(querytest2);
	}
	@Test
	void firstQuerySearchTest() {
		assertEquals(querytest1.search(bookings).size(),1);
	}
	@Test
	void secondQuerySearchTest() {
		assertEquals(querytest2.search(bookings).size(),1);
	}
	
	

}
