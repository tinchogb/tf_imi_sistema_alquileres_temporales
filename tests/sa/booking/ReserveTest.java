package sa.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.reserveStates.IReserveState;
import sa.booking.reserveStates.Timer;
import sa.properties.Property;
import sa.users.Owner;
import sa.users.Tenant;

class ReserveTest {

	private Reserve reserve;
	
	private Booking booking;
	private Tenant tenant;
	private Period period;
	private double price;
	private Owner owner;
	private Property property;
	private Timer timer;

	private IReserveState stateBooked;
	
	private LocalDate	today;

	
	@BeforeEach
	void setUp() throws Exception {

		// DOCs
		this.booking	= mock(Booking.class);
		this.tenant		= mock(Tenant.class);
		this.period		= mock(Period.class);
		this.owner		= mock(Owner.class);
		this.property	= mock(Property.class);
		
		this.timer 		= mock(Timer.class);

		this.stateBooked	= mock(ReserveBooked.class);
		
		this.today = LocalDate.now();
		this.price	= 10;

		when(this.period.start()).thenReturn(this.today);
		when(this.period.end()).thenReturn(this.today.plusDays(1));
		when(this.booking.priceBetween(this.today, this.today.plusDays(1))).thenReturn(this.price);
		when(this.booking.getProperty()).thenReturn(this.property);
		when(this.property.getOwner()).thenReturn(this.owner);
		
		when(booking.getTimer()).thenReturn(timer);
		
		
		// SUT
		this.reserve = new Reserve(booking, tenant, period);
	}

	@Test
	void testConstructor() {
		assertNotNull(this.reserve);
	}

	@Test
	public void testGetCheckIn() {
		assertEquals(this.today, this.reserve.getCheckIn());
	}

	@Test
	public void testGetCheckOut() {
		assertEquals(this.today.plusDays(1), this.reserve.getCheckOut());
	}

	@Test
	public void testGetTenant() {
		assertEquals(this.tenant, this.reserve.getTenant());
	}

	@Test
	public void testGetBooking() {
		assertEquals(this.booking, this.reserve.getBooking());
	}

	@Test
	public void testGetPrice() {
		assertEquals(this.price, this.reserve.getPrice());
	}

	@Test
	public void testSetState() {
		assertNull(this.reserve.getState()); //la reserva se crea sin estado
		this.reserve.setState(this.stateBooked); // configuro el estado de la reserva a booked
		assertEquals(this.stateBooked, this.reserve.getState());
	} 

	@Test
	public void testCancel() {
		assertNull(this.reserve.getState());
		this.reserve.setState(this.stateBooked);
		this.reserve.cancel();
		verify(this.stateBooked, times(1)).cancel();
	}

	@Test
	public void testApprove() {
		assertNull(this.reserve.getState());
		this.reserve.setState(this.stateBooked);
		this.reserve.approve();
		verify(this.stateBooked, times(1)).approve();
	}

	@Test
	public void testDecline() {
		assertNull(this.reserve.getState());
		this.reserve.setState(this.stateBooked);
		this.reserve.decline();
		verify(this.stateBooked, times(1)).decline();
	}

	@Test
	public void testDeclined() {
		assertNull(this.reserve.getState());
		verify(this.booking, times(0)).handleDeclination(this.reserve);
		verify(this.tenant, times(0)).reserveDeclined(this.reserve);
		this.reserve.declined();
		verify(this.tenant, times(1)).reserveDeclined(this.reserve);
		verify(this.booking, times(1)).handleDeclination(this.reserve);
	}
	
	@Test
	public void testApproved() {
		assertNull(this.reserve.getState());
		verify(this.tenant, times(0)).reserveApproved(this.reserve);
		verify(this.tenant, times(0)).sendEmail(this.reserve, "Se acaba de aceptar tu reserva.");
		verify(this.booking, times(0)).handleDeclination(this.reserve);
		this.reserve.approved();
		verify(this.tenant, times(1)).reserveApproved(this.reserve);
		verify(this.tenant, times(1)).sendEmail(this.reserve, "Se acaba de aceptar tu reserva.");
		verify(this.booking, times(1)).handleApprovation(this.reserve);
	}

	@Test
	public void testCancelled() {
		assertNull(this.reserve.getState());
		verify(this.booking, times(0)).handleCancellation(this.reserve);
		this.reserve.cancelled();
		verify(this.booking, times(1)).handleCancellation(this.reserve);
	}

	@Test
	public void testFinished() {
		assertNull(this.reserve.getState());
		verify(this.booking, times(0)).handleFinalization(this.reserve);
		this.reserve.finished();
		verify(this.booking, times(1)).handleFinalization(this.reserve);
	}

	@Test
	public void testGetCancellationDate() {
		assertNull(this.reserve.getCancellationDate());
	}

	@Test
	public void testSetCancellationDate() {
		assertNull(this.reserve.getCancellationDate());
		LocalDate date = LocalDate.now();
		this.reserve.setCancellationDate(date);
		assertEquals(date, this.reserve.getCancellationDate());
	}
	
	@Test
	public void testSetPrice() {
		assertEquals(this.price, this.reserve.getPrice());
		this.reserve.setPrice(this.price*2);
		assertEquals(this.price*2, this.reserve.getPrice());
	}
}
