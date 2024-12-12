package sa.booking.reserveStates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;


import sa.booking.Booking;
import sa.booking.Reserve;
import sa.properties.Property;
import sa.users.Owner;
import sa.users.Tenant;

class ReserveBookedTest {

	private ReserveBooked		stateBooked;
	private ReserveCancelled	stateCancelled;
	private Booking				booking;
	private Reserve				reserve;
	private Property			property;
	private Tenant				tenant;
	private Owner				owner;
	
	private Timer				timer;
	
	@BeforeEach
	void setUp() throws Exception {
		// DOC (Depended-On-Component): nuestros doubles
		this.stateCancelled	= mock(ReserveCancelled.class);
		this.booking		= mock(Booking.class);
		this.reserve		= mock(Reserve.class);
		this.property		= mock(Property.class);
		this.tenant			= mock(Tenant.class);
		this.owner			= mock(Owner.class);
		this.timer			= mock(Timer.class);

		when(this.booking.getProperty()).thenReturn(this.property);
		when(this.reserve.getTenant()).thenReturn(this.tenant);
		when(this.reserve.getBooking()).thenReturn(this.booking);
		when(this.property.getOwner()).thenReturn(this.owner);
		
		//when(this.stateBooked.getReserve()).thenReturn(reserve);
		when(this.booking.getTimer()).thenReturn(timer);
		
		
		
		// SUT (System Under Test): objeto a testear
		this.stateBooked = new ReserveBooked(this.reserve);
	}

	@Test
	void testConstructor() {
		assertNotNull(this.stateBooked);
	}

	@Test
	void testCancel() {
		verify(this.reserve, times(0)).setState(this.stateCancelled);
		verify(this.booking, times(0)).notifySubscribersCancelled(this.reserve);
		this.stateBooked.cancel();
		//verify(this.stateBooked.getReserve(),times(1)).setState(new ReserveCancelled(this.stateBooked.getReserve(),LocalDate.now()));
		//verify(this.booking, times(1)).handleCancellation(this.reserve); falta arreglar esto
	}

	@Test
	void testGetReserve() {
		assertEquals(this.reserve, this.stateBooked.getReserve());
	}

	@Test
	void testIsCancelled() {
		assertEquals(false, this.stateBooked.isCancelled());
	}
	
	@Test
	void testUpdate() {
		this.stateBooked.update();
		verify(reserve,times(3)).getBooking();  //Se usan 3 veces, en el constructor de stateBooked, en el metodo update, y la otra en el constructor de reserveOccupied
		verify(booking,times(3)).getTimer();  //Se usan 3 veces, en el constructor de stateBooked, en el metodo update, y la otra en el constructor de reserveOccupied
	}
}
