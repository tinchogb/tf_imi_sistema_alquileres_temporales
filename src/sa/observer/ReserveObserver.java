package sa.observer;



import sa.booking.Booking;
import sa.booking.Reserve;
import sa.observer.interfaces.INotifyObserver;

public class ReserveObserver implements INotifyObserver {

	@Override
	public void updateNewReserve(Reserve r) {
		
		System.out.println("Se alquilo la propiedad:");
		r.getBooking().getProperty().summary();
		
	}

	@Override
	public void updateCancellation(Reserve r) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLowerPrice(Booking b) {
		// TODO Auto-generated method stub
		
	}

	
}
