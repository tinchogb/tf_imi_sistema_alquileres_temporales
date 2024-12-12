package sa.properties;

import java.util.ArrayList;
import java.util.List;


import sa.users.Owner;

public class Property implements Rankeable {
	
	private double area;
	private String country;
	private String city;
	private String address;
	private int maxGuests;
	private String description;
	private int maxPicture;
	private List<AmenityType> amenities;
	private PropertyType type;
	private List<Picture> pictures;
	private Owner owner;
	private List<Review> reviews;
	
	
	public Property(double area, String country, String city, String address, int maxGuests, String description,
			List<AmenityType> amenities, int maxPicture, PropertyType type, Owner owner) {
		this.area = area;
		this.country = country;
		this.city = city;
		this.address = address;
		this.maxGuests = maxGuests;
		this.description = description;
		this.maxPicture = maxPicture;
		this.type = type;
		this.owner = owner;
		this.amenities = amenities;
		this.pictures = new ArrayList<Picture>();
		this.reviews = new ArrayList<Review>();
	}

	// Para hacer DOC
	public Property(double area, String country, String city, String address, int maxGuests, String description, List<AmenityType> amenities, int maxPicture,
			   PropertyType type, Owner owner, List<Picture> pictures, List<Review> reviews) {
	this.area = area;
	this.country = country;
	this.city = city;
	this.address = address;
	this.maxGuests = maxGuests;
	this.description = description;
	this.maxPicture = maxPicture;
	this.type = type;
	this.owner = owner;
	this.amenities = amenities;
	this.pictures = pictures;
	this.reviews = reviews;
	
}
	
	public double getArea() {
		return area;
	}

	public String getCountry() {
		return country;
	}

	public String getAddress() {
		return address;
	}

	public String getDescription() {
		return description;
	}

	public int getMaxPicture() {
		return maxPicture;
	}

	public PropertyType getType() {
		return type;
	}

	public List<Picture> getPictures() {
		return pictures;
	}

	public void summary() {
		
		// TODO Auto-generated method stub
		/**
		 * imprimir en pantalla cada atributo de property, con un sout para cada uno o buscar una manera para imprimir todos, en cualquier formato
		 * */
		System.out.println("Property Summary:");
	    System.out.println("Area: " + this.area);
	    System.out.println("Country: " + this.country);
	    System.out.println("City: " + this.city);
	    System.out.println("Address: " + this.address);
	    System.out.println("Max Guests: " + this.maxGuests);
	    System.out.println("Description: " + this.description);
	    System.out.println("Max Pictures: " + this.maxPicture);
	    System.out.println("Type: " + this.type);
	    System.out.println("Owner: " + this.owner);
	    System.out.println("Amenities: " + this.amenities);
	    System.out.println("Pictures: " + this.pictures);
	    System.out.println("Reviews: " + this.reviews);
		
		
	}
	
	public String getCity() {
		return this.city;
	}
	
	public List<Review> getReviews() {
		return this.reviews;
	}
	
	@Override
	public double getRank() {
		/**
		 * calcula el promedio del rating de todos los reviews, que son las vistas que tiene una propiedad y el rating con la que 
		 * la valoró cada usuario en el sitio
		 * 
		 * */
		return this.getReviews().stream()
								.mapToInt(rewiew -> rewiew.getRating())
								.average()
				                .orElse(0.0);
	}

	public int getMaxGuests() {
		//Lo agrego Ivan
		return this.maxGuests;
	}

	public List<AmenityType> getAmenities() {
		return this.amenities;
	}


	public PropertyType getPropertyType() {
		return this.type;
	}
	

	public Owner getOwner() {
		return this.owner;
	}
	
 
}
























































