package application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "task")
public class Task {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "user_id")
	private int user;
	
	@Column(name = "room_number")
	private int room;
	
	@Column(name = "service_id")
	private int service;
	
	@Column(name = "description")
	private String description;
	
	public Task(int id, int user, int room, int service, String description) {
		this.id = id;
		this.user = user;
		this.room = room;
		this.service = service;
		this.description = description;
	}
	


	public Task() {
		this.id = 0;
		this.user = 0;
		this.room = 0;
		this.service = 0;
		this.description = "";
	}



	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getUser() {
		return user;
	}
	public void setUser(int user) {
		this.user = user;
	}
	public int getRoom() {
		return room;
	}
	public void setRoom(int room) {
		this.room = room;
	}
	public int getService() {
		return service;
	}
	public void setService(int service) {
		this.service = service;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	
}
