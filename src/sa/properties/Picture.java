package sa.properties;

public class Picture {
	private String description;
	
	public Picture(String description) {
		this.description = description;
	}
	
	public void showPicture() {
		
		/**
		 * print en pantalla de la descripcion de la foto
		 * */
		System.out.println("Picture Description: ");
	    System.out.println("Description: " + this.description);
	}
	
}
