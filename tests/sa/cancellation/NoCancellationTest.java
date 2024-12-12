package sa.cancellation;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;


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
		
		reserveMock = mock(Reserve.class);
	}

	@Test
	void newCancellationTest() {
		assertNotNull(cancellationtest);
	}
	
	@Test
	void activateCancellationTest() {
		cancellationtest.activate(reserveMock,dayTest);
		verify(reserveMock).getPrice();
	}

}
