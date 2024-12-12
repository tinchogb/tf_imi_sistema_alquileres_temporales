package sa.booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;

import java.time.LocalDate;


class SpecialPeriodTest {

	private SpecialPeriod	specialPeriod;
	
	private double		price;
	private LocalDate	begin;
	private LocalDate	end;
	
	@BeforeEach
	void setUp() throws Exception {
		// DOCs
		this.price	= 1;
		this.begin	= LocalDate.now();
		this.end	= this.begin.plusDays(2);
		
		// SUT
		this.specialPeriod	= new SpecialPeriod(this.price, begin, end);
	}

	@Test
	void testConstructor() {
		assertNotNull(this.specialPeriod);
	}
	
	@Test
	void testStart() {
		assertEquals(this.begin, this.specialPeriod.start());
	}

	@Test
	void testEnd() {
		assertEquals(this.end, this.specialPeriod.end());
	}
	
	@Test
	void testBelongs() {
		assertFalse(this.specialPeriod.belongs(this.begin.minusDays(1)));
		assertTrue(this.specialPeriod.belongs(this.begin));
		assertTrue(this.specialPeriod.belongs(this.begin.plusDays(1)));
		assertTrue(this.specialPeriod.belongs(this.end));
		assertFalse(this.specialPeriod.belongs(this.end.plusDays(1)));
	}

	@Test
	void testPrice() {
		assertEquals(this.price, this.specialPeriod.price());
	}
	
	@Test
	void testSetPrice() {
		assertEquals(this.price, this.specialPeriod.price());
		this.specialPeriod.setPrice(this.price*2);
		assertEquals(this.price*2, this.specialPeriod.price());
	}
}
