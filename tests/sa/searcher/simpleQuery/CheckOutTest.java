package sa.searcher.simpleQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.Period;


class CheckOutTest {

	private CheckOut querytest1;
	private CheckOut querytest2;
	
	private Booking bookingMock;
	
	private List<Booking> bookings;
	
	private Period bookingPeriod;
	
	private LocalDate fechaInicio; 
	private LocalDate fechaFin;
	
	@BeforeEach
	void setUp() throws Exception {
		
		querytest1 = new CheckOut(LocalDate.of(2024, 11, 20));
		querytest2 = new CheckOut(LocalDate.of(2024, 8, 17));
		
		bookingMock = mock(Booking.class);
		
		bookingPeriod = mock(Period.class);
		
		bookings = new ArrayList<Booking>();
		
		fechaInicio = LocalDate.of(2024, 10, 10);
		fechaFin = LocalDate.of(2024, 12, 31);
		
		when(bookingPeriod.start()).thenReturn(fechaInicio);
		when(bookingPeriod.end()).thenReturn(fechaFin);
		when(bookingMock.getPeriod()).thenReturn(bookingPeriod);
		
		bookings.add(bookingMock);
	}

	@Test
	void newCheckOutTest() {
		assertNotNull(querytest1);
		assertNotNull(querytest2);
	}
	@Test
	void getCheckOutDateTest() {
		assertEquals(querytest1.getCheckOutDate(), LocalDate.of(2024, 11, 20));
		assertEquals(querytest2.getCheckOutDate(), LocalDate.of(2024, 8, 17));
	}
	@Test
	void succesfulQuerySearchTest() {
		when(bookingMock.getPeriod().belongs(LocalDate.of(2024, 11, 20))).thenReturn(true);
		verify(bookingMock).getPeriod();
		
		assertEquals(this.querytest1.search(bookings).size(),1);
	}
	@Test
	void failedQuerySearchTest() {
		when(bookingMock.getPeriod().belongs(LocalDate.of(2024, 8, 17))).thenReturn(false);
		verify(bookingMock).getPeriod();
		
		assertEquals(this.querytest2.search(bookings).size(),0);
	}

}