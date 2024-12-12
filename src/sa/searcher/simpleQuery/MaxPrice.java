package sa.searcher.simpleQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.booking.Booking;

public class MaxPrice extends SimpleQuery {

	private double value;
	private LocalDate date;
	
	public MaxPrice(double value, LocalDate d) {
		this.date = d;
		this.value = value;
	}
	
	@Override
	public List<Booking> search(List<Booking> bookings) {
		List<Booking> aux = new ArrayList<Booking>();
		
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).price(date) <= getMaxPrice()) {
				aux.add(bookings.get(i));
			}
		}
		
		return aux;
	}

	public double getMaxPrice() {
		// TODO Auto-generated method stub
		return this.value;
	}
}
