package sa.cancellation;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import sa.booking.Reserve;

class IntermediateCancellationTest {

private IntermediateCancellation cancellationtest;
	
	private Reserve reserveMock;
	private LocalDate dateTest;
	@BeforeEach
	void setUp() throws Exception {
		
		reserveMock = mock(Reserve.class);
		cancellationtest = new IntermediateCancellation();
		
		dateTest = LocalDate.of(2024, 10, 1);
	}

	@Test
	void newCancellationTest() {
		assertNotNull(cancellationtest);
	}
	
	@Test
	void cancelation20DaysBeforeCheckInTest() {
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 22));
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock).setPrice(0.0);
	}
	
	@Test
	void cancellation19daysBeforeCheckInTest() {
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 20));
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock).setPrice(reserveMock.getPrice() * 0.5);
	}
	@Test
	void cancellation10daysBeforeCheckInTest() {
		assertTrue(dateTest.isBefore(LocalDate.of(2024, 10, 12).minusDays(10)));
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 12));
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock).setPrice(reserveMock.getPrice() * 0.5);
	}
	
	@Test
	void cancellation5daysBeforeCheckInTest() {
		when(reserveMock.getCheckIn()).thenReturn(LocalDate.of(2024, 10, 6));
		cancellationtest.activate(reserveMock, dateTest);
		verify(reserveMock,never()).getPrice();
	}
	

}
