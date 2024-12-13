package sa.booking;

import sa.booking.reserveStates.IReserveState;

public class ReserveWaiting implements IReserveState {

	private Reserve			reserve;

	public ReserveWaiting(Reserve reserve) {
		// TODO Auto-generated constructor stub
		this.reserve = reserve;
	}

	@Override
	public void update() {}

	@Override
	public void cancel() {}

	@Override
	public Reserve getReserve() {
		// TODO Auto-generated method stub
		return this.reserve;
	}

	@Override
	public void approve() {
		// TODO Auto-generated method stub
		// El Owner aprueba la solicitud de la reserva.
		this.getReserve().setState(new ReserveBooked(this.getReserve()));
		this.getReserve().approved();

	}

	@Override
	public void decline() {
		// TODO Auto-generated method stub
		// El Owner rechaza la solicitud de la reserva.
		this.getReserve().declined();
	}
}
