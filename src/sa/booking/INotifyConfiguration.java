package sa.booking;
import sa.observer.interfaces.INotifyObserver;

public interface INotifyConfiguration {

	public void registerPriceObserver(INotifyObserver o);
	public void registerCancelObserver(INotifyObserver o);
	public void registerReserveObserver(INotifyObserver o);

	public void notifySubscribersPrice();
	public void notifySubscribersReserve(Reserve r);
	public void notifySubscribersCancelled(Reserve r);
}
