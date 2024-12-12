package sa.users;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.accomodationsite.AccomodationSite;
import sa.booking.Booking;
import sa.booking.Reserve;
import sa.properties.AmenityType;
import sa.properties.PropertyType;
	


class AdministratorTest {

	AccomodationSite accomodationSite;
	AccomodationSite accomodationSiteEmpty;
	Administrator administrator;
	String fullName;
	int telephone;
	String mail;
	PropertyType propertyType;
	AmenityType amenityType;
	
	Tenant tenant1;
	Tenant tenant2;
	Tenant tenant3;
	
	Reserve reserve1;
	Reserve reserve2;
	Reserve reserve3;
	Reserve reserve4;
	Reserve reserve5;
	
	Booking booking1;
	Booking booking2;
	
	List<Tenant> expectedTenants;
	List<Tenant> tenants;
	List<Reserve> reserves;
	List<Reserve> reserves2;
	
	List<Booking> bookings;

	
	
	
	@BeforeEach
	public void setUp() throws Exception {
		
		
		// acá todo lo relacionado a accomodationSite:
		
		accomodationSite = spy(new AccomodationSite());
		accomodationSiteEmpty = spy(new AccomodationSite());
		
		propertyType = mock(PropertyType.class);
		amenityType =  mock(AmenityType.class);
		
		tenant1 = mock(Tenant.class);
		tenant2 = mock(Tenant.class);
		tenant3 = mock(Tenant.class);
		
		reserve1 = mock(Reserve.class);
		reserve2 = mock(Reserve.class);
		reserve3 = mock(Reserve.class);
		reserve4 = mock(Reserve.class);
		reserve5 = mock(Reserve.class);
		
		booking1 = mock(Booking.class);
		booking2 = mock(Booking.class);
		
		tenants = new ArrayList<Tenant>();
		bookings  = new ArrayList<Booking>();
		
		when(booking1.getReserves()).thenReturn(reserves);
		
		reserves = new ArrayList<Reserve>();
		reserves2 = new ArrayList<Reserve>();
		
		reserves.add(reserve1);
		reserves.add(reserve2);
		reserves.add(reserve3);
		// todo esto para el occupiedReservesTest:
		reserves2.add(reserve4);
		reserves2.add(reserve5);
		
		when(reserve1.getTenant()).thenReturn(tenant1);
		when(reserve2.getTenant()).thenReturn(tenant2);
		when(reserve3.getTenant()).thenReturn(tenant3);
		when(reserve4.getTenant()).thenReturn(tenant1);
		when(reserve5.getTenant()).thenReturn(tenant1);
		
		// todo esto para el occupiedReservesTest:
		when(reserve1.getCheckIn()).thenReturn(LocalDate.of(2024, 11, 25));
		when(reserve1.getCheckOut()).thenReturn(LocalDate.of(2024, 12, 15));
		
		when(reserve2.getCheckIn()).thenReturn(LocalDate.of(2024, 11, 27));
		when(reserve2.getCheckOut()).thenReturn(LocalDate.of(2024, 12, 17));
		
		when(reserve3.getCheckIn()).thenReturn(LocalDate.of(2024, 12, 24));
		when(reserve3.getCheckOut()).thenReturn(LocalDate.of(2025, 1, 1));
		
		when(reserve4.getCheckIn()).thenReturn(LocalDate.of(2024, 11, 25));
		when(reserve4.getCheckOut()).thenReturn(LocalDate.of(2024, 12, 31));
		
		when(reserve5.getCheckIn()).thenReturn(LocalDate.of(2024, 11, 20));
		when(reserve5.getCheckOut()).thenReturn(LocalDate.of(2024, 12, 30));
		
		when(booking1.getReserves()).thenReturn(reserves);
		when(booking2.getReserves()).thenReturn(reserves2);
		// todo esto para el occupiedReservesTest:

		tenants.add(tenant1);
		tenants.add(tenant2);
		tenants.add(tenant3);
		
		expectedTenants = new ArrayList<Tenant>();
		
		expectedTenants.add(tenant1);
		expectedTenants.add(tenant2);
		expectedTenants.add(tenant3);
		
		// todo esto para el occupiedReservesTest:

		bookings.add(booking1);
		bookings.add(booking2);
		
		// acá todo lo relacionado a Administrator:
		fullName = "Nacho";
		telephone = 123;
		mail = "foo@gmail.com";
		
		
		
		// SUT: Administrator
		administrator = new Administrator(fullName, telephone, mail, accomodationSite);
		
	}
	
	

	// Exercise:
	
	@Test
	void getAccomodationSiteTest() {
		assertEquals(accomodationSite, administrator.getAccomodationSite());
	}
	
	@Test
	void setAccomodationSiteTest() {
		administrator.setAccomodationSite(accomodationSite);
		assertEquals(accomodationSite, administrator.getAccomodationSite());
	}
	
	@Test
	void summaryTest() {
		administrator.summary();
	}
	
	@Test
	void addAllowedPropertyTypeTest() {
		administrator.addAllowedPropertyTypes(propertyType);
		assertEquals(accomodationSite.getAllowedProperties().get(0), propertyType);
	}
 	
	@Test
	void allowedAmenitiesTest() {
		administrator.allowedAmenities(amenityType);
		assertEquals(accomodationSite.getAllowedAmenities().get(0), amenityType);
	}
	
	
	
	@Test
	void bestTenantsTest() {
		assertEquals(expectedTenants, administrator.bestTenants(tenants));
	}
	
	@Test
	void occupancyRateTest() {
		when(accomodationSite.getBookings()).thenReturn(bookings);
		assertEquals(80.0, administrator.occupancyRate());
	}
	
	@Test
	void occupancyRateTestWithoutBookings() {
		administrator.setAccomodationSite(accomodationSiteEmpty);
		assertEquals(0.0, administrator.occupancyRate());
	}

	@Test
	void sendEmailTest() {
		administrator.sendEmail(reserve1, "mensaje");
	}
}