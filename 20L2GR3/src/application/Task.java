package application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "task")
public class Task {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne
    private User user;
	
	@ManyToOne
    private Rooms room;
	
	@Column(name = "service_id")
	private int service;
	
	@Column(name = "description")
	private String description;
	
	@Column(name="status")
	private boolean status;

	

	public Task(int id, User user, Rooms room, int service, String description, boolean status) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.service = service;
		this.description = description;
		this.status = status;
	}
	

	public Task() {
		super();
		this.id =0;
		this.user = null;
		this.room = null;
		this.service = 0;
		this.description = "";
		this.status = false;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Rooms getRoom() {
		return room;
	}

	public void setRoom(Rooms room) {
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


	public boolean isStatus() {
		return status;
	}


	public void setStatus(boolean status) {
		this.status = status;
	}
	
	
	
	
}
