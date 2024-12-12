package sa.booking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class PricerTest {

	private Pricer				pricer;
	private double				basePrice;
	private SpecialPeriod		period1;
	private SpecialPeriod		period2;
	private List<SpecialPeriod>	periods;
	
	private double		price1;
	private LocalDate	begin1;
	private LocalDate	end1;
	
	private double		price2;
	private LocalDate	begin2;
	private LocalDate	end2;
	
	@BeforeEach
	void setUp() throws Exception {
		// DOCs
		this.basePrice = 5;
		
		this.price1	= 1;
		this.begin1	= LocalDate.now();
		this.end1	= this.begin1.plusDays(2);
		
		this.price2	= 2;
		this.begin2	= this.end1.plusDays(2);
		this.end2	= this.begin2.plusDays(2);
		
		this.period1 = spy(new SpecialPeriod(this.price1, this.begin1, this.end1));
		this.period2 = spy(new SpecialPeriod(this.price2, this.begin2, this.end2));
		this.periods = new ArrayList<SpecialPeriod>();
		this.periods.add(this.period1);
		
	
		// SUT
		this.pricer	= new Pricer(this.basePrice, this.periods);
	}

	@Test
	void testConstructor() {
		assertNotNull(this.pricer);
	}

	@Test
	void testAddSpecialPeriod() {
		assertEquals(1, this.periods.size());
		this.pricer.addSpecialPeriod(this.period2);
		assertEquals(2, this.periods.size());
	}

	@Test
	void testRemoveSpecialPeriod() {
		assertEquals(1, this.periods.size());
		assertTrue(this.periods.containsAll(Arrays.asList(this.period1)));
		this.pricer.addSpecialPeriod(this.period2);
		assertEquals(2, this.periods.size());
		this.pricer.removeSpecialPeriod(this.period1);
		assertEquals(1, this.periods.size());
		assertTrue(this.periods.containsAll(Arrays.asList(this.period2)));
	}

	@Test
	void testPrice() {
		assertTrue(this.periods.containsAll(Arrays.asList(this.period1)));
		assertEquals(this.basePrice, this.pricer.price(this.period1.start().minusDays(1)));
		assertEquals(this.price1, this.pricer.price(this.period1.start()));
		assertEquals(this.price1, this.pricer.price(this.period1.start().plusDays(1)));
		assertEquals(this.basePrice, this.pricer.price(this.period1.end().plusDays(1)));
	}
	
	@Test
	void testPriceBetweenBelongs() {
		assertTrue(this.periods.containsAll(Arrays.asList(this.period1)));
		assertEquals(this.basePrice+this.price1, this.pricer.priceBetween(this.period1.start().minusDays(1), this.period1.start()));
		assertEquals(this.basePrice+3*this.price1, this.pricer.priceBetween(this.period1.start().minusDays(1), this.period1.end()));
		this.pricer.addSpecialPeriod(this.period2);
		assertTrue(this.periods.containsAll(Arrays.asList(this.period1, this.period2)));
		assertEquals(2*this.basePrice+3*this.price1+this.basePrice+this.price2, 
				this.pricer.priceBetween(this.period1.start().minusDays(2), this.period2.start()));
	}

	@Test
	void testSetBasePrice() {
		// TODO Auto-generated method stub
		// Chequeado con un período conocido
		assertEquals(this.basePrice, this.pricer.price(this.period1.start().minusDays(1)));
		this.pricer.setBasePrice(this.basePrice+10);
		assertEquals(this.basePrice+10, this.pricer.price(this.period1.start().minusDays(1)));
	}

	@Test
	void testGetBasePrice() {
		assertEquals(this.basePrice, this.pricer.getBasePrice());
	}

	@Test
	void testGetSPeriods() {
		assertEquals(this.periods, this.pricer.getSPeriods());
	}
}
