package sa.booking;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import sa.booking.reserveStates.Timer;
import sa.cancellation.CostFree;
import sa.cancellation.ICancellationPolicy;
import sa.properties.Property;
import sa.subscriptions.INotifyTimerSubscriber;
import sa.users.Tenant;
import sa.observer.interfaces.INotifyObserver;



public class Booking implements INotifyConfiguration {

	private Period					period;
	private Property				property;
	private Pricer					pricer;

	private ICancellationPolicy		policy;

	private List<PaymentMethod>			paymentMethods;
	private List<Reserve>				reserves;
	private List<Reserve>				waitings;
	private Timer						timer;

	private List<INotifyObserver> 	obsCancel;
	private List<INotifyObserver> 	obsReserve;
	private List<INotifyObserver> 	obsPrice;


	public Booking(ICancellationPolicy policy, Property property, LocalDate begin, LocalDate end, List<PaymentMethod> paymentMethods,
			double pricePerDayWeekday, List<SpecialPeriod> periods, Timer timer) {
		// TODO Auto-generated constructor stub
		this.pricer		 		= new Pricer(pricePerDayWeekday, periods);
		this.policy				= policy;
		this.paymentMethods		= paymentMethods;
		this.setPeriod(new Period(begin, end));
		this.property			= property;
		this.obsPrice			= new ArrayList<INotifyObserver>();
		this.obsCancel  		= new ArrayList<INotifyObserver>();
		this.obsReserve  		= new ArrayList<INotifyObserver>();
		this.timer				= timer;
	}

	// Para hacer DOC de sus atributos.
	public Booking(CostFree policy, Pricer pricer, Property property,
			List<PaymentMethod> paymentMethods, double pricePerDayWeekday, List<SpecialPeriod> specialPeriods,
			Period period, List<Reserve> reserves, List<Reserve> waitings, Timer timer, List<INotifyObserver> obsCancel
			, List<INotifyObserver> obsReserve, List<INotifyObserver> obsPrice) {
		// TODO Auto-generated constructor stub
		this.pricer 		= pricer;
		this.pricer.setBasePrice(pricePerDayWeekday);
		specialPeriods.stream().forEach(p -> this.pricer.addSpecialPeriod(p));
		this.policy			= policy;
		this.paymentMethods	= paymentMethods;
		this.property		= property;
		this.period			= period;
		this.reserves		= reserves;
		this.waitings		= waitings;
		this.timer			= timer;
		this.obsCancel		= obsCancel;
		this.obsReserve		= obsReserve;
		this.obsPrice		= obsPrice;
	}

	public void newReserve(Tenant t, LocalDate start, LocalDate end) {
		// TODO Auto-generated method stub
		// El owner tiene que evaluar si aceptarla o no.
		Reserve newReserve = new Reserve(this, t, new Period(start, end));
		this.getConditionalReserves().add(newReserve);
		this.getProperty().getOwner().reserveRequested(newReserve); //cada vez que se crea una nueva reserva, tiene que llamar al owner para saber si lo aprueba o no?
	}

	public void newConditionalReserve(Tenant t, LocalDate start, LocalDate end) {
		// TODO Auto-generated method stub
		this.getConditionalReserves().add(new Reserve(this, t, new Period(start, end)));
	}

	public Period getPeriod() {
		return this.period;
	}

	private void setPeriod(Period p) {
		this.period = p;
	}

