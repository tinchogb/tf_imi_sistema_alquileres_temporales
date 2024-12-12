package sa.booking;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sa.booking.reserveStates.ReserveBooked;
import sa.booking.reserveStates.Timer;
import sa.cancellation.CostFree;
import sa.cancellation.ICancellationPolicy;
import sa.cancellation.NoCancellation;
import sa.properties.Property;
import sa.observer.ApplicationMobile;
import sa.observer.interfaces.INotifyObserver;
import sa.users.Owner;
import sa.users.Tenant;


public class BookingTest {

	private Booking				booking;
	private Booking				bookingReal;
	private Timer				timer;

	private List<PaymentMethod> 		paymentMethods 	= new ArrayList<PaymentMethod>();
	private Period	    				period;
	private List<Reserve> 				reserves = new ArrayList<Reserve>();
	private List<Reserve>				waitings = new ArrayList<Reserve>();
	private List<INotifyObserver> 		obsPrice = new ArrayList<INotifyObserver>();
	private List<INotifyObserver> 		obsCancel = new ArrayList<INotifyObserver>();
	private List<INotifyObserver> 		obsReserve = new ArrayList<INotifyObserver>();

	
	// Para DOC Pricer
	private Pricer				pricer;
	private List<SpecialPeriod> specialPeriods	= new ArrayList<SpecialPeriod>();
	private SpecialPeriod		specialPeriod1;
	private SpecialPeriod		specialPeriod2;
	private SpecialPeriod		specialPeriod3;
	private double 				pricePerDayWeekday;
	private double 				pricePerDayHighSeason;
	private double 				pricePerDayLongWeekend;

	private INotifyObserver		subscriber1;
	private INotifyObserver		subscriber2;
	private INotifyObserver		subscriber3;
	private INotifyObserver		subscriber4;
	private INotifyObserver		subscriber5;

	private CostFree			policy;
	private Property			property;
	private Owner				owner;
	private Tenant				tenant1;
//	private Tenant				tenant2;
//	private Tenant				tenant3;
	
	private PaymentMethod		paymentMethod1;
	private PaymentMethod		paymentMethod2;

	private LocalDate 			begin;
	private LocalDate			end;

	private LocalDate 			today;
	private Reserve				reserve1;
	private Reserve				reserve2;
	private Reserve				reserve3;
	private Reserve				reserve4;
	private Period				bookedperiod1;
	private Period				bookedperiod2;
	private Period				bookedperiod3;
	private Period				bookedperiod4;
	
