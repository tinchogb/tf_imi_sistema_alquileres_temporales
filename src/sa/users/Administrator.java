package sa.users;


import java.util.List;

import sa.accomodationsite.AccomodationSite;
import sa.booking.Reserve;
import sa.properties.AmenityType;
import sa.properties.PropertyType;


public class Administrator extends User {
	
	private AccomodationSite accomodationSite;
	
	public Administrator(String fullName, int telephone, String mail, AccomodationSite accomodationSite) {
	
		super(fullName, telephone, mail);
		
		this.accomodationSite = accomodationSite;
	}
	
	public AccomodationSite getAccomodationSite() {
		return this.accomodationSite;
	}
	
	public void setAccomodationSite(AccomodationSite accomodationSite) {
		this.accomodationSite = accomodationSite;
	}
	

	@Override
	public void summary() {
		// TODO Auto-generated method stub
		/**
		 * aca queda vacío 
		 * */
		
	}
	
	
	public void addAllowedPropertyTypes(PropertyType allowedProperty) {
		
		/**
		 * 
		 * da de alta los tipos de inmuebles, le delega al accomodationSite y el accomodation site chekea 
		 * si es un tipo valido dentro de sus tipos de propiedades habilitados.
		 * 
		 * 
		 * */
		
		this.getAccomodationSite().setAllowedProperties(allowedProperty);
	}
	
	public void allowedAmenities(AmenityType allowedAmenity) {
		/**
		 * 
		 * da de alta los tipos de inmuebles, le delega al accomodationSite y el accomodation site chekea 
		 * si es un tipo valido dentro de sus tipos de propiedades habilitados.
		 *  
		 * */
		this.getAccomodationSite().setAllowedAmenities(allowedAmenity);
		
	}
	
	public List<Tenant> bestTenants(List<Tenant> tenants) {
		
		/**
		 * recibe una lista de tenants por parametro, y la ordena entre los primeros 10 mejores tenants que 
		 * alquilaron mas veces en el sitio. 
		 * 
		 * 
		 * */
		
		return 	tenants.stream()
			    	   .sorted((tenant1, tenant2) -> Integer.compare(
			    				this.getAccomodationSite().allReservesOfTheTenant(tenant2).size(),
			    				this.getAccomodationSite().allReservesOfTheTenant(tenant1).size()
			    			)) 
			    	   .limit(10) 
			    	   .toList(); 
		
		
		
	}
	
//	public List<Property> propertiesToBeReserve() {
//		/**
//		 * primero filtra la lista y se queda con los bookings disponibles, y luego la transforma en una lista de propiedades disponibles
//		 * 
//		 * 
//		 * */
//		// TODO: como no existe el estado disponible ahora, tampoco se guarda una reserva antes de que pase a estado reservada, o 
//		// a este metodo lo borro, o cambio y digo el porcentaje de reservas en estado reservadas, no las disponibles, seria algo asi 
//		// como, la lista de reservas reservadas dividido el total de la lista de reservas formales, en realidad tampoco porque todas las 
//		// reservas de la lista de reservas formales son reservas que estan en alguno de los 3 estados, y no entran a esa lista sino pasan por ser reservas reservadas
//		// si no sale borrarlo, tampoco veo que lo pida el enunciado 
//		return this.getAccomodationSite().getVacantProperties().stream()
//															   .map(actualBooking -> actualBooking.getProperty())
//															   .toList();
//										
//	}
	
	public double occupancyRate() {
		
		/**
		 * toma la lista de bookings, primero verifica que no sea cero la cantidad de elementos, porque no se puede 
		 * dividir por cero, luego toma la cantidad de bookings reservados y los divide por el total de bookings de 
		 * la pagina
		 * 
		 * */
		
		// TODO: HAY que adaptarlo a lo nuevo, el approved bookings ya no existe, seria un getOccupiedBookings()
		
		if (this.getAccomodationSite().getBookings().size() == 0) {
	        return 0.0; 
	    }
		
		return ((double) this.getAccomodationSite().getOccupiedReserves().size() /
				 this.getAccomodationSite().getAllReserves().size()) * 100;
	}

	@Override
	public void sendEmail(Reserve reserve, String message) {
		// TODO Auto-generated method stub
		
	}
}