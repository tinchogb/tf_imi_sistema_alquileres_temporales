package sa.users;

import sa.booking.Reserve;
import sa.properties.Property;
import sa.properties.Review;

public class Tenant extends User {
	
	public Tenant(String fullName, int telephone, String mail) {
		
		super(fullName, telephone, mail);
	}

	@Override
	public void summary() {
		// TODO Auto-generated method stub
		/**
		 * 
		 * * hacer lo mismo que en property 
		 */
		System.out.println("Tenant Summary:");
	    System.out.println("name: " + this.fullName);
	    System.out.println("telephone: " + this.telephone);
	    System.out.println("mail: " + this.mail);
		
	}	

	public void reserveDeclined(Reserve reserve) {
		// recibe el rechazo de una reserva
		// TODO: El enunciado no dice nada respecto a los rechazos.
	}

	public void reserveApproved(Reserve reserve) {
		// TODO Auto-generated method stub
		this.getReserves().add(reserve);
	}

	public void qualify(Property property) {
		// TODO Auto-generated method stub
		// La PERSONA inquilina debería calificar a:
		// - la propiedad por medio de un Review
		// Luego se adjuntan cada Review a su respectivo historial de Reviews donde
		// finalmente se calcula cada ranking.
	}

	public void qualify(Owner owner) {
		// TODO Auto-generated method stub
		// La PERSONA inquilina debería calificar al:
		// - propietario de la propiedad por medio de un Review (property.getOwner())
		// Luego se adjuntan su Review a su respectivo historial de Reviews donde
		// finalmente se calcula su ranking.
	}


	@Override
	public void sendEmail(Reserve reserve, String message) {
		// TODO Auto-generated method stub
		// envío mensaje y reserva por mail al Tenant
	}
}
