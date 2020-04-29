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
@Table(name = "services")
public class Services {

	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")
	private int id;
	
	@Column(name = "name")
    private String name;
	
	@Column(name = "price")
	private float price;
	
	@OneToMany
    private List<Task> task;
	

 

}
