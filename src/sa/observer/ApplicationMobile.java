package sa.observer;




import sa.booking.Booking;
import sa.booking.Reserve;
import sa.observer.interfaces.INotifyObserver;


public class ApplicationMobile implements INotifyObserver{

private ObjectScreen screen;
	
	@Override
	public void updateCancellation(Reserve r) {
		
		getScreen().popUp(r.getBooking().getProperty().getPropertyType().type(), "Rojo", 3);
		
	}
	
	public ObjectScreen getScreen() {
		return this.screen;
	}

	public void setScreen(ObjectScreen s) {
		this.screen = s;
	}

	@Override
	public void updateLowerPrice(Booking b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateNewReserve(Reserve r) {
		// TODO Auto-generated method stub
		
	}

}
