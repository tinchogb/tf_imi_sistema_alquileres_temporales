package sa.accomodationsite;

import static org.junit.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.PaymentMethod;
import sa.booking.SpecialPeriod;
import sa.properties.AmenityType;
import sa.properties.Property;
import sa.properties.PropertyType;
import sa.searcher.composite.And;
import sa.searcher.composite.Or;
import sa.searcher.simpleQuery.IQuery;
import sa.users.Tenant;
import sa.booking.Reserve;
import sa.booking.reserveStates.Timer;
import sa.cancellation.ICancellationPolicy;


public class AccomodationSiteTest {
	
	
	// inicializacion de variables: setUp
	
	// variables para el metodo getOccupiedReserves, tambien utilizadas para el allReservesOfTheTenant:
	AccomodationSite accomodationSite;
	AccomodationSite accomodationSiteReal;
	Booking booking1;
	Booking booking2;
	Reserve reserve1;
	Reserve reserve2;
	Reserve reserve3;
	List<Reserve> expectedReserves;
	
	// variables para el metodo allReservesOfTheTenant:
	List<Reserve> reservesOfTheTenant;
	Tenant tenant;
	
	
	// variables para el metodo futureReservesOfTheTenant
	List<Reserve> futureReserves;
	
	
	// variables para el metodo futureReservesOfTheTenant
	List<Booking> bookings;
	
	// variables para el metodo addBooking  para el que es solo para el SUT
	
	Booking booking3;
	
	// todo para el create booking:
	
	Property property;
	Property property2;
	LocalDate begin;
	LocalDate end;
	
	List<SpecialPeriod> periods;
	SpecialPeriod period1;
	SpecialPeriod period2;
	
	List<PaymentMethod> paymentsMethods;
	PaymentMethod payment1;
	PaymentMethod payment2;
	
	AmenityType electricity;
	AmenityType internet;
	PropertyType house;
	AmenityType agua;
	PropertyType duplex;
	Timer timer;
	ICancellationPolicy policy;

	List<AmenityType> amenityTypes = new ArrayList<AmenityType>();
	List<PropertyType> propertyTypes = new ArrayList<PropertyType>();

	// todo para el allBookingCities
	
	Set<String> visitedCities;
	List<Reserve> expectedReservesBooking2;
	
	// para el getAllReserves:
	
	List<Reserve> allReserves;
	
	// para el metodo search:
	
	IQuery query;
	IQuery query2;
	List<Booking> emptyList;
	
	
	// Exercise:
	
