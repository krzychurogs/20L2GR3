package application;

import java.sql.Date;
//test
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "POKOJ")
public class Rooms {
	public Rooms(int id, int number_of_seats, int room_number, String lvl) {
		super();
		this.id = id;
		this.number_of_seats = number_of_seats;
		this.room_number = room_number;
		this.lvl = lvl;
	}
 @Id @GeneratedValue
@Column(name = "id_pokoju")
	private int id;
 @Column(name = "liczba_miejsc")
	private int number_of_seats;
 @Column(name = "nr")
	private int room_number;
 @Column(name = "lvl")
	private String lvl;
 
	private java.util.Date data;
	
	
	
	public Rooms(int id, int number_of_seats, int room_number, String lvl, java.util.Date data) {
		super();
		this.id = id;
		this.number_of_seats = number_of_seats;
		this.room_number = room_number;
		this.lvl = lvl;
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumber_of_seats() {
		return number_of_seats;
	}
	public void setNumber_of_seats(int number_of_seats) {
		this.number_of_seats = number_of_seats;
	}
	public int getRoom_number() {
		return room_number;
	}
	public void setRoom_number(int room_number) {
		this.room_number = room_number;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public java.util.Date getData() {
		return data;
	}
	public void setData(Date data) {
		this.data = data;
	}
	

	
	
}
