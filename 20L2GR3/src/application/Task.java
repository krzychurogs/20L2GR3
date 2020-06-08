package application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "task")
/**
 * Model reprezentujacy Zadanie, polaczony relacja z Pokojami i serwisami
 */
public class Task {
	
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@ManyToOne
    private User user;
	
	@ManyToOne(fetch = FetchType.LAZY) 
    private Rooms room;
	
	@ManyToOne(fetch = FetchType.LAZY)
    private Services service;
	
	@Column(name = "description")
	private String description;
	
	@Column(name="status")
	private boolean status;

	


	

	public Task(int id, User user, Rooms room, Services service, String description, boolean status) {
		super();
		this.id = id;
		this.user = user;
		this.room = room;
		this.service = service;
		this.description = description;
		this.status = status;
	}


	public Services getService() {
		return service;
	}


	public void setService(Services service) {
		this.service = service;
	}


	public Task() {
		super();
		this.id =0;
		this.user = null;
		this.room = null;
		this.service = null;
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