	@BeforeEach
	public void setUp() throws Exception {
		booking1 = mock(Booking.class);
		booking2 = mock(Booking.class);
		booking3 = mock(Booking.class);
		
		reserve1 = mock(Reserve.class);
		reserve2 = mock(Reserve.class);
		reserve3 = mock(Reserve.class);
		tenant = mock(Tenant.class);
		
		reservesOfTheTenant = new ArrayList<Reserve>();
		expectedReserves = new ArrayList<Reserve>();
		expectedReservesBooking2 = new ArrayList<Reserve>();
		futureReserves = new ArrayList<Reserve>();
		bookings = new ArrayList<Booking>();
		
		expectedReservesBooking2.add(reserve1);
		expectedReservesBooking2.add(reserve2);
		
		expectedReserves.add(reserve1);
		expectedReserves.add(reserve2);
		expectedReserves.add(reserve1);
		expectedReserves.add(reserve2);
		
		
		reservesOfTheTenant.add(reserve1);
		reservesOfTheTenant.add(reserve2);
		reservesOfTheTenant.add(reserve1);
		reservesOfTheTenant.add(reserve2);
		
		bookings.add(booking1);
		bookings.add(booking2);
		
		
		when(reserve1.getCheckIn()).thenReturn(LocalDate.of(2024, 11, 25));
		when(reserve1.getCheckOut()).thenReturn(LocalDate.of(2024, 12, 15));
		when(reserve1.getBooking()).thenReturn(booking1);
		
		when(reserve2.getCheckIn()).thenReturn(LocalDate.of(2024, 11, 27));
		when(reserve2.getCheckOut()).thenReturn(LocalDate.of(2024, 12, 17));
		when(reserve2.getBooking()).thenReturn(booking2);
		
		when(reserve3.getCheckIn()).thenReturn(LocalDate.of(2024, 12, 24));
		when(reserve3.getCheckOut()).thenReturn(LocalDate.of(2025, 1, 1));
		
		when(reserve1.getTenant()).thenReturn(tenant);
		when(reserve2.getTenant()).thenReturn(tenant);
		when(reserve3.getTenant()).thenReturn(tenant);
		
		// para el createBooking:
		
		property = mock(Property.class);
		property2 = mock(Property.class);
		begin = LocalDate.of(2024, 11, 25);
		end = LocalDate.of(2024, 12, 31);
		
		paymentsMethods = new ArrayList<PaymentMethod>();
		payment1 = mock(PaymentMethod.class);
		payment2 = mock(PaymentMethod.class);
		paymentsMethods.add(payment1);
		paymentsMethods.add(payment2);
		
		periods = new ArrayList<SpecialPeriod>();
		period1 = mock(SpecialPeriod.class);
		period2 = mock(SpecialPeriod.class);
		periods.add(period1);
		periods.add(period2);
		
		timer = mock(Timer.class);
		
		house = mock(PropertyType.class);
		duplex = mock(PropertyType.class);
		
		when(booking1.getReserves()).thenReturn(expectedReservesBooking2);
		when(booking1.getProperty()).thenReturn(property2);
		when(property2.getCity()).thenReturn("Buenos Aires");
		
		when(booking2.getReserves()).thenReturn(expectedReservesBooking2);
		when(booking2.getProperty()).thenReturn(property);
		when(property.getCity()).thenReturn("Córdoba");
		
		// para allReservesCities:
		
		visitedCities = new HashSet<String>();
		visitedCities.add("Buenos Aires");
		visitedCities.add("Córdoba");
		
		// para el getAllReserves:
		
		allReserves = new ArrayList<Reserve>();
		allReserves.add(reserve1);
		allReserves.add(reserve2);
		allReserves.add(reserve1);
		allReserves.add(reserve2);
		
		// para el search:
		
		query = mock(And.class);
		query2 = mock(Or.class);
		
		emptyList = new ArrayList<Booking>();

		amenityTypes.add(electricity);
		amenityTypes.add(internet);
		amenityTypes.add(agua);
		
		propertyTypes.add(duplex);
		propertyTypes.add(house);

		// SUT con DOCs
		accomodationSite = new AccomodationSite(bookings, amenityTypes, propertyTypes);

		// SUT real
		accomodationSiteReal = new AccomodationSite();
	}

	@Test
	void testConstructor() {
		assertNotNull(accomodationSite);
	}

	@Test
	void testConstructorReal() {
		assertNotNull(accomodationSiteReal);
	}

	@Test
	void getOccupiedReservesTest() {
		assertEquals(expectedReserves, accomodationSite.getOccupiedReserves());
	}
	
	@Test
	void allReservesOfTheTenant() {
		assertEquals(reservesOfTheTenant, accomodationSite.allReservesOfTheTenant(tenant));
	}
	
	@Test
	void futureReservesOfTheTenant() {
		assertEquals(futureReserves, accomodationSite.futureReservesOfTheTenant(tenant));
	}
	
	@Test
	void getBookingsTest() {
		assertEquals(bookings, accomodationSite.getBookings());
	}
	
	@Test
	void queryTest() {
		// TODO: solo testea el lado positivo, preguntarle a ivan como testear la rama del else, probar pasarle una lista vacia para la rama if positiva
		// para el else puede ser un spy que se verifique si le llego la llamada a la query del  metodo search
		assertEquals(bookings, accomodationSite.search(null));
	}
	
	@Test
	void queryTestRamaElse() {
		assertEquals(emptyList, accomodationSite.search(query2));
	}
	
//	@Test
//	void addBookingTest() {
//		bookings.add(booking3);
//		assertEquals(3, accomodationSite.getBookings().size());
//	}
	
