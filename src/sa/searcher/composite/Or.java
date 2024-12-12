package sa.searcher.composite;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import sa.booking.Booking;
import sa.searcher.simpleQuery.IQuery;

public class Or extends CompositeQuery {
	
	
//	private IQuery fstQuery;
//	private IQuery sndQuery;
	
	public Or(IQuery firstQuery, IQuery secondQuery) {
//		this.fstQuery = firstQuery;
//		this.sndQuery = secondQuery;
		super(firstQuery,secondQuery);
	}
	@Override
	public List<Booking> search(List<Booking> bookings) {
		Set<Booking> totalBookings = new LinkedHashSet<Booking>();
		
		totalBookings.addAll(query1.search(bookings));
		totalBookings.addAll(query2.search(bookings));
		
		return new ArrayList<Booking>(totalBookings);
	}

}
