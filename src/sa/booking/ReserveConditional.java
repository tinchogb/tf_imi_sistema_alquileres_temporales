package sa.booking;

import sa.booking.reserveStates.IReserveState;

public class ReserveConditional implements IReserveState {
	
	private Reserve			reserve;
	
	public ReserveConditional(Reserve reserve) {
		// TODO Auto-generated constructor stub
		this.reserve = reserve;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancel() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Reserve getReserve() {
		// TODO Auto-generated method stub
		return this.reserve;
	}

	@Override
	public void approve() {
		// TODO Auto-generated method stub
		this.getReserve().setState(new ReserveWaiting(this.getReserve()));
	}

	@Override
	public void decline() {}

}
