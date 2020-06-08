package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import application.Job;
import application.Rooms;
import application.Services;
import application.Task;
import application.User;

public class TestTask {
	Task task = new Task();
	@Test
    void getDescription() {
        task.setDescription("tuff");
        assertEquals("tuff", task.getDescription());
    }
	@Test
    void getId() {
		task.setId(2);
        assertEquals(2, task.getId());
    }
	
	@Test
    void getRoom() {
		Rooms room=new Rooms();
		task.setRoom(room);
        assertEquals(room, task.getRoom());
    }
	@Test
    void getStatus() {
		Boolean status=true;
		task.setStatus(status);	
        assertEquals(status,task.isStatus());
    }
	@Test
    void getUser() {
		User user=new User();
		task.setUser(user);
        assertEquals(user, task.getUser());
    }
	@Test
    void getServices() {
		Services service=new Services();
		task.setService(service);
        assertEquals(service, task.getService());
    }
	
	
}