	@Test
	void createBookingTest() {
		when(property.getPropertyType()).thenReturn(house);
		accomodationSite.setAllowedProperties(house);
		accomodationSite.setAllowedAmenities(electricity);
		accomodationSite.setAllowedAmenities(internet);
		
		when(property.getAmenities()).thenReturn(amenityTypes);
		
		accomodationSite.createBooking(policy, property, begin, end, paymentsMethods, 100.0, periods, timer);
		
		assertEquals(accomodationSite.getBookings().size(), 3);
		
	}
	
	@Test
	void createInvalidBooking() {
		when(property.getPropertyType()).thenReturn(duplex);
		accomodationSite.setAllowedProperties(house);
		accomodationSite.setAllowedAmenities(electricity);
		accomodationSite.setAllowedAmenities(internet);
		
		when(property.getAmenities()).thenReturn(amenityTypes);

	     assertThrows(RuntimeException.class, () -> {
	    	 accomodationSiteReal.createBooking(policy, property, begin, end, paymentsMethods, 100.0, periods, timer);
	        });
	}
	
	@Test
	void viewPropertyTest() {
	when(booking1.getProperty()).thenReturn(property);
	    verifyNoInteractions(booking1);
	    accomodationSite.viewProperty(booking1);
	    verify(booking1, times(1)).getProperty();
	}

	@Test
	void allReservesCitiesTest() {
		assertEquals(visitedCities, accomodationSite.allReservesCities(tenant));
	}
	
	@Test
	void getAllReservesTest() {
		assertEquals(allReserves, accomodationSite.getAllReserves());
	}	
}

































// codigo nacho-ivan viejo:


// inicializacion de constructor de booking
//accomodationSite = new AccomodationSite();
//
//
//checkIn = LocalDate.of(2024, 11, 10);
//checkOut = LocalDate.of(2024, 11, 20);
//
//property = mock(Property.class);
//
//
//
//paymentsMethods = new ArrayList<PaymentMethod>();
//paymentsMethods.add(cash);
//
//pricePerDayWeekday = 1000.0;
//
//periods = new ArrayList<SpecialPeriod>();
//period = mock(SpecialPeriod.class);
//periods.add(period);
//
//house = mock(PropertyType.class);
//depto = mock(PropertyType.class);
//mansion = mock(PropertyType.class);
//
//accomodationSite.addAllowedProperty(depto);
//accomodationSite.addAllowedProperty(house);
//
//internet = mock(AmenityType.class);
//electricity = mock(AmenityType.class);
//TV = mock(AmenityType.class);
//
//accomodationSite.addAllowedAmenities(internet);
//accomodationSite.addAllowedAmenities(electricity);
//
//bookingMock1 = mock(Booking.class);
//bookingMock2 = mock(Booking.class);
//bookingMock3 = mock(Booking.class);
//
////CAMBIAR EL NOMBRE CUANDO MARTIN HAGA MERGE CON SU RAMA A MAIN
//reserveAvailable = mock(ReserveAvailable.class);
//reserveOccupied = mock(ReserveOccupied.class);
//reserveBooked = mock(ReserveBooked.class);
//
//availableBookings = new ArrayList<Booking>();
//
//ivan = mock(Tenant.class);
//nacho = mock(Tenant.class);
//martin = mock(Tenant.class);	

///*	@Test
//void createNonValidBookingTest() {
//	//ESTE METODO EXPLOTA POR EL HECHO DE LA EXCEPCION, VERIFICAR COMO SE PUEDE SOLUCIONAR
//	List<AmenityType> auxProperty = new ArrayList<AmenityType>();
//	auxProperty.add(electricity);
//	auxProperty.add(internet);
//	
//	when(property.getPropertyType()).thenReturn(mansion);
//	
//	when(property.getAmenities()).thenReturn(auxProperty);
//	
//    accomodationSite.createBooking(property, checkIn, checkOut, paymentsMethods, pricePerDayWeekday, periods);
//	
//	assertEquals(accomodationSite.getBookings().size(), 0);
//	
//	
//}*/
//// Verify:
//

