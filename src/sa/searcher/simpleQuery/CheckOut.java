package sa.searcher.simpleQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.booking.Booking;

public class CheckOut extends SimpleQuery{

private LocalDate date;
	
	public CheckOut(LocalDate d) {
		this.date = d;
	}

	@Override
	public List<Booking> search(List<Booking> bookings) {
		List<Booking> aux = new ArrayList<Booking>();
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).getPeriod().belongs(getCheckOutDate())) {
				aux.add(bookings.get(i));
			}
		}
		return aux;
	}

	public LocalDate getCheckOutDate() {
		// TODO Auto-generated method stub
		return this.date;
	}

}
