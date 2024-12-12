package sa.observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import sa.booking.Booking;
import sa.booking.Reserve;
import sa.properties.Property;

class ReserveObserverTest {

	private ReserveObserver observertest;
	private Booking bookingMock;
	private Reserve reserveMock;
	@BeforeEach
	void setUp() throws Exception {
		observertest = new ReserveObserver();
		bookingMock = mock(Booking.class);
		reserveMock = mock(Reserve.class);
	}

	@Test
	void newReserveObserverTest() {
		ReserveObserver observer = new ReserveObserver();
		assertNotNull(observer);
	}
	
	@Test
	void updateObserver()
	{
		Property auxProperty = mock(Property.class);
		
		when(bookingMock.getProperty()).thenReturn(auxProperty);
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		observertest.updateNewReserve(reserveMock);
		
		
		verify(auxProperty).summary();
	} 
	@Test
	void unusedMethodUpdateCancellation() {
		observertest.updateCancellation(reserveMock);
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		verifyNoInteractions(reserveMock.getBooking());
	}
	@Test
	void unusedMethodUpdateLowerPrice() {
		observertest.updateLowerPrice(bookingMock);
		Property propertyMock = mock(Property.class);
		when(bookingMock.getProperty()).thenReturn(propertyMock);
		verifyNoInteractions(bookingMock.getProperty());
	}

}
