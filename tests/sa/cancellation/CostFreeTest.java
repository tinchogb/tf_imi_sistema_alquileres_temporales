package sa.cancellation;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import sa.booking.Booking;
import sa.booking.Reserve;


class CostFreeTest {

	private CostFree cancellationtest;
	
	private Reserve reserveMock;
	private LocalDate dateTest;
	private Booking bookingMock;
	
	@BeforeEach
	void setUp() throws Exception {
		cancellationtest = new CostFree();
		
		reserveMock = mock(Reserve.class);
		dateTest = LocalDate.of(2024, 10, 10);
		
		bookingMock = mock(Booking.class);
		
		
	}

	@Test
	void newCancellationTest() {
		assertNotNull(cancellationtest);
	}
	@Test
	void cancellation10DaysBeforeCheckIn() {
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 21));
		assertTrue(dateTest.isBefore(reserveMock.getCheckIn()));
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock).setPrice(0.0);
	}
	@Test
	void cancellation5DaysBeforeCheckIn() {
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 16));
		when(reserveMock.getCheckOut()).thenReturn(LocalDate.of(2024, 10, 22));
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		
		when(bookingMock.price(LocalDate.of(2024, 10, 16))).thenReturn(10.5);
		when(bookingMock.price(LocalDate.of(2024, 10, 17))).thenReturn(10.5);
		
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock).setPrice(21.0);
	}
	@Test
	void cancellation1DayReserve5DaysBeforeCheckIn() {
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 16));
		when(reserveMock.getCheckOut()).thenReturn(LocalDate.of(2024, 10, 16));
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		
		when(bookingMock.price(LocalDate.of(2024, 10, 16))).thenReturn(20.5);
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock).setPrice(41.0);
	}
	

}
