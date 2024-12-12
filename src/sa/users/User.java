package sa.users;

import java.util.ArrayList;
import java.util.List;

import sa.booking.Reserve;
import sa.properties.Rankeable;
import sa.properties.Review;

public abstract class User implements Rankeable {
	protected String fullName;
	protected int telephone;
	protected String mail;
	protected List<Review> reviews;
	protected List<Reserve> reserves;
	
	public User(String fullName, int telephone, String mail) {
		this.fullName = fullName;
		this.telephone = telephone;
		this.mail = mail;
		this.reviews = new ArrayList<Review>();
		this.reserves = new ArrayList<Reserve>();
	}
	
	public List<Review> getReviews() {
		return this.reviews;
	}
	
	public List<Reserve> getReserves() {
		return this.reserves;
	}

	public abstract void sendEmail(Reserve reserve, String message );
	// TODO: debería enviarse un mail con la data pasada por argumento

	public abstract void summary();
	
	@Override
	public double getRank() {
		
		
		/**
		 * acá lo mismo que en property.
		 * 
		 * 
		 * */
		return this.getReviews().stream()
								.mapToInt(rewiew -> rewiew.getRating())
								.average()
								.orElse(0.0);

		
	}

}
