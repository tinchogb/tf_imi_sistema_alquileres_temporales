package sa.booking.reserveStates;

import sa.booking.Reserve;
import sa.subscriptions.INotifyTimerSubscriber;

public interface IReserveState extends INotifyTimerSubscriber {

	public void cancel();
	public boolean isCancelled();
	public Reserve getReserve();
}
