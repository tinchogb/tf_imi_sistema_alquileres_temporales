package sa.cancellation;

import sa.booking.Reserve;

public interface ICancellationPolicy {

	//Se necesita el localdate para poder modificar el dia de cancelacion en los test
		public void activate(Reserve reserve);
}
