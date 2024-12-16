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

class OrTest {

	private Or querytest1;
	private Or querytest2;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	
	private City cityQuery;
	private MaxGuest maxGuestQuery;
	private MinPrice minPriceQuery;
	private ReservePeriod reservePeriodQuery;
	private Property house;
	
	private LocalDate startDate;
	private LocalDate endDate;
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
		
		
		startDate = LocalDate.of(2024, 11, 20);
		checkInDate = LocalDate.of(2024, 11, 20);
		checkOutDate = checkInDate.plusDays(1);
		
		minPriceQuery = new MinPrice(1229.0, checkInDate);
		spyMinPriceQuery =spy(minPriceQuery);
		
		reservePeriodQuery = new ReservePeriod(checkInDate, checkOutDate);
		spyReservePeriodQuery = spy(reservePeriodQuery);

		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		
		house = mock(Property.class);
		
		bookingPeriod = mock(Period.class);
		
		
		when(bookingPeriod.start()).thenReturn(startDate);
		when(bookingPeriod.end()).thenReturn(endDate);
		
		when(house.getCity()).thenReturn("Buenos Aires");
		when(house.getMaxGuests()).thenReturn(5);
		
		when(bookingMock.getProperty()).thenReturn(house);
		when(bookingMock.getPeriod()).thenReturn(bookingPeriod);
		when(bookingMock.price(checkInDate)).thenReturn(1230.0);
		when(bookingMock.price(checkOutDate)).thenReturn(1239.0);
		when(bookingMock.isAvailableDate(checkInDate)).thenReturn(true);
		when(bookingMock.isAvailableDate(checkOutDate)).thenReturn(true);
		when(bookingPeriod.belongs(checkInDate)).thenReturn(true);
		when(bookingPeriod.belongs(checkOutDate)).thenReturn(true);
		
		bookings.add(bookingMock);

		// SUT
		querytest1 = new Or(spyCityQuery,spyMaxGuestQuery);
		querytest2 = new Or(spyMinPriceQuery,spyReservePeriodQuery);
	}

	@Test
	void newOrQueryTest() {
		assertNotNull(querytest1);
		assertNotNull(querytest2);
	}

	@Test
	void firstQuerySearchTest() {
		assertEquals(1, querytest1.search(bookings).size());
	}

	@Test
	void secondQuerySearchTest() {
		assertEquals(1, querytest2.search(bookings).size());
	}
}
