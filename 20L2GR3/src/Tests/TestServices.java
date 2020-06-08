package Tests;

import static org.junit.Assert.assertEquals;

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

public class TestServices {
	Services services = new Services();
	@Test
    void getName() {
		services.setName("pranie");
        assertEquals("pranie",services.getName());
	}
	
	@Test
    void getID() {
		services.setId(2);
        assertEquals(2,services.getId());
	}
	
	@Test
    void getTask() {
		List<Task> task = null;
        services.setTask(task);
        assertEquals(task,services.getTask());
	}
	@Test
    void getBill() {
		List<Bill> bill = null;
        services.setBill(bill);
        assertEquals(bill,services.getBill());
	}
	
	
}