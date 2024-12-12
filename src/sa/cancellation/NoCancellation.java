package sa.cancellation;

import java.time.LocalDate;

import sa.booking.Reserve;

public class NoCancellation implements ICancellationPolicy{

	@Override
	public void activate(Reserve reserve,LocalDate cancellationDate) {
		System.out.println("La cancelacion se efectuó, se debe pagar el monto de $: "+reserve.getPrice());
		
	}

	
	
	

	
}