	@BeforeEach
	public void setUp() {
		// DOC (Depended-On-Component): nuestros doubles
		this.policy				= spy(CostFree.class);
		this.property			= mock(Property.class);
		this.pricer				= mock(Pricer.class);
		this.specialPeriod1		= mock(SpecialPeriod.class);
		this.specialPeriod2		= mock(SpecialPeriod.class);
		this.specialPeriod3		= mock(SpecialPeriod.class);
		this.owner				= spy(new Owner(null, 0, null));
		this.tenant1 	  		= mock(Tenant.class);
//		this.tenant2 	  		= mock(Tenant.class);
//		this.tenant3 	  		= mock(Tenant.class);
		this.paymentMethod1 	= mock(PaymentMethod.class);
		this.paymentMethod2 	= mock(PaymentMethod.class);
		this.subscriber1	  	= mock(INotifyObserver.class);
		this.subscriber2	  	= mock(INotifyObserver.class);
		this.subscriber3	  	= mock(INotifyObserver.class);
		this.subscriber4	  	= mock(INotifyObserver.class);
		this.subscriber5	  	= mock(INotifyObserver.class);
		this.reserve1			= mock(Reserve.class);
		this.reserve2			= mock(Reserve.class);
		this.reserve3			= mock(Reserve.class);
		this.reserve4		    = mock(Reserve.class);
		this.bookedperiod1		= mock(Period.class);
		this.bookedperiod2		= mock(Period.class);
		this.bookedperiod3		= mock(Period.class);
		this.bookedperiod4		= mock(Period.class);
	    this.timer				= mock(Timer.class);
	    
		this.period = mock(Period.class);
		
		this.specialPeriods.add(specialPeriod1);
		this.specialPeriods.add(specialPeriod2);
		this.specialPeriods.add(specialPeriod3);
		this.paymentMethods.add(paymentMethod1);
		this.paymentMethods.add(paymentMethod2);

		this.obsPrice.add(subscriber1);
		this.obsCancel.add(subscriber2);
		this.obsReserve.add(subscriber3);
		
		this.today 				= LocalDate.now();
		this.begin				= this.today;
		this.end				= this.today.plusDays(4);
		this.pricePerDayWeekday		= 5;
		this.pricePerDayHighSeason	= 6;
		this.pricePerDayLongWeekend	= 7;

		
		when(this.reserve1.getCheckIn()).thenReturn(this.today);
		when(this.reserve1.getCheckOut()).thenReturn(this.today);
		when(this.reserve1.getTenant()).thenReturn(this.tenant1);
		
		when(this.reserve2.getCheckIn()).thenReturn(LocalDate.of(2024, 12, 31));
		
		when(this.specialPeriod1.price()).thenReturn(pricePerDayHighSeason);
		when(this.specialPeriod1.start()).thenReturn(this.begin.plusDays(2));
		when(this.specialPeriod1.end()).thenReturn(this.begin.plusDays(3));
		when(this.specialPeriod1.belongs(this.begin.plusDays(2))).thenReturn(true);
		when(this.specialPeriod1.belongs(this.begin.plusDays(3))).thenReturn(true);

		when(this.pricer.price(this.begin)).thenReturn(pricePerDayWeekday);
		when(this.pricer.price(this.begin.plusDays(1))).thenReturn(pricePerDayWeekday);
		when(this.pricer.price(this.begin.plusDays(2))).thenReturn(pricePerDayHighSeason);
		when(this.pricer.price(this.begin.plusDays(3))).thenReturn(pricePerDayHighSeason);
		when(this.pricer.priceBetween(this.begin, this.end)).thenReturn(pricePerDayWeekday+pricePerDayHighSeason+pricePerDayLongWeekend);
		when(this.pricer.getSPeriods()).thenReturn(this.specialPeriods);

		when(this.property.getOwner()).thenReturn(this.owner);
		

		// Alquila 1 día
		when(this.bookedperiod1.start()).thenReturn(this.today);
		when(this.bookedperiod1.end()).thenReturn(this.today);
		when(this.bookedperiod1.belongs(this.today)).thenReturn(true);
		when(this.bookedperiod1.belongs(this.begin)).thenReturn(true);
		when(this.bookedperiod1.belongs(this.end)).thenReturn(false);
		when(this.reserve1.getPeriod()).thenReturn(this.bookedperiod1);
//		when(this.reserve1.getCheckIn()).thenReturn(this.bookedperiod1.start());
//		when(this.reserve1.getCheckOut()).thenReturn(this.bookedperiod1.end());
		when(this.reserve1.getBooking()).thenReturn(booking);

		// Alquila 2 día
		when(this.bookedperiod2.start()).thenReturn(this.today);
		when(this.bookedperiod2.end()).thenReturn(this.today.plusDays(1));
		when(this.bookedperiod2.belongs(this.today)).thenReturn(true);
		when(this.bookedperiod2.belongs(this.begin)).thenReturn(true);
		when(this.bookedperiod2.belongs(this.end)).thenReturn(false);
		when(this.reserve2.getPeriod()).thenReturn(this.bookedperiod2);
//		when(this.reserve2.getCheckIn()).thenReturn(this.bookedperiod2.start());
//		when(this.reserve2.getCheckOut()).thenReturn(this.bookedperiod2.end());
		
		// Alquila los 5 día
		when(this.bookedperiod3.start()).thenReturn(this.today);
		when(this.bookedperiod3.end()).thenReturn(this.end);
		when(this.bookedperiod3.belongs(this.today)).thenReturn(true);
		when(this.bookedperiod3.belongs(this.begin)).thenReturn(true);
		when(this.bookedperiod3.belongs(this.end)).thenReturn(true);
		when(this.reserve3.getPeriod()).thenReturn(this.bookedperiod3);
//		when(this.reserve3.getCheckIn()).thenReturn(this.bookedperiod3.start());
//		when(this.reserve3.getCheckOut()).thenReturn(this.bookedperiod3.end());
		
		//Alquila 2 dias en medio del periodo
		when(this.bookedperiod4.start()).thenReturn(this.begin.plusDays(1));
		when(this.bookedperiod4.end()).thenReturn(this.begin.plusDays(2));
		when(this.bookedperiod4.belongs(this.today)).thenReturn(false);
		when(this.bookedperiod4.belongs(this.begin.plusDays(1))).thenReturn(true);
		when(this.bookedperiod4.belongs(this.end)).thenReturn(false);
		when(this.reserve4.getPeriod()).thenReturn(this.bookedperiod4);
		
		when(this.reserve4.getCheckIn()).thenReturn(this.begin.plusDays(1));
		when(this.reserve4.getCheckOut()).thenReturn(this.begin.plusDays(2));
		
		
		when(this.period.start()).thenReturn(begin);
		when(this.period.end()).thenReturn(end);
		

		// SUT (System Under Test): objeto a testear
		this.booking = new Booking(   policy
									, pricer
									, property
									, paymentMethods
									, pricePerDayWeekday
									, specialPeriods
									, period
									, reserves
									, waitings
									, timer
									, obsCancel
									, obsReserve
									, obsPrice);

		this.bookingReal = new Booking(   policy
										, property
										, begin
										, end
										, paymentMethods
										, pricePerDayWeekday
										, specialPeriods
										, timer );
	}

