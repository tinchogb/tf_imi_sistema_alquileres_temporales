package sa.searcher.simpleQuery;

import java.util.ArrayList;
import java.util.List;

import sa.booking.Booking;

public class MaxGuest extends SimpleQuery {

private int value;
	
	public MaxGuest(int quantity) {
		this.value = quantity;
	}

	@Override
	public List<Booking> search(List<Booking> bookings) {
		List<Booking> aux = new ArrayList<Booking>();
		
		for(int i=0;i<bookings.size();i++) {
			if(this.value <= bookings.get(i).getProperty().getMaxGuests()) {
				aux.add(bookings.get(i));
			}
		}
		
		return aux;
	}

	public int getMaxGuests() {
		return this.value;
	}

}
