package application;

import java.sql.Date;
//test
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "room")
public class Rooms {
 @Id 
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 @Column(name = "ID")
	private int id;
 @Column(name = "NUMBER_OF_SEATS")
	private int numberOfSeats;
 @Column(name = "ROOM_NUMBER")
	private int roomNumber;
 @Column(name = "lvl")
	private String lvl;
 @ManyToOne
 @JoinColumn(name="reservation")
 private Reservation reservation;
 
	
	
	
	
	
	

	
	
}