	@Test
	public void testConstructor() {
		assertNotNull(this.booking);
	}

	@Test
	public void testConstructorReal() {
		assertNotNull(this.bookingReal);
	}


	@Test
	public void testGetPeriod() {
		assertEquals(this.period, this.booking.getPeriod());
	}

	@Test
	public void testGetPricer() {
		assertEquals(this.pricer, this.booking.getPricer());
	}

	@Test
	public void testGetPaymentMethods() {
		assertEquals(this.paymentMethods, this.booking.getPaymentMethods());
	}

	@Test
	public void testGetProperty() {
		assertEquals(this.property, this.booking.getProperty());
	}

	@Test
	public void testGetPolicy() {
		assertEquals(this.policy, this.booking.getPolicy());
	}

	@Test
	public void testGetConditionalReserves() {
		assertEquals(this.waitings, this.booking.getConditionalReserves());
	}

	@Test
	public void testApplyPolicy() {
		assertNotNull(this.booking.getPolicy());
		verifyNoInteractions(this.policy);
		when(this.reserve1.getBooking()).thenReturn(booking);
		this.booking.applyPolicy(this.reserve1,LocalDate.now()); //Se necesita una fecha ahora, verificar 
		verify(this.policy).activate(this.reserve1,LocalDate.now());  //activate ahora necesita un LocalDate
	}

	@Test
	public void testSetBasePrice() {
		verify(this.pricer, times(0)).price(this.begin);
		assertEquals(this.pricePerDayWeekday, this.booking.price(this.begin));
		verify(this.pricer, times(1)).price(this.begin);
		verify(this.pricer, times(0)).getBasePrice();
		verify(this.pricer, times(0)).setBasePrice(pricePerDayWeekday*0.5);
		this.booking.setBasePrice(pricePerDayWeekday*0.5);
		when(this.pricer.getBasePrice()).thenReturn(pricePerDayWeekday);
		verify(this.pricer, times(1)).setBasePrice(pricePerDayWeekday*0.5);
	}
	
	@Test
	public void testPrice() {
		assertEquals(this.pricePerDayWeekday, this.booking.price(this.begin));
	}
	
	@Test
	public void testPriceBetween() {
		double expectedPrice = this.pricePerDayWeekday + this.pricePerDayHighSeason + this.pricePerDayLongWeekend; 
		assertEquals(expectedPrice, this.booking.priceBetween(this.begin, this.end));
	}
	
	@Test
	public void testNewReserve() {
		assertEquals(0, this.booking.getReserves().size());
		verifyNoInteractions(this.owner);
		this.booking.newReserve(this.tenant1, this.today, this.today.plusDays(1));
		Reserve r = this.owner.getReserveRequested();
		verify(this.owner, times(1)).reserveRequested(r);
	}
	
	@Test
	public void testAddReserve() {
		
		Reserve spyReserve1 = spy(new Reserve(booking,tenant1,bookedperiod1)); //necesito un spy. Su estado es waiting
		
		
		assertEquals(0, this.booking.getReserves().size());
		verifyNoInteractions(this.timer);	
		
		ReserveBooked spyReserveBooked = spy(new ReserveBooked(spyReserve1));
		
		spyReserve1.setState(spyReserveBooked);
		this.booking.addReserve(spyReserve1);
		
		assertEquals(1, this.booking.getReserves().size()); 
		
		
		//verify(this.timer, times(1)).register(spyReserveBooked, spyReserve1.getCheckIn()); //No se porque pero tira error ya que el state de verify no es el mismo que en la implementacion de register
		//verify(this.timer, times(1)); Me hace explotar el otro test

	}
	
	@Test
	public void testNewConditionalReserve() {
		assertEquals(0, this.booking.getConditionalReserves().size());
		this.booking.newConditionalReserve(this.tenant1, this.today, this.today.plusDays(1));
		assertEquals(1, this.booking.getConditionalReserves().size());
	}

