package sa.booking;


import java.time.LocalDate;

import sa.booking.reserveStates.IReserveState;

public class ReserveBooked implements IReserveState {

	private Reserve			reserve;

	public ReserveBooked(Reserve reserve) {
		// TODO Auto-generated constructor stub
		this.reserve  	  = reserve;
		this.getReserve().getBooking().getTimer().register(this, this.getReserve().getCheckIn());
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		// La cancelación es asentada en el sistema y se envía un mail al dueño del inmueble informándole sobre
		// el hecho. Debe ser posible enviar por email alguna de las reservas realizadas.
		this.getReserve().setState(new ReserveCancelled(this.getReserve(), LocalDate.now()));
		// el sistema (que ES un observer) debe registrar la cancelación y puede avisarle al propietario y al Tenant
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
		this.getReserve().getBooking().getTimer().unregister(this, this.getReserve().getCheckIn());
		this.getReserve().setState(new ReserveOccupied(this.getReserve()));
	}

	@Override
	public void approve() {}

	@Override
	public void decline() {}


}
