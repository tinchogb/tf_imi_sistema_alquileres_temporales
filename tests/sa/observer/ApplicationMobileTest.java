package sa.observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;



import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import sa.booking.Booking;
import sa.booking.Reserve;



import sa.properties.Property;
import sa.properties.PropertyType;

class ApplicationMobileTest {

private ApplicationMobile observertest;
	
	private Reserve reserveMock;
	private Booking bookingMock;
	private Property propertyMock;
	private PropertyType type;
	private ObjectScreen screenMock;
	
	@BeforeEach
	void setUp() throws Exception {
		observertest = new ApplicationMobile();
		
		reserveMock = mock(Reserve.class);
		
		bookingMock = mock(Booking.class);
		
		propertyMock = mock(Property.class);
		type = mock(PropertyType.class);
		screenMock = mock(ObjectScreen.class);
		
		observertest.setScreen(screenMock);
		
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		when(bookingMock.getProperty()).thenReturn(propertyMock);
		when(propertyMock.getPropertyType()).thenReturn(type);
		when(type.type()).thenReturn("Casa Quinta");
	}

	@Test
	void newObserverTest() {
		assertNotNull(observertest);
	}
	
	@Test
	void observerGettingNotified() {
		
		observertest.updateCancellation(reserveMock);
		//verify(propertyMock);
		assertNotNull(observertest.getScreen());
		verify(observertest.getScreen()).popUp(anyString(), anyString(), anyInt());
	}
	
	@Test
	void unusedMethodUpdateLowerPrice() {
		observertest.updateLowerPrice(bookingMock);
		verifyNoInteractions(bookingMock.getProperty());
	}
	@Test
	void unusedMethodUpdateNewReserve() {
		observertest.updateNewReserve(reserveMock);
		verifyNoInteractions(reserveMock.getBooking());
	}
	
	 
}