	public List<Period> availablePeriods(){
		List<Period> avaiablePeriods = new ArrayList<Period>();
		
		LocalDate startDate = this.getPeriod().start();
		
		this.reserves.sort((r1,r2)-> r1.getCheckIn().compareTo(r2.getCheckIn())); //Ordeno la lista de reservas de forma cronologica
		
		for(Reserve reserve: reserves) {
			if(startDate.isBefore(reserve.getCheckIn())) {  //SI LA FECHA DE INICIO DEL ALQUILER ESTA ANTES QUE LA FECHA DE CHECKIN DEL PRIMER ALQUILER REGISTRADO
				avaiablePeriods.add(new Period(startDate, reserve.getCheckIn().minusDays(1))); //SE CREA UN PERIODO NUEVO CUYO INICIO ES LA FECHA DE INICIO Y EL FIN ES UN DIA ANTES QUE EMPIECE LA PRIMER RESERVA DE LA LISTA. A ESTE PERIODO LO AGREGO A LA LISTA DE PERIODOS DISPONIBLES
			}
			startDate = reserve.getCheckOut().plusDays(1);	//HAGO QUE LA FECHA DE INICIO SEA UN DIA DESPUES DEL CHECK OUT DE LA RESERVA ACTUAL	
		}
		
		if(startDate.isBefore(this.getPeriod().end()) || startDate.equals(this.getPeriod().end())) { //VERIFICA QUE DESPUES DE REGISTRAR TODAS LOS ALQUILERES, MI FECHA DE INICIO ESTE ANTES QUE LA FECHA FINAL DEL ALQUILER O QUE SEA IGUAL. EN EL CASO DE QUE SI, SE AGREGA UN PERIODO CUYA FECHA DE INICIO ES LA FECHA INICIAL Y LA FECHA FINAL ES LA FECHA FINAL DEL ALQUILER
			avaiablePeriods.add(new Period(startDate,this.getPeriod().end()));
		}
		
		return avaiablePeriods;
	}
	
	public boolean isAvailableDate(LocalDate checkInDate) {
		if(checkInDate == null) {  //SI LA FECHA QUE ME PASARON ES NULA, TIRO ERROR
			throw new IllegalArgumentException("La fecha no puede ser nulo");	
		}
		
		List<Period> periodsAvailables = this.availablePeriods();
		
		for(Period p : periodsAvailables) {
			if(p.belongs(checkInDate)) {
				return true;
			}
		}
		return false;
	}

	public Property getProperty() {
		// TODO Auto-generated method stub
		return this.property;
	}

	public List<PaymentMethod> getPaymentMethods() {
		// TODO Auto-generated method stub
		return this.paymentMethods;
	}

	public ICancellationPolicy getPolicy() {
		// TODO Auto-generated method stub
		return this.policy;
	}

	public void applyPolicy(Reserve r, LocalDate cancellationDate) {
		// TODO Auto-generated method stub
		this.policy.activate(r,cancellationDate);
	}

	public void setBasePrice(double newPrice) {
		// TODO Auto-generated method stub
		double currBP = this.pricer.getBasePrice();
		this.pricer.setBasePrice(newPrice);
		if (currBP > newPrice) {
			// notificá que el booking bajó de precio base
			this.notifySubscribersPrice();
		} 		
	}

	public void setSPPrice(double newPrice, SpecialPeriod sp) {
		// TODO Auto-generated method stub
		double currSPP = sp.price();
		sp.setPrice(newPrice);
		if (currSPP > newPrice) {
			// notificá que el booking bajó de precio
			this.notifySubscribersPrice();
		}
	}
	
	public double price(LocalDate date) {
		// TODO Auto-generated method stub
		return this.pricer.price(date);
	}

	public double priceBetween(LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return this.pricer.priceBetween(startDate, endDate);
	}

	public List<Reserve> getReserves() {
		// TODO Auto-generated method stub
		return this.reserves;
	}

	public List<Reserve> getConditionalReserves() {
		// TODO Auto-generated method stub
		return this.waitings;
	}

	void addReserve(Reserve reserve) {
		// TODO Auto-generated method stub
		this.getReserves().add(reserve);
		this.notifySubscribersReserve(reserve);
	}

	public Timer getTimer() {
		// TODO Auto-generated method stub
		return this.timer;
	}

