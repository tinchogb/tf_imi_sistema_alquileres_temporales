package sa.properties;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AmenityTypeTest {

	String type;
	String description;
	AmenityType amenityType;

	@BeforeEach
	void setUp() throws Exception {
		
		type = "impuesto";
		description = "gas";
		amenityType = new AmenityType(type, description);
		
	}

	@Test
	void getTypeTest() {
		assertEquals(type, amenityType.type());
	}
	
	@Test
	void getDescriptionTest() {
		assertEquals(description, amenityType.description());
	}


}
