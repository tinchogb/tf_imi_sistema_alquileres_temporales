package sa.observer;



import sa.booking.Booking;
import sa.booking.Reserve;
import sa.observer.interfaces.INotifyObserver;


public class OfferWebSite implements INotifyObserver{

	private ObjectPublisher publisher;
	@Override
	public void updateLowerPrice(Booking booking) {
		// TODO Auto-generated method stub
		System.out.println("Le paso el booking al objeto colaborativo");
		this.getPublisher().publish("No te pierdas esta oferta: Un inmueble "+ booking.getProperty().getPropertyType() +" a tan solo "+ String.valueOf(booking.getPricer().getBasePrice()));

	}
	
	public ObjectPublisher getPublisher() {
		// TODO Auto-generated method stub
		return this.publisher;
	}
	
	public void setPublisher(ObjectPublisher p) { 
		this.publisher = p;
		
	}
	
	@Override
	public void updateCancellation(Reserve r) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void updateNewReserve(Reserve r) {
		// TODO Auto-generated method stub
		
	}

	

}
