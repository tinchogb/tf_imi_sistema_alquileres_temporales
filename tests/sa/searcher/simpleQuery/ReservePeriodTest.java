package sa.searcher.simpleQuery;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;

class ReservePeriodTest {

	private ReservePeriod querytest1;

	private Booking booking1;
	private Booking booking2;
	
	private List<Booking> bookings;
	
	@BeforeEach
	void setUp() throws Exception {

		booking1 = mock(Booking.class);
		booking2 = mock(Booking.class);

		bookings = new ArrayList<Booking>();
		bookings.add(booking1);
		bookings.add(booking2);

		// SUT
		querytest1 = new ReservePeriod(LocalDate.of(2024, 8, 17), LocalDate.of(2024, 11, 20));
	}

	@Test
	void testNewCheckIn() {
		assertNotNull(querytest1);
	}

	@Test
	void testGetCheckInDate() {
		assertEquals(querytest1.getCheckInDate(),LocalDate.of(2024, 8, 17));
	}

	@Test
	void TestGetCheckOutDate() {
		assertEquals(querytest1.getCheckOutDate(), LocalDate.of(2024, 11, 20));
	}

	@Test
	void testSearch() {
		verifyNoInteractions(booking1);
		verifyNoInteractions(booking2);
		when(booking1.hasAvailablePeriod(querytest1.getCheckInDate(), querytest1.getCheckOutDate())).thenReturn(false);
		when(booking2.hasAvailablePeriod(querytest1.getCheckInDate(), querytest1.getCheckOutDate())).thenReturn(true);
		assertEquals(1, this.querytest1.search(bookings).size());
		assertEquals(true, this.querytest1.search(bookings).contains(booking2));
	}
}
