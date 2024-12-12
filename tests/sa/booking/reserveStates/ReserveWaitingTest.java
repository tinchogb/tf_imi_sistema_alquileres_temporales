package sa.booking.reserveStates;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.Reserve;

class ReserveWaitingTest {

	private ReserveWaiting stateReserve;
	private Reserve reserve;
	private Booking booking;
	private Timer timer;

	@BeforeEach
	void setUp() throws Exception {
		reserve = mock(Reserve.class);
		timer = mock(Timer.class);
		stateReserve = new ReserveWaiting(this.reserve);
		booking = mock(Booking.class);
		when(reserve.getBooking()).thenReturn(booking);
		when(booking.getTimer()).thenReturn(timer);
	}

	@Test
	void newReserveStateTest() {
		assertNotNull(stateReserve);
	}
	@Test
	void isCancellatedTest() {
		assertFalse(stateReserve.isCancelled());
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


}
