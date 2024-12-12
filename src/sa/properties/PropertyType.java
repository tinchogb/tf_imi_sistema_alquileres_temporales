package sa.properties;

public class PropertyType {
	private String type;
	private String description;
	
	public PropertyType(String type, String description) {
		this.type = type;
		this.description = description;
	}
	
	public String type() {
		return this.type;
	}
	
	public String description() {
		return this.description;
	}
}
