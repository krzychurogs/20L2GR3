package Tests;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import application.Guest;
import application.Job;
import application.Reservation;
import application.Rooms;
import application.Services;
import application.Task;
import application.User;

public class TestGuest {
	Guest guest = new Guest();
	@Test
    void getName() {
        guest.setName("marcin");
        assertEquals("marcin",guest.getName());
	}
	@Test
    void getSurname() {
        guest.setSurname("kowalski");
        assertEquals("kowalski",guest.getSurname());
	}
	@Test
    void getID() {
        guest.setId(2);
        assertEquals(2,guest.getId());
	}
	@Test
    void getReservation() {
		List<Reservation> reservation = null;
        guest.setReservation(reservation);
        assertEquals(reservation,guest.getReservation());
	}
	
	
}