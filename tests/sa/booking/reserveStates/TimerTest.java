package sa.booking.reserveStates;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import sa.booking.Booking;
import sa.booking.Reserve;


class TimerTest {

	private Timer timer;
	
	private HashMap<LocalDate, Set<Reserve>> rsubscribers;
	private ReserveBooked    suscriber1;
	private ReserveOccupied  suscriber2;
	private ReserveBooked    suscriber3;
	
	private Booking	booking1;
	private Booking	booking2;
	
	private Reserve	reserve1;
	private Reserve	reserve2;
	
	private LocalDate date1;
	private LocalDate date2;
	
	@BeforeEach
	void setUp() throws Exception {
		// DOC
		//this.suscriber1	= mock(BookingSubscriber.class); // El timer recibe como suscriptores a los estados, no a un bookingSubscriber
		//this.suscriber2	= mock(BookingSubscriber.class);  // El timer recibe como suscriptores a los estados, no a un bookingSubscriber
		this.suscriber1 = mock(ReserveBooked.class);
		this.suscriber2 = mock(ReserveOccupied.class);
		this.suscriber3 = mock(ReserveBooked.class);
		this.booking1 	= mock(Booking.class);
		this.booking2 	= mock(Booking.class);
		this.reserve1 	= mock(Reserve.class);
		this.reserve2 	= mock(Reserve.class);
		this.date1		= LocalDate.now();
		this.date2		= this.date1.plusDays(2);
		
		// SUT
		this.timer = new Timer(3);  //Modelamos el pasaje por X milisegundos
		
	}

	@Test
	void testConstructor() {
		assertNotNull(this.timer);
	}

	@Test
	void testRegister() {
		assertEquals(0, this.timer.getSubscribers().size());
		this.timer.register(suscriber1, date1);
		assertEquals(1, this.timer.getSubscribers().size());
	}

	@Test
	void testUnRegister() {
		assertEquals(0, this.timer.getSubscribers().size());
		this.timer.register(suscriber1, date1);
		assertEquals(1, this.timer.getSubscribers().size());
		this.timer.register(suscriber3, date1);
		assertEquals(2, this.timer.getSubscribers().size());
		this.timer.unregister(suscriber1, date1); 
		this.timer.unregister(suscriber3, date1);
		assertEquals(0, this.timer.getSubscribers().size()); // No tienen override de equals() y hashCode()
	}

	@Test
	void testNotify() {
		assertEquals(0, this.timer.getSubscribers().size());
		this.timer.register(suscriber1, date1); 
		assertEquals(1, this.timer.getSubscribers().size());
		this.timer.notify(date1); 
		//verify(this.booking1, times(1)).update(reserve1, date1); quedo viejo
		verify(suscriber1,times(1)).update();
		
		
	}

}
