package application;

public class Task {
	
	private int id;
	private int user;
	private int room;
	private int service;
	private String description;
	
	public Task(int id, int user, int room, int service, String description) {
		this.id = id;
		this.user = user;
		this.room = room;
		this.service = service;
		this.description = description;
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
