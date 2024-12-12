package sa.properties;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PictureTest {
	
	String description;
	Picture picture;
	
	@BeforeEach
	void setUp() throws Exception {
		
		description = "frente de la casa";
		picture = new Picture(description);
	}

	@Test
	void testSummary() {
		final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		picture.showPicture();
		assertEquals(outContent.toString(), "Picture Description: " + System.lineSeparator()
					 											    + "Description: frente de la casa"
					 											    + System.lineSeparator());
	}

}
