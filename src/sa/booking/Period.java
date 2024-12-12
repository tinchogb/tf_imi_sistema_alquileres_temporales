package sa.booking;

import java.time.LocalDate;

public class Period {

	protected LocalDate	start;
	protected LocalDate	end;

	public Period(LocalDate start, LocalDate end) {
		// TODO Auto-generated constructor stub
		this.start = start;
		this.end   = end;
	}

	public LocalDate start() {
		// TODO Auto-generated method stub
		return this.start;
	}

	public LocalDate end() {
		// TODO Auto-generated method stub
		return this.end;
	}

	public boolean belongs(LocalDate date) {
		// TODO Auto-generated method stub
		return date.isEqual(this.start()) 	||
				   date.isEqual(this.end()) 	|| 
				   (date.isAfter(this.start()) 	&& date.isBefore(this.end()));
	}

}
