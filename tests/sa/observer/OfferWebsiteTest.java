package sa.observer;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import sa.booking.Booking;
import sa.booking.Pricer;
import sa.booking.Reserve;
import sa.properties.Property;
import sa.properties.PropertyType;

class OfferWebsiteTest {

	private OfferWebSite observertest;
	
	private Booking bookingMock;
	private ObjectPublisher publisher;
	
	private PropertyType type;
	private Property propertyMock;
	private Pricer pricer;
	
	@BeforeEach
	void setUp() throws Exception {
		
		observertest = new OfferWebSite();
		
		
		bookingMock = mock(Booking.class);
		publisher = mock(ObjectPublisher.class);
		type = mock(PropertyType.class);
		propertyMock = mock(Property.class);
		pricer = mock(Pricer.class);
		
		
		observertest.setPublisher(publisher);
		
		
		when(bookingMock.getProperty()).thenReturn(propertyMock);
		when(bookingMock.getPricer()).thenReturn(pricer);
		when(pricer.getBasePrice()).thenReturn(1234.0);
		when(propertyMock.getPropertyType()).thenReturn(type);
		when(type.type()).thenReturn("Departamento");
	}

	@Test
	void newObserverTest() {
		assertNotNull(observertest);
	}
	
	@Test
	void observerGettingNotified() {
		observertest.updateLowerPrice(bookingMock);
	}
	@Test
	void unusedMethodUpdateCancellationTest() {
		Reserve reserveMock = mock(Reserve.class); 
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		
		observertest.updateCancellation(reserveMock);
		
		verifyNoInteractions(reserveMock.getBooking());
	}
	@Test
	void unusedMethodUpdateNewReserveTest() {
		Reserve reserveMock = mock(Reserve.class); 
		when(reserveMock.getBooking()).thenReturn(bookingMock);
		
		observertest.updateNewReserve(reserveMock);
		verifyNoInteractions(reserveMock.getBooking());
	}
 
}
