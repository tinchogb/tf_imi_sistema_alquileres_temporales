package sa.accomodationsite;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import sa.searcher.simpleQuery.IQuery;
import sa.properties.AmenityType;
//import sa.payments.PaymentMethod;
import sa.booking.*;

import sa.properties.Property;
import sa.properties.PropertyType;
import sa.users.Tenant;
import sa.booking.reserveStates.Timer;
import sa.cancellation.ICancellationPolicy;


public class AccomodationSite {
	
	private List<Booking> bookings;
	private List<PropertyType> allowedProperties;
	private List<AmenityType> allowedAmenities;
	
	public AccomodationSite(List<Booking> bs, List<AmenityType> as, List<PropertyType> ps) {
		this.bookings = bs;
		this.allowedAmenities = as;
		this.allowedProperties = ps;
	}
	
	public AccomodationSite() {
		this.bookings = new ArrayList<Booking>();
		this.allowedAmenities = new ArrayList<AmenityType>();
		this.allowedProperties = new ArrayList<PropertyType>();
	}
	
	
	public void createBooking(ICancellationPolicy policy, Property property, LocalDate begin, LocalDate end, List<PaymentMethod> paymentMethods,
							  double pricePerDayWeekday, List<SpecialPeriod> periods, Timer timer) {
		/**
		 * crea un booking nuevo y lo agrega a la lista de bookings. Antes de agregarlo verifica que la propiedad del booking
		 * tenga un tipo de propiedad valido y unos tipos de servicios validos para sitio web, dados por el administrador.
		 * 
		 * */
		
		Booking newBooking = new Booking(policy, property, begin, end, paymentMethods,
											pricePerDayWeekday, periods, timer);
		//
		if(this.verifyPropertyType(property) && this.verifyAmenityType(property)) {
			
			this.bookings.add(newBooking);
		} else {
			throw new RuntimeException("la propiedad o el servicio dados no son válidos para el sitio web");
		}
		
		
	}
	
	
	public boolean verifyAmenityType(Property property) {
		/**
		 * verifica si el tipo de la propiedad dada pertenece al tipo de propiedad aceptado por el sitio web
		 * testear a parte
		 * */
		return this.getAllowedAmenities().containsAll((Collection<?>) (property.getAmenities()));
	}

	public boolean verifyPropertyType(Property property) {
		/**
		 * verifica si el tipo de los servicios de la propiedad dada pertenecen al tipo de servicios aceptado por el sitio web
		 * 
		 * */
		
		return this.getAllowedProperties().contains(property.getPropertyType());
	}

	public List<Booking> getBookings() {
		return this.bookings;
	}
	
	public List<PropertyType> getAllowedProperties() {
		return this.allowedProperties;
	}
	
	public List<AmenityType> getAllowedAmenities() {
		return this.allowedAmenities;
	}
	
	public void setAllowedProperties(PropertyType allowedProperty) {
		
		/**
		 * Agrega el tipo de propiedad valido para el sitio, dado por el administrador
		 * */
		
		this.getAllowedProperties().add(allowedProperty);
		
		
	}
	
	public void setAllowedAmenities(AmenityType allowedAmenity) {
		
		/**
		 * Agrega los tipos de servicios validos para el sitio, dados por el administrador
		 * 
		 * */
		
		this.getAllowedAmenities().add(allowedAmenity);
		
	}
	
	
	public void viewProperty(Booking booking) {
		
		 /*
		  * llama al metodo sumary de property para printear en pantalla los atributos de la propiedad
		  * 
			 * */
		
		booking.getProperty().summary();
		 
	}
	
	public List<Reserve> getOccupiedReserves() {
		
		return this.getBookings().stream()
			    				 .flatMap(booking -> booking.getReserves().stream())
			    				 .filter(reserve -> reserve.getCheckIn().isBefore(LocalDate.now())
			    						 			&& reserve.getCheckOut().isAfter(LocalDate.now()))
			    				 .toList();
	}
	
	
	public List<Reserve> allReservesOfTheTenant(Tenant tenant) {
		
		/*
		 * Recorre la lista de bookings y la filtra por el tenant dado, y retorna una lista de bookings del tenant dado
		 * 
		 * */
	
		return this.getBookings().stream()
		         				 .flatMap(booking -> booking.getReserves().stream())
		         				 .filter(actualReserve -> actualReserve.getTenant().equals(tenant))
		         				 .toList();
	}
	
		
	public List<Reserve> futureReservesOfTheTenant(Tenant tenant) {
		
		
		/**
		 * primero filtra la lista de bookings y se queda con todos los alquileres del tenant dado, luego, a esa lista 
		 * resultante, la filtra y se queda con los bookings con un checkIn superior al dia de hoy, es decir, los bookings
		 * futuros
		 * 
		 * */
		
		LocalDate today = LocalDate.now();
		
		return this.allReservesOfTheTenant(tenant).stream()
						  	  		      .filter(actualReserve -> actualReserve.getCheckIn().isAfter(today))
						  	  		      .toList();
		
	}
	
	public Set<String> allReservesCities(Tenant tenant) {
		/**
		 * primero filtra la lista de bookings y se queda con todos los alquileres del tenant dado, luego transforma esa
		 * lista en una lista de ciudades por las que alquiló el tenant dado, y sin elementos repetidos,
		 * sería reutilizar el filtro de la lista de bookings a la lista de reservas del tenant dado, y esa transformarla
		 * a una lista de ciudades.
		 * */
		  
		return this.allReservesOfTheTenant(tenant).stream()
												  .map(reserve -> reserve.getBooking())
				  								  .map(booking -> booking.getProperty())
				  								  .map(property -> property.getCity())
				  								  .collect(Collectors.toSet());
		
	}
	
	public List<Booking> search(IQuery query) {
		/**
		 * recibe un iquery como parametro, luego verifica si el searcher esta vacio o es null, entonces retorna 
		 * todos los bookings del sitio, y sino, almacena el criterio de busqueda dado.
		 *
		 * */

		if(query == null){
			return this.getBookings();} else {
			return query.search(this.getBookings());}
		}
	
	public List<Reserve> getAllReserves() {
		return this.getBookings().stream()
								 .flatMap(booking -> booking.getReserves().stream())
								 .toList();
	}
		
}
	

