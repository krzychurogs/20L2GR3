package application;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
//test
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
@Entity
@Table(name = "room")
public class Rooms {
	
	@Id @GeneratedValue
 	@Column(name = "ID")
	private int id;
 	
	@Column(name = "NUMBER_OF_SEATS")
	private int numberOfSeats;
 	
 	@Column(name = "ROOM_NUMBER")
	private int roomNumber;
 	
 	@Column(name = "lvl")
	private String lvl;
 	
 	@OneToMany(cascade=CascadeType.ALL)
 	@JoinColumn(name = "ID")
    private List<Task> tasks;
 	
 	@OneToMany(cascade=CascadeType.ALL)
 	@JoinColumn(name="reservation")
 	 private List<Reservation> reservation;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getNumberOfSeats() {
		return numberOfSeats;
	}

	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}

	public int getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}

	public String getLvl() {
		return lvl;
	}

	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public List<Reservation> getReservation() {
		return reservation;
	}
	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}


	public Rooms(int id, int numberOfSeats, int roomNumber, String lvl) {
		super();
		this.id = id;
		this.numberOfSeats = numberOfSeats;
		this.roomNumber = roomNumber;
		this.lvl = lvl;
	}

	public Rooms() {
		super();
	}

	@Override
	public String toString() {
		return  roomNumber+"" ;
	}	

	
}

	

