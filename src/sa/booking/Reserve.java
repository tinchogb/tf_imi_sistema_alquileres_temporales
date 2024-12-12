package sa.booking;

import java.time.LocalDate;

import sa.booking.reserveStates.IReserveState;
import sa.booking.reserveStates.ReserveBooked;
import sa.booking.reserveStates.ReserveCancelled;
import sa.booking.reserveStates.ReserveWaiting;
import sa.booking.reserveStates.Timer;
import sa.subscriptions.INotifyTimerSubscriber;
import sa.users.Tenant;

public class Reserve {

	private Booking booking;
	private Period period;
	private Tenant tenant;
	private double price;
	private IReserveState state;

	public Reserve(Booking b, Tenant t, Period p) {
		// TODO Auto-generated constructor stub
		this.booking = b;
		this.period	 = p;
		this.tenant	 = t;
		this.price	 = this.booking.priceBetween(this.getCheckIn(), this.getCheckOut());
		this.state	 = new ReserveWaiting(this);
	}

	public void setState(IReserveState state) {
		// TODO Auto-generated method stub
		this.state = state;
	}

	public IReserveState getState() {
		// TODO Auto-generated method stub
		return this.state;
	}

	public Tenant getTenant() {
		// TODO Auto-generated method stub
		return this.tenant;
	}

	public void approve() { // El Owner aprueba la solicitud de la reserva.
		// TODO Auto-generated method stub
		// TODO: el owner debería olvidar esta reserve 
		this.getBooking().getProperty().getOwner().cleanRequestedReserve();
		this.getBooking().removeWaiting(this);
		this.setState(new ReserveBooked(this));
		this.getBooking().addReserve(this);
		// TODO: debería cobrarle al Tenant porque ya fue aceptada (no fue modelado porque no lo pide el enunciado)
		this.getTenant().reserveApproved(this);
		this.getTenant().sendEmail(this, "Se acaba de aceptar tu reserva.");
	}

	public void decline() { // El Owner rechaza la solicitud de la reserva.
		// TODO: el owner debería olvidar esta reserve
		this.getBooking().getProperty().getOwner().cleanRequestedReserve();
		this.getBooking().removeWaiting(this);
		this.getTenant().reserveDeclined(this);
	}
	
	public void cancel() {
		// TODO Auto-generated method stub
		this.state.cancel();
	}

	public Period getPeriod() {
		// TODO Auto-generated method stub
		return this.period;
	}

	public LocalDate getCheckIn() {
		// TODO Auto-generated method stub
		return this.getPeriod().start();
	}

	public LocalDate getCheckOut() {
		// TODO Auto-generated method stub
		return this.getPeriod().end();
	}

	public Booking getBooking() {
		// TODO Auto-generated method stub
		return this.booking;
	}

	public double getPrice() {
		// TODO Auto-generated method stub
		return this.price;
	}
	
	public void setPrice(double d) {
		this.price = d; 
		
	}

	public void finished() {
		// TODO Auto-generated method stub
		// Pasos a realizar:
		// 1. Sacar de reserves que administra Booking
		// 2. Tenant tiene que calificar al Owner y Property
		// 3. Owner tiene que calificar al Tenant
		this.getBooking().removeReserve(this);
		this.triggerQualification();
	}

	private void triggerQualification() {
		// TODO Auto-generated method stub
		this.getTenant().qualify(this.getBooking().getProperty());
		this.getTenant().qualify(this.getBooking().getProperty().getOwner());
		this.getBooking().getProperty().getOwner().qualify(this.getTenant());
	}

	public void cancelled() {
		// TODO Auto-generated method stub
		this.getBooking().handleCancellation(this);
	}
}