//
////@Test
////void getVacantPropertiesTest() {
////	
////	
////	//assertEquals(vacantProperties.size(), 1);
////	//assertEquals(accomodationSite.getVacantProperties().size(), 0);
////	//accomodationSite.createBooking(property, checkIn, checkOut, pricePerDayWeekday, pricePerDayHighSeason, pricePerDayLongSeason, state1, tenant); 
////	
////	when(bookingMock1.getState()).thenReturn(reserveAvailable);
////	when(bookingMock2.getState()).thenReturn(reserveAvailable);
////	when(bookingMock3.getState()).thenReturn(reserveBooked);
////	
////	availableBookings.add(bookingMock1);
////	availableBookings.add(bookingMock2);
////	
////	accomodationSite.addBooking(bookingMock1);
////	accomodationSite.addBooking(bookingMock2);
////	accomodationSite.addBooking(bookingMock3);
////	
////	assertEquals(accomodationSite.getVacantProperties(), availableBookings);
////	
////	}
//	
//@Test
//void allReservesOfTheTenantTest() {
//	
////	when(bookingMock1.getTenant()).thenReturn(ivan);
////	when(bookingMock2.getTenant()).thenReturn(nacho);
////	when(bookingMock3.getTenant()).thenReturn(nacho);
////	
////	accomodationSite.addBooking(bookingMock1);
////	accomodationSite.addBooking(bookingMock2);
////	accomodationSite.addBooking(bookingMock3);
////
////	assertEquals(accomodationSite.bookingHistory(nacho).size(), 2);
////	assertEquals(accomodationSite.bookingHistory(ivan).size(), 1);
//	
//	
//}
//
//@Test
//void futureBookings() {
//	
//	accomodationSite.addBooking(bookingMock1);
//	accomodationSite.addBooking(bookingMock2);
//	accomodationSite.addBooking(bookingMock3);
//	
//	when(bookingMock1.getTenant()).thenReturn(ivan);
//	when(bookingMock2.getTenant()).thenReturn(nacho);
//	when(bookingMock3.getTenant()).thenReturn(nacho);
//	
//	when(bookingMock1.getCheckIn()).thenReturn(LocalDate.now().plusDays(15));
//	when(bookingMock2.getCheckIn()).thenReturn(LocalDate.now().minusMonths(2));
//	when(bookingMock3.getCheckIn()).thenReturn(LocalDate.now().plusMonths(4));
//	
//	assertEquals(accomodationSite.futureBookings(ivan).size(), 1);
//	assertEquals(accomodationSite.futureBookings(nacho).size(), 1);
//}
//
//@Test
//void allBookingsCities() {
//	
//	//assertEquals(accomodationSite.allBookingCities(tenant).size(), 1);
//	
//	accomodationSite.addBooking(bookingMock1);
//	accomodationSite.addBooking(bookingMock2);
//	accomodationSite.addBooking(bookingMock3);
//	
//	Property propertyMock1 = mock(Property.class);
//	Property propertyMock2 = mock(Property.class);
//	Property propertyMock3 = mock(Property.class);
//	
//	when(bookingMock1.getTenant()).thenReturn(nacho);
//	when(bookingMock2.getTenant()).thenReturn(nacho);
//	when(bookingMock3.getTenant()).thenReturn(nacho);
//
//	when(propertyMock1.getCity()).thenReturn("Rosario");
//	when(propertyMock2.getCity()).thenReturn("Buenos Aires");
//	when(propertyMock3.getCity()).thenReturn("Cordoba");
//	
//	when(bookingMock1.getProperty()).thenReturn(propertyMock1);
//	when(bookingMock2.getProperty()).thenReturn(propertyMock2);
//	when(bookingMock3.getProperty()).thenReturn(propertyMock3);
//	
//	assertEquals(accomodationSite.allBookingCities(nacho).size(), 3);


