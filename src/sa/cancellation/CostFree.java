package sa.cancellation;

import java.time.LocalDate;

import sa.booking.Reserve;

public class CostFree implements ICancellationPolicy{

	@Override
	public void activate(Reserve reserve, LocalDate cancellationDate) {
		if(cancellationDate.isBefore(reserve.getCheckIn().minusDays(10))) {
			System.out.println("La cancelación se asentó correctamente de manera gratuita");
			reserve.setPrice(0.0);
		}else if(reserve.getCheckIn().equals(reserve.getCheckOut())) { //si la reserva cancelada duraba 1 dia, se paga el doble del precio de ese dia
			System.out.println("La cancelación se asentó correctamente, solo se paga el equivalente a dos dias");
			reserve.setPrice(reserve.getBooking().price(reserve.getCheckIn()) * 2);
		}
		else {
			LocalDate aux = reserve.getCheckIn();
			int i = 0;
			double total = 0;
			
			//FALLA SI SE ALQUILA SOLO UN DIA
			while(!aux.isEqual(reserve.getCheckOut()) && i !=2) { //Comparo que checkIn no sea el mismo date que checkOut y que i no sea igual a 2 (Quiero sumar el precio de DOS dias)
				total += reserve.getBooking().price(aux);
				aux = aux.plusDays(1);
				i++;
			}
			System.out.println("La cancelación se asentó correctamente, solo se paga el equivalente a dos dias");
			reserve.setPrice(total);
			
		}
		
	}

}
