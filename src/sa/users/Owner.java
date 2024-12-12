package sa.users;


import java.util.ArrayList;
import java.util.List;

import sa.booking.Reserve;
import sa.properties.Property;

public class Owner extends User {
	
	private List<Property> 	properties;
	private Reserve reserveRequested;

	public Owner(String fullName, int telephone, String mail) {
	
		super(fullName, telephone, mail);
		
		this.properties = new ArrayList<Property>();
	}
	
	@Override
	public void summary() {
		// TODO Auto-generated method stub
		/**
		 * aca tambien queda vacío
		 */
	}

	public List<Property> getProperties() {
		return this.properties;
	}
	
	public void reserveRequested(Reserve reserve) {
		this.setReserveRequested(reserve);
		// Acá el owner puede visualizar datos del tenant
		reserve.getTenant().summary();
	}
	
	public void setReserveRequested(Reserve reserve) {
		this.reserveRequested = reserve;
	}
	
	public Reserve getReserveRequested() {
		return this.reserveRequested;
	}
	
	public void cleanRequestedReserve() {
		this.reserveRequested = null;
	}

	public void qualify(Tenant tenant) {
		// TODO Auto-generated method stub
		// La PERSONA propietaria debería calificar al inquilino por medio de un Review
		// Luego se adjunta el Review al historial de Reviews del Tenant donde
		// finalmente se calcula su ranking.
	}

	@Override
	public void sendEmail(Reserve reserve, String message) {
		// TODO Auto-generated method stub
		// envío mensaje y reserva por mail al Owner
	}
}