//Property property;
//
//Property invalidProperty;
//	
//LocalDate checkIn;
//LocalDate checkOut;
//
//double pricePerDayWeekday;
//double pricePerDayHighSeason;
//double pricePerDayLongSeason;
//
//Tenant ivan;
//Tenant martin;
//Tenant nacho;
//
//IReserveState reserveAvailable;
//IReserveState reserveBooked;
//IReserveState reserveOccupied;
//
//List<Booking> bookings;
//Set<String> cities;
//
//Booking bookingMock1;
//Booking bookingMock2;
//Booking bookingMock3;
//
//List<Booking> approvedBookings;
//List<Booking> availableBookings;
//
//List<PaymentMethod> paymentsMethods;
//
//PaymentMethod cash;
//
////------------------
//SpecialPeriod period;
//List<SpecialPeriod> periods;
////------------------
// 
//
//Administrator administrator;
//
//List<AmenityType> allowedAmenities;
//List<AmenityType> invalidAmenities;
//

//
//PropertyType house;
//PropertyType depto;
//PropertyType mansion;






/*
//CODIGO DE NACHO 

property = mock(Property.class);
invalidProperty = mock(Property.class);
invalidType = mock(PropertyType.class);

validType = mock(PropertyType.class);
checkIn = LocalDate.now().plusDays(1);
checkOut = mock(LocalDate.class);

pricePerDayWeekday = 100.0;
pricePerDayHighSeason = 70.0;
pricePerDayLongSeason = 50.0;

tenant = mock(Tenant.class);
tenant2 = mock(Tenant.class);

state1 =  mock(ReserveAvailable.class);
state2 = mock(ReserveApproved.class);
state3 = mock(ReserveCompleted.class);

paymentsMethods = new ArrayList<PaymentMethod>();
cash = mock(PaymentMethod.class);
paymentsMethods.add(cash);

periods = new ArrayList<Period>();
period = mock(Period.class);
periods.add(period);

bookingMock = mock(Booking.class);

// inicializacion de valores de referentes al accomodationSite

validAmenity1 = mock(AmenityType.class);
validAmenity2 = mock(AmenityType.class);

propertyType1 = mock(PropertyType.class);
propertyType2 = mock(PropertyType.class);
propertyType3 = mock(PropertyType.class);

// SUT: accomodationSite

accomodationSite = new AccomodationSite();
/*accomodationSite.createBooking(property, checkIn, checkOut, paymentsMethods,
							   pricePerDayWeekday, periods); 
accomodationSite.createBooking(property, checkIn, checkOut, paymentsMethods,
		   					   pricePerDayWeekday, periods); */
/*
availableBookings = new ArrayList<Booking>();
availableBookings.add(accomodationSite.getBookings().getFirst());

bookings = new ArrayList<Booking>();
bookings.add(accomodationSite.bookingHistory(tenant).get(0));

approvedBookings = new ArrayList<Booking>();
approvedBookings.add(accomodationSite.getBookings().get(1));

cities = new HashSet<String>();
cities.add(property.getCity());

allowedAmenities = new ArrayList<AmenityType>();
invalidAmenities = new ArrayList<AmenityType>();

allowedAmenities.add(validAmenity1);
invalidAmenities.add(validAmenity2);

accomodationSite.addAllowedProperty(propertyType1);
accomodationSite.addAllowedProperty(propertyType2);

accomodationSite.addAllowedAmenities(validAmenity1);
accomodationSite.addAllowedAmenities(validAmenity2);



//administrator = spy(Administrator.class);


/**
 *  Asigno los mock Stubs acá arriba para que las variables no entren como null en el sut,
	sino que se les asigne el valor esperado para testearlas, en este caso para que no entren al genericBooking como null, 
	pero no directamente al SUT. NO BORRAR.

*/

/*when(property.getCity()).thenReturn("Buenos Aires");
when(invalidProperty.getPropertyType()).thenReturn(invalidType);
when(invalidType.type()).thenReturn("casa");
when(property.getPropertyType()).thenReturn(validType);
when(validType.type()).thenReturn("duplex");

when(property.getAmenities()).thenReturn(allowedAmenities);
when(invalidProperty.getAmenities()).thenReturn(invalidAmenities);*/



















