	//cambie temporalmente de private a public porque no lo podia testear
	void triggerNextRequest(LocalDate start, LocalDate end) {
//		for (LocalDate currDate = start; !currDate.equals(end.plusDays(1)); currDate.plusDays(1)) {
//			final LocalDate date = currDate; // Nos tira error en el lambda porque necesita que sea final
//			Optional<Reserve> wr = this.getConditionalReserves().stream()
//									.filter(w -> date.equals(w.getCheckIn()) && end.isBefore(end.plusDays(1)))
//									.findFirst();
//			// Si está en waiting pasa a ser una reserva formal
//			if (wr.isPresent()) {
//				Reserve next_r = wr.get();
//				this.getProperty().getOwner().reserveRequested(next_r);
//			}
//		}
		
		Period aux = new Period(start,end);  //armo un periodo auxiliar con las fechas que me dieron para poder usar el metodo belongs
		List<Reserve> conditionalReserves = this.getConditionalReserves(); // lista de reservas condicionales. Se podria ahorrar esta linea de codigo poniendo el metodo directamente en el for
		
		for(Reserve r : conditionalReserves) { //for iterando en cada elemento de la lista de condicionales
			if(aux.belongs(r.getCheckIn()) && aux.belongs(r.getCheckOut())) { //verifica que esa reserva condicional este dentro del periodo que se cancelo (aux)
				this.getProperty().getOwner().reserveRequested(r); //Notifico al owner para que verifique esta reserva
				break; //rompo el ciclo 
			}
		}
	}

	public Pricer getPricer() {
		// TODO Auto-generated method stub
		return this.pricer;
	}

	void removeReserve(Reserve reserve) {
		// TODO Auto-generated method stub
		this.reserves.remove(reserve);
	}

	public void removeWaiting(Reserve reserve) {
		// TODO Auto-generated method stub
		this.getConditionalReserves().remove(reserve);
	}

	public void handleCancellation(Reserve reserve) {
		// TODO Auto-generated method stub
		this.removeReserve(reserve);
		this.notifySubscribersCancelled(reserve);
		// TODO: qué se hace con la reserva cancelada 'r' ?
		this.applyPolicy(reserve, LocalDate.now());
		this.getProperty().getOwner().sendEmail(reserve, "Se acaba de cencelar la reserva.");
		this.triggerNextRequest(LocalDate.now(), reserve.getCheckOut());
	}

	@Override
	public void registerPriceObserver(INotifyObserver o) {
		// TODO Auto-generated method stub
		this.obsPrice.add(o);
	}

	@Override
	public void registerCancelObserver(INotifyObserver o) {
		// TODO Auto-generated method stub
		this.obsCancel.add(o);
	}

	@Override
	public void registerReserveObserver(INotifyObserver o) {
		// TODO Auto-generated method stub
		this.obsReserve.add(o);
	}

	@Override
	public void notifySubscribersReserve(Reserve r) {
		// TODO Auto-generated method stub
		this.obsReserve.stream().forEach(o -> o.updateNewReserve(r));
	}

	@Override
	public void notifySubscribersPrice() {
		// TODO Auto-generated method stub
		this.obsPrice.stream().forEach(o -> o.updateLowerPrice(this));
	}

	@Override
	public void notifySubscribersCancelled(Reserve r) {
		// TODO Auto-generated method stub
		// Tanto el AccomodationSite como User pueden implementar la interfaz de observador y por lo tanto
		// AccomodationSite, Tenant y Owner pueden registrarse a las cancelaciones con facilidad.
		this.obsCancel.stream().forEach(o -> o.updateCancellation(r));
	}

	public void unRegisterPriceObserver(INotifyObserver o) {
		// TODO Auto-generated method stub
		this.obsPrice.remove(o);
	}

	public void unRegisterCancelObserver(INotifyObserver o) {
		// TODO Auto-generated method stub
		this.obsCancel.remove(o);
	}

	public void unRegisterReserveObserver(INotifyObserver o) {
		// TODO Auto-generated method stub
		this.obsReserve.remove(o);
	}
}
