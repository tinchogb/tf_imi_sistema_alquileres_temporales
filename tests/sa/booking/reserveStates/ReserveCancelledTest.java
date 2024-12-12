package sa.booking.reserveStates;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

import java.time.LocalDate;

import sa.booking.Reserve;

class ReserveCancelledTest {

	private ReserveCancelled	stateCancelled;

	private Reserve				reserve;
	private LocalDate			cancelDate;
	
	@BeforeEach
	void setUp() throws Exception {
		// DOC (Depended-On-Component): nuestros doubles
		this.reserve		= mock(Reserve.class);
		this.cancelDate		= LocalDate.now();
		
		
		// SUT (System Under Test): objeto a testear
		this.stateCancelled = new ReserveCancelled(this.reserve, this.cancelDate);
	}

	@Test
	void testConstructor() {
		assertNotNull(this.stateCancelled);
	}

	@Test
	void testCancel() {
		this.stateCancelled.cancel();
		verifyNoInteractions(this.reserve);
	}
	
	@Test
	void testGetReserve() {
		assertEquals(this.reserve, this.stateCancelled.getReserve());
	}

	@Test
	void testIsCancelled() {
		assertEquals(true, this.stateCancelled.isCancelled());
	}

	@Test
	void testCancellationDate() {
		assertEquals(this.cancelDate, this.stateCancelled.cancellationDate());
	}
	
	@Test
	void testUpdate() {
		this.stateCancelled.update();
		verifyNoInteractions(this.reserve);
	}
}
