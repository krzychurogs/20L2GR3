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

@OneToMany(cascade=CascadeType.ALL)
@JoinColumn(name="reservation")
private Set<Rooms> rooms;
@Column(name = "guest_id")
	private int guest_id;
@Column(name = "date_reservation")
	private java.util.Date date;





}

