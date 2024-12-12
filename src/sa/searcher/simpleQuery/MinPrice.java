package sa.searcher.simpleQuery;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.booking.Booking;

public class MinPrice extends SimpleQuery{
	
	private double value;
	private LocalDate date;
	
	public MinPrice(double min, LocalDate d) {
		this.value = min;
		this.date = d;
	}

	@Override
	public List<Booking> search(List<Booking> bookings) {
		List<Booking> aux = new ArrayList<Booking>();
		
		for(int i=0;i<bookings.size();i++) {
			if(bookings.get(i).price(date) >= getMinPrice()) {
				aux.add(bookings.get(i));
			}
		}
		
		return aux;
	}

	public double getMinPrice() {
		return this.value;
	}
}
