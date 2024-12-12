package sa.cancellation;

import java.time.LocalDate;

import sa.booking.Reserve;

public class IntermediateCancellation implements ICancellationPolicy{

	@Override
	public void activate(Reserve reserve, LocalDate cancellationDate) {
		
		if(cancellationDate.isBefore(reserve.getCheckIn().minusDays(20))) {
			System.out.println("La cancelación se asentó correctamente de manera gratuita");
			reserve.setPrice(0.0);
		}else if(cancellationDate.isBefore(reserve.getCheckIn().minusDays(19)) || cancellationDate.isBefore(reserve.getCheckIn().minusDays(10)) ) {
			System.out.println("La cancelación se asentó correctamente, se paga la mitad de la reserva");
			reserve.setPrice(reserve.getPrice() * 0.5);
		}else {
			System.out.println("La cancelación se asentó correctamente, se paga todo");
		}
		
	}

}