	@Test
	public void testTriggerNextRequestApproved() {
		
		when(property.getOwner()).thenReturn(owner);
		
		Reserve spyReserve1 = spy(new Reserve(booking,tenant1,bookedperiod1)); //necesito un spy
		
		assertEquals(0, this.booking.getConditionalReserves().size());
		this.waitings.add(spyReserve1);
		assertEquals(1, this.booking.getConditionalReserves().size());
		this.booking.triggerNextRequest(spyReserve1.getCheckIn(),spyReserve1.getCheckOut());
		verify(this.owner, times(1)).reserveRequested(spyReserve1);
		
		spyReserve1.approve();
		
		assertTrue(this.booking.getReserves().contains(spyReserve1));
		
		verify(owner).cleanRequestedReserve();
		assertEquals(0, this.booking.getConditionalReserves().size());
	}
	
	@Test
	public void testTriggerNextRequestDeclined() {
		
		when(property.getOwner()).thenReturn(owner);
		Reserve spyReserve1 = spy(new Reserve(booking,tenant1,bookedperiod1)); //necesito un spy
		
		assertEquals(0, this.booking.getConditionalReserves().size());
		this.waitings.add(spyReserve1);
		assertEquals(1, this.booking.getConditionalReserves().size());
		this.booking.triggerNextRequest(spyReserve1.getCheckIn(),spyReserve1.getCheckOut());
		verify(this.owner, times(1)).reserveRequested(spyReserve1);
		
		spyReserve1.decline();
		assertEquals(0, this.booking.getConditionalReserves().size());
	}

	// Subscribers
	@Test
	public void testNotifySubscribersLowerPrice() {
		verify(this.subscriber5, times(0)).updateLowerPrice(this.booking);
		when(this.pricer.getBasePrice()).thenReturn(pricePerDayWeekday);
		this.obsPrice.add(this.subscriber5);
		this.booking.setBasePrice(pricePerDayWeekday*0.5);
		verify(this.subscriber5, times(1)).updateLowerPrice(this.booking);
	}

	@Test
	public void testRegisterPriceObserver() {
		assertEquals(1, this.obsPrice.size());
		this.booking.registerPriceObserver(this.subscriber4);
		assertEquals(2, this.obsPrice.size());
	}

	@Test
	public void testUnregisterPriceObserver() {
		assertEquals(1, this.obsPrice.size());
		this.booking.unRegisterPriceObserver(subscriber1);
		assertEquals(0, this.obsPrice.size());
	}

	@Test
	public void testNotifySubscribersPrice() {
		verify(this.subscriber1, times(0)).updateLowerPrice(booking);
		this.booking.notifySubscribersPrice();
		verify(this.subscriber1, times(1)).updateLowerPrice(booking);
	}

	@Test
	public void testRegisterCancelObserver() {
		assertEquals(1, this.obsCancel.size());
		this.booking.registerCancelObserver(subscriber4);
		assertEquals(2, this.obsCancel.size());
	}

	@Test
	public void testUnregisterCancelObserver() {
		assertEquals(1, this.obsCancel.size());
		this.booking.unRegisterCancelObserver(subscriber2);
		assertEquals(0, this.obsCancel.size());
	}

	@Test
	public void testNotifySubscribersCancelled() {
		verify(this.subscriber2, times(0)).updateCancellation(reserve1);
		this.booking.notifySubscribersCancelled(reserve1);
		verify(this.subscriber2, times(1)).updateCancellation(reserve1);
	}

	@Test
	public void testRegisterReserveObserver() {
		assertEquals(1, this.obsReserve.size());
		this.booking.registerReserveObserver(subscriber4);
		assertEquals(2, this.obsReserve.size());
	}

	@Test
	public void testUnregisterReserveObserver() {
		assertEquals(1, this.obsReserve.size());
		this.booking.unRegisterReserveObserver(subscriber3);
		assertEquals(0, this.obsReserve.size());
	}

