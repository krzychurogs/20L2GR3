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

public class TestJob {
	Job job = new Job();
	@Test
    void getName() {
        job.setName("kucharz");
        assertEquals("kucharz",job.getName());
	}
	@Test
    void getId() {
		job.setId(2);
        assertEquals(2,job.getId());
	}
	@Test
    void getReservation() {
		List<User> user = null;
        job.setUser(user);
        assertEquals(user,job.getUser());
	}
}