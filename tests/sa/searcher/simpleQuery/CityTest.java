package sa.searcher.simpleQuery;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import sa.booking.Booking;
import sa.properties.Property;


class CityTest {
	
	private City querytest1;
	private City querytest2;
	
	private Booking bookingMock;
	private List<Booking> bookings;
	
	private String city1;
	private String city2;
	
	private Property house;
	
	@BeforeEach
	void setUp() throws Exception {
		city1 = "Buenos Aires";
		querytest1 = new City(city1);
		
		city2 ="Cordoba";
		querytest2 = new City(city2);
		
		house = mock(Property.class);
		
		bookingMock = mock(Booking.class);
		bookings = new ArrayList<Booking>();
		
		when(bookingMock.getProperty()).thenReturn(house);
		when(house.getCity()).thenReturn(city1);
		
		bookings.add(bookingMock);
		
		
	}

	@Test
	void newCityQuery() {
		assertNotNull(querytest1);
		assertNotNull(querytest2);
	}
	@Test
	void getNameCityTest() {
		assertEquals(querytest1.getNameCity(), "Buenos Aires");
		assertEquals(querytest2.getNameCity(), "Cordoba");
	}
	
	@Test
	void successfullQuerySearchTest() {
		assertEquals(querytest1.search(bookings).size(),1);
	}

	@Test
	void failedQuerySearchTest()
	{
		assertEquals(querytest2.search(bookings).size(),0);

	}
	
	
}
