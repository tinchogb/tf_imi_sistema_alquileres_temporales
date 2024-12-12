package sa.searcher.simpleQuery;

import java.util.List;

import sa.booking.Booking;

public interface IQuery {
	public List<Booking> search(List<Booking> bookings);
}
