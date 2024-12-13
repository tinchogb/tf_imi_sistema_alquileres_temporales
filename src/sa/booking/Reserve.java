package sa.booking;

import java.time.LocalDate;

import sa.booking.reserveStates.IReserveState;
import sa.users.Tenant;

public class Reserve {

	private Booking booking;
	private Period period;
	private Tenant tenant;
	private double price;
	private IReserveState state = null;
	private LocalDate cancellationDate = null;

	public Reserve(Booking b, Tenant t, Period p) {
		// TODO Auto-generated constructor stub
		this.booking = b;
		this.period	 = p;
		this.tenant	 = t;
		this.price	 = this.booking.priceBetween(this.getCheckIn(), this.getCheckOut());
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

	public void approve() {
		// TODO Auto-generated method stub
		this.state.approve();
	}

	public void decline() {
		this.state.decline();
	}
	
	public void cancel() {
		// TODO Auto-generated method stub
		this.state.cancel();
	}

	public LocalDate getCancellationDate() {
		return this.cancellationDate;
	}
	
	public void setCancellationDate(LocalDate date) {
		this.cancellationDate = date;
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

	void finished() {
		// TODO Auto-generated method stub
		this.getBooking().handleFinalization(this);
	}

	void cancelled() {
		// TODO Auto-generated method stub
		this.getBooking().handleCancellation(this);
	}

	void approved() {
		// TODO Auto-generated method stub
		// TODO: debería cobrarle al Tenant porque ya fue aceptada (no fue modelado porque no lo pide el enunciado)
		this.getTenant().reserveApproved(this);
		this.getTenant().sendEmail(this, "Se acaba de aceptar tu reserva.");
		this.getBooking().handleApprovation(this);
	}

	public void declined() {
		// TODO Auto-generated method stub
		// TODO: el owner debería olvidar esta reserve
		this.getTenant().reserveDeclined(this);
		this.getBooking().handleDeclination(this);
	}
}