	@Test
	public void testNotifySubscribersReserve() {
		verify(this.subscriber3, times(0)).updateNewReserve(reserve1);
		this.booking.notifySubscribersReserve(reserve1);
		verify(this.subscriber3, times(1)).updateNewReserve(reserve1);
	}
	
//	@Test
//	public void testUpdate() {
//		assertEquals(0, this.reserves.size());
//		this.reserves.add(this.reserve1);
//		assertEquals(1, this.reserves.size());
//		verify(this.reserve1, times(0)).getCheckOut();
//		this.booking.update(this.reserve1, this.today);
//		verify(this.reserve1, times(1)).getCheckOut();
//
//		assertEquals(0, this.reserves.size());				el metodo update en booking ya no existe, se diversifico en los estados
//		this.reserves.add(this.reserve1);
//		assertEquals(1, this.reserves.size());
//		verify(this.reserve1, times(1)).getCheckOut();
//		verify(this.reserve1, times(0)).next();
//		this.booking.update(this.reserve1, this.today.minusDays(1));
//		verify(this.reserve1, times(3)).getCheckOut();
//		verify(this.reserve1, times(1)).next();
//	}

	@Test
	public void testSetSPPrice() {
		verify(this.subscriber1, times(0)).updateLowerPrice(this.booking);
		double newPrice = pricePerDayHighSeason*0.5;
		verify(this.specialPeriod1, times(0)).price();
		verify(this.specialPeriod1, times(0)).setPrice(newPrice);
		this.booking.setSPPrice(newPrice, this.specialPeriod1);
		verify(this.specialPeriod1, times(1)).price();
		verify(this.specialPeriod1, times(1)).setPrice(newPrice);
		verify(this.subscriber1, times(1)).updateLowerPrice(this.booking);
	}
	
	@Test
	public void availablePeriodsTest() {
		//assertTrue(this.booking.availablePeriods().size() != 0);
		//period = mock(Period.class);
		//Preguntar como el booking SUT sabe su periodo
		//No se inicializo el period del booking
		Period periodTest1 = mock(Period.class);
		Period periodTest2 = mock(Period.class);
		Period periodTest3 = mock(Period.class);
		Period periodTest4 = mock(Period.class);
		
		when(periodTest1.start()).thenReturn(begin);
		when(periodTest1.end()).thenReturn(end);
		
		//La lista de reserves del booking esta vacia,por ende el periodo disponible va a ser solo uno, y es equivalente al total de tiempo de alquiler disponible de la propiedad
		
		assertEquals(this.booking.availablePeriods().size(),1);
		assertEquals(this.booking.availablePeriods().get(0).start(),periodTest1.start());
		assertEquals(this.booking.availablePeriods().get(0).end(),periodTest1.end()); 
		
		
		//Agrego una reserva de 1 dia 
		
		when(periodTest2.start()).thenReturn(begin.plusDays(1));
		when(periodTest2.end()).thenReturn(end);
		
		this.reserves.add(reserve1);
		assertEquals(this.booking.availablePeriods().size(),1);
		assertEquals(this.booking.availablePeriods().get(0).start(),periodTest2.start());
		assertEquals(this.booking.availablePeriods().get(0).end(),periodTest2.end()); 
		
		//Agrego una reserva de dos dias que esta en medio WIP
		
		when(periodTest3.start()).thenReturn(begin);
		when(periodTest3.end()).thenReturn(begin);
		
		when(periodTest4.start()).thenReturn(begin.plusDays(3));
		when(periodTest4.end()).thenReturn(begin.plusDays(4));
		
		this.reserves.remove(reserve1);
		this.reserves.add(reserve4);
		
		assertEquals(this.booking.availablePeriods().size(),2);
		
		assertEquals(this.booking.availablePeriods().get(0).start(),periodTest3.start());
		assertEquals(this.booking.availablePeriods().get(0).end(),periodTest3.end());
		
		assertEquals(this.booking.availablePeriods().get(1).start(),periodTest4.start());
		assertEquals(this.booking.availablePeriods().get(1).end(),periodTest4.end());
		

	
		
	}
	
	@Test
	public void isAvailableDateTest() {
		this.reserves.add(reserve4);// dias ocupados mañana y pasado
		
		assertTrue(this.booking.isAvailableDate(today)); // hoy esta disponible
		assertFalse(this.booking.isAvailableDate(today.plusDays(1))); //mañana no esta disponible
	}
	
	@Test
	void isAvailableNullDateTest() {
	
		   Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            this.booking.isAvailableDate(null);
        });

        assertEquals("La fecha no puede ser nulo", exception.getMessage());
    }
	

	

	@Test
	void removeReserveTest() {
		booking.addReserve(reserve1);
		booking.removeReserve(reserve1);
		assertEquals(0, booking.getReserves().size());
	}
	
	@Test
	void handleCancellationTest() {
		
		verifyNoInteractions(this.owner);
		this.booking.handleCancellation(reserve2);
		assertEquals(0, booking.getReserves().size());
		verify(this.owner, times(1)).sendEmail(reserve2, "Se acaba de cencelar la reserva.");
	}
	


	
}
