package sa.booking.reserveStates;

import java.time.LocalDate;

import sa.booking.Reserve;

public class ReserveCancelled implements IReserveState {

	private LocalDate	cancelDate;
	private Reserve		reserve;

	public ReserveCancelled(Reserve reserve, LocalDate date) {
		// TODO Auto-generated constructor stub
		this.reserve = reserve;
		this.cancelDate = date;
	}

	@Override
	public boolean isCancelled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Reserve getReserve() {
		// TODO Auto-generated method stub
		return this.reserve;
	}

	public LocalDate cancellationDate() {
		return this.cancelDate;
	}

	@Override
	public void cancel() {}

	@Override
	public void update() {}
}
