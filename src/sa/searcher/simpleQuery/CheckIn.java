package sa.searcher.simpleQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.booking.Booking;


public class CheckIn extends SimpleQuery{
	
	private LocalDate date;

	public CheckIn(LocalDate date) {
		this.date = date;
	}

	@Override
	public List<Booking> search(List<Booking> bookings) {

		List<Booking> aux = new ArrayList<Booking>();
		
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).getPeriod().belongs(getCheckInDate()) && bookings.get(i).isAvailableDate(getCheckInDate())) {
				aux.add(bookings.get(i));
			}
		}
		return aux;
	}

	public LocalDate getCheckInDate() {
		return this.date;
	}


	

	
	

}
