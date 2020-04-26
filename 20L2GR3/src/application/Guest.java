package application;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
//test
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "guest")
public class Guest {

	 @Id 
	 @GeneratedValue(strategy = GenerationType.IDENTITY)
	 @Column(name = "ID")
		private int id;
	 @Column(name = "Name")
	    private String name;
	 @Column(name = "Surname")
	    private String surname;
   

    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="guest")
    private List<Reservation> reservation;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public List<Reservation> getReservation() {
		return reservation;
	}


	public void setReservation(List<Reservation> reservation) {
		this.reservation = reservation;
	}
   
 

}
