package sa.booking;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.Reserve;
import sa.booking.ReserveWaiting;
import sa.booking.reserveStates.IReserveState;
import sa.booking.reserveStates.Timer;
import sa.users.Tenant;

class ReserveWaitingTest {

	private ReserveWaiting stateReserve;
	private Reserve reserve;
	private Booking booking;
	private Tenant tenant;
	private Period period;
	private Timer timer;

	@BeforeEach
	void setUp() throws Exception {
		timer = mock(Timer.class);
		period = mock(Period.class);
		tenant = mock(Tenant.class);
		booking = mock(Booking.class);
		when(booking.getTimer()).thenReturn(timer);
		when(period.start()).thenReturn(LocalDate.now());
		when(period.end()).thenReturn(LocalDate.now().plusDays(1));
		reserve = spy(new Reserve(booking, tenant, period));
		
		// SUT
		stateReserve = new ReserveWaiting(this.reserve);
	}

	@Test
	void newReserveStateTest() {
		assertNotNull(stateReserve);
	}

	@Test
	void cancelTest() {
		stateReserve.cancel();
		verifyNoInteractions(this.reserve);
	}

	@Test
	void updateTest() {
		stateReserve.update();
		verifyNoInteractions(this.reserve);	
	}

	@Test
	void testGetReserve() {
		assertEquals(this.reserve, this.stateReserve.getReserve());
	}

	@Test
	void approveTest() {
		verifyNoInteractions(this.reserve);
		this.reserve.setState(stateReserve);
		stateReserve.approve();
		IReserveState state = this.reserve.getState();
		assertNotEquals(state, this.stateReserve);
		verify(this.reserve, times(1)).setState(state);
		verify(this.reserve, times(1)).approved();
	}
	
	@Test
	void declineTest() {
		verifyNoInteractions(this.reserve);	
		stateReserve.decline();
		verify(this.reserve, times(1)).declined();
	}

}
