package application;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name ="reservation")
public class Reservation {
	
@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name="ID")
private int id;

@ManyToOne
@JoinColumn(name="reservation")
private Rooms room;
@Column(name = "guest_id")
	private int guest_id;
@Column(name = "date_reservation")
	private Date dates;
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}
public Rooms getRoom() {
	return room;
}
public void setRoom(Rooms room) {
	this.room = room;
}
public int getGuest_id() {
	return guest_id;
}
public void setGuest_id(int guest_id) {
	this.guest_id = guest_id;
}
public Date getDates() {
	return dates;
}
public void setDates(Date dates) {
	this.dates = dates;
}








}

