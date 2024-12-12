package sa.booking.reserveStates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import sa.booking.Booking;
import sa.booking.Period;
import sa.booking.Reserve;
import sa.properties.Property;
import sa.users.Owner;
import sa.users.Tenant;

class ReserveOccupiedTest {

	private ReserveOccupied		stateOccupied1;
	private ReserveOccupied		stateOccupied2;
	private Booking				booking;
	private Property			property;
	private Tenant				tenant;
	private Owner				owner;
	private Reserve				reserve;
	private Timer 				timer;
	private Reserve				spyReserve;
	private Period				period;
	
	
	@BeforeEach
	void setUp() throws Exception {
		// DOC (Depended-On-Component): nuestros doubles
		this.booking		= mock(Booking.class);
		this.property		= mock(Property.class);
		this.tenant			= mock(Tenant.class);
		this.owner			= mock(Owner.class);
		this.reserve		= mock(Reserve.class);
		this.timer          = mock(Timer.class);
		this.period			= mock(Period.class);
		
		this.spyReserve		= spy(new Reserve(booking,tenant,period));

		when(this.booking.getProperty()).thenReturn(this.property);
		when(this.reserve.getTenant()).thenReturn(this.tenant);
		when(this.reserve.getBooking()).thenReturn(this.booking);
		when(this.property.getOwner()).thenReturn(this.owner);
		when(booking.getTimer()).thenReturn(timer);
		
		
		// SUT (System Under Test): objeto a testear
		this.stateOccupied1 = new ReserveOccupied(this.reserve);
		this.stateOccupied2 = new ReserveOccupied(this.spyReserve);
	}

	@Test
	void testConstructor() {
		assertNotNull(this.stateOccupied1);
		assertNotNull(this.stateOccupied2);
	}

	@Test
	void testCancel() {
		verify(this.booking, times(0)).notifySubscribersCancelled(this.spyReserve);
		this.stateOccupied2.cancel();
		verify(this.booking, times(1)).handleCancellation(this.spyReserve);
	}
//	
	@Test
	void testGetReserve() {
		assertEquals(this.reserve, this.stateOccupied1.getReserve());
	}

	@Test
	void testIsCancelled() {
		assertEquals(false, this.stateOccupied1.isCancelled());
	}
	
	@Test
	void testUpdate() {
		this.stateOccupied2.update();
		verify(this.booking.getTimer(),times(1)).unregister(this.stateOccupied2,this.stateOccupied2.getReserve().getCheckOut());
		verify(this.stateOccupied2.getReserve(),times(1)).finished();
	}

}
