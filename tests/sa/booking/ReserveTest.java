package sa.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.reserveStates.IReserveState;
import sa.booking.reserveStates.ReserveBooked;
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
		assertNotNull(this.reserve.getState()); //la reserva esta en waiting
		this.reserve.setState(this.stateBooked); // cambiamos el estado de la reserva a booked
		assertEquals(this.stateBooked, this.reserve.getState());
	} 

	@Test
	public void testFromCreatedToBooked() {
		assertNotNull(this.reserve.getState()); 
		this.reserve.setState(this.stateBooked);				//es literalmente el mismo codigo que el test anterior
		assertEquals(this.stateBooked, this.reserve.getState());
	}

	@Test
	public void testCancel() {
		assertNotNull(this.reserve.getState());
		this.reserve.setState(this.stateBooked);
		this.reserve.cancel();
		verify(this.stateBooked, times(1)).cancel();
	}

	@Test
	public void testApprove() {
		assertNotNull(this.reserve.getState());
		verifyNoInteractions(this.tenant);
		this.reserve.approve();
		verify(this.booking, times(1)).addReserve(this.reserve);
		verify(this.tenant, times(1)).sendEmail(reserve, "Se acaba de aceptar tu reserva.");
	}

	@Test
	public void testDecline() {
		assertNotNull(this.reserve.getState());
		verify(this.owner, times(0)).cleanRequestedReserve();
		verify(this.tenant, times(0)).reserveDeclined(this.reserve);
		this.reserve.setState(this.stateBooked); //necesito cambiarle el estado ya que un waiting no sabe ser rechazado, solamente cancelado
		this.reserve.decline(); 
		//assertNull(this.reserve.getState());  no tiene sentido en nuestra implementacion
		verify(this.owner, times(1)).cleanRequestedReserve();
		verify(this.tenant, times(1)).reserveDeclined(this.reserve);
	}

	@Test
	public void testSetPrice() {
		assertEquals(this.price, this.reserve.getPrice());
		this.reserve.setPrice(this.price*2);
		assertEquals(this.price*2, this.reserve.getPrice());
	}
}
