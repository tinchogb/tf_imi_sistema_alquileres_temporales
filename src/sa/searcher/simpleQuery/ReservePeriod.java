package sa.searcher.simpleQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.booking.Booking;


public class ReservePeriod extends SimpleQuery{
	
	private LocalDate start;
	private LocalDate end;

	public ReservePeriod(LocalDate start, LocalDate end) {
		this.start = start;
		this.end = end;
	}

	@Override
	public List<Booking> search(List<Booking> bookings) {

		return bookings.stream()
						.filter(b -> b.hasAvailablePeriod(this.getCheckInDate(), this.getCheckOutDate()))
						.toList();
	}

	public LocalDate getCheckInDate() {
		return this.start;
	}
	
	public LocalDate getCheckOutDate() {
		return this.end;
	}
}
