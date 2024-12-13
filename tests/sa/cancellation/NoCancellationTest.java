package sa.cancellation;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Reserve;

class NoCancellationTest {

	private NoCancellation cancellationtest;
	private Reserve reserveMock;
	
	private LocalDate dayTest;
	
	
	@BeforeEach
	void setUp() throws Exception {
		cancellationtest = new NoCancellation();
		dayTest = LocalDate.now();
		this.reserveMock = mock(Reserve.class);
		when(this.reserveMock.getCancellationDate()).thenReturn(dayTest);
		reserveMock = mock(Reserve.class);
	}

	@Test
	void newCancellationTest() {
		assertNotNull(cancellationtest);
	}
	
	@Test
	void activateCancellationTest() {
		cancellationtest.activate(reserveMock);
		verify(reserveMock).getPrice();
	}

}
