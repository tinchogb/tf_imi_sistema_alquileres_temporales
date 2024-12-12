package sa.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PropertyTypeTest {
	
	String type;
	String description;
	PropertyType propertyType;

	@BeforeEach
	void setUp() throws Exception {
		
		type = "casa";
		description = "quinta";
		propertyType = new PropertyType(type, description);
		
	}

	@Test
	void getTypeTest() {
		assertEquals(type, propertyType.type());
	}
	
	@Test
	void getDescriptionTest() {
		assertEquals(description, propertyType.description());
	}

}
