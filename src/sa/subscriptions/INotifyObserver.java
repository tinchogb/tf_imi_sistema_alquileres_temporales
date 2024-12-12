package sa.subscriptions;

import java.time.LocalDate;

import sa.booking.Booking;

public interface INotifyObserver {

//ESTA INTERFAZ NO LA UTILIZA NADIE, YO NO LA CREE (IVAN). RECOMIENDO BORRARLA PORQUE SE PUEDE CONFUNDIR CON LA MISMA INTERFAZ QUE ESTA GUARDADA EN SA.OBSERVER.INTERFACES
	public void update(Booking b);

	//public void update(Booking b, BookedPeriod bp);

	public void update(Booking b, LocalDate date);

}
