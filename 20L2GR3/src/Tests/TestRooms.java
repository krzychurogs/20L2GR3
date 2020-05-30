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

public class TestRooms {
	Rooms room = new Rooms();
	@Test
    void getID() {
        room.setId(2);
        assertEquals(2,room.getId());
	}
	@Test
    void getSurname() {
        room.setLvl("medium");
        assertEquals("medium",room.getLvl());
	}
	@Test
    void getNumberOfSeats() {
        room.setNumberOfSeats(2);
        assertEquals(2,room.getNumberOfSeats());
	}
	
	@Test
    void getReservation() {
		List<Reservation> reservation = null;
        room.setReservation(reservation);
        assertEquals(reservation,room.getReservation());
	}
	@Test
    void getRoomNumber() {
		
        room.setRoomNumber(312);
        assertEquals(312,room.getRoomNumber());
	}
	
	
}