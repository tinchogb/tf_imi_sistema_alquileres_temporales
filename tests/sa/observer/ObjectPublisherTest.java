package sa.observer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ObjectPublisherTest {

	private ObjectPublisher test;
	@BeforeEach
	void setUp() throws Exception {
		test = new ObjectPublisher();
	}

	@Test
	void newPublisherTest() {
		assertNotNull(test);
	}
	
	@Test
	void publisherMethodTest() {
		test.publish("Publicacion");
	}

}
