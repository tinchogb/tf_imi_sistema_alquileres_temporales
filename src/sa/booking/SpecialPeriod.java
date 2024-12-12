package sa.booking;


import java.time.LocalDate;

public class SpecialPeriod extends Period {

	private double		price;
	
	public SpecialPeriod(double price, LocalDate begin, LocalDate end) {
		// TODO Auto-generated constructor stub
		super(begin, end);
		this.price	= price;
	}
	
	public double price() {
		// TODO Auto-generated method stub
		return this.price;
	}

	public void setPrice(double newPrice) {
		// TODO Auto-generated method stub
		this.price = newPrice;
	}

}
