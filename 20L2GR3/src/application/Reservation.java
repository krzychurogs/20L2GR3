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
@ManyToOne
@JoinColumn(name="guest")
	private Guest guest;
@Column(name = "date_reservation")
	private Date dates;
@Column(name = "enddate_reservation")
private Date endDate;
public Date getEndDate() {
	return endDate;
}
public void setEndDate(Date endDate) {
	this.endDate = endDate;
}
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

public Guest getGuest() {
	return guest;
}
public void setGuest(Guest guest) {
	this.guest = guest;
}
public Date getDates() {
	return dates;
}
public void setDates(Date dates) {
	this.dates = dates;
}








}

