package sa.booking;

import java.time.LocalDate;

import sa.booking.reserveStates.IReserveState;

public class ReserveOccupied implements IReserveState {

	private Reserve			reserve;

	public ReserveOccupied(Reserve reserve) {
		// TODO Auto-generated constructor stub
		this.reserve  = reserve;
		this.getReserve().getBooking().getTimer().register(this, this.getReserve().getCheckOut());
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		// La cancelación es asentada en el sistema y se envía un mail al dueño del inmueble informándole sobre
		// el hecho. Debe ser posible enviar por email alguna de las reservas realizadas.
		this.getReserve().setState(new ReserveCancelled(this.getReserve(), LocalDate.now()));
		this.getReserve().cancelled();
	}

	@Override
	public Reserve getReserve() {
		// TODO Auto-generated method stub
		return this.reserve;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		// Se terminó la ocupación
		this.getReserve().getBooking().getTimer().unregister(this, this.getReserve().getCheckOut());
		this.getReserve().finished();
	}

	@Override
	public void approve() {}

	@Override
	public void decline() {}
}
