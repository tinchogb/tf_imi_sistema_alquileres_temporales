package sa.observer;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectScreenTest {

	private ObjectScreen test;
	@BeforeEach
	void setUp() throws Exception {
		test = new ObjectScreen();
	}

	@Test
	void newScreenTest() {
		assertNotNull(test);
	}
	@Test
	void screenMethodTest() {
		test.popUp("Mensaje", "Rojo", 15);
	}

}
