package sa.booking.reserveStates;

import sa.booking.Reserve;
import sa.subscriptions.INotifyTimerSubscriber;

public interface IReserveState extends INotifyTimerSubscriber {

	public void approve();
	public void cancel();
	public void decline();
	public Reserve getReserve();
}
