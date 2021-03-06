package Tests;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import application.Bill;
import application.Guest;
import application.Job;
import application.Reservation;
import application.Rooms;
import application.Services;
import application.Task;
import application.User;

public class TestBill {
	Bill bill = new Bill();
	@Test
    void getID() {
        bill.setId(2);
        assertEquals(2,bill.getId());
	}
	
	
	@Test
    void getReservation() {
		Reservation reservation=new Reservation();
        bill.setReservation(reservation);
        assertEquals(reservation,bill.getReservation());
	}
	@Test
    void getServices() {
		List<Services> services = null;
        bill.setServices(services);
        assertEquals(services,bill.getServices());
	}
	@Test
    void getProfit() {
		Services service= new Services("pizza",14.0F);
		List<Services> list = new ArrayList<Services>();
		list.add(service);
		Reservation reservation = new Reservation(bill);
	
		bill.setReservation(reservation);
		bill.setServices(list);
		assertEquals(14, 14, 1e-15);
	}
	
}