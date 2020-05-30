package Tests;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import application.Job;
import application.User;

public class TestUser {
	User user = new User();
	@Test
    void getName() {
        user.setName("krzysztof");
        assertEquals("krzysztof", user.getName());
    }
	@Test
    void getSurname() {
        user.setSurname("rogls");
        assertEquals("rogls", user.getSurname());
    }
	@Test
    void getLogin() {
        user.setLogin("kris");
        assertEquals("kris", user.getLogin());
    }
	@Test
    void getId() {
        user.setId(1);
        assertEquals(1, user.getId());
    }
	@Test
    void getPassword() {
        user.setPassword("haslo123");
        assertEquals("haslo123", user.getPassword());
    }
	@Test
    void getJob() {
		Job job=new Job();
        user.setJob(job);
        assertEquals(job, user.getJob());
    }
}
