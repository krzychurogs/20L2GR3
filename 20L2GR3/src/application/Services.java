package application;
import java.sql.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Model reprezentujacy serwisy, polaczony relacja z rachunkami,zadaniami
 */
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
	
	@OneToMany(cascade=CascadeType.ALL)
 	@JoinColumn(name="ID")
    private List<Task> task;
	
	@ManyToMany(mappedBy = "services")
 	private List<Bill> bill;

	


	

	public Services(int id, String name, float price, List<Task> task, List<Bill> bill) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.task = task;
		this.bill = bill;
	}

	public Services(String name, Float price) {
		this.price = price;
		this.name = name;
		
	}

	public Services() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return name;
	}

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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public List<Task> getTask() {
		return task;
	}

	public void setTask(List<Task> task) {
		this.task = task;
	}

	public List<Bill> getBill() {
		return bill;
	}

	public void setBill(List<Bill> bill) {
		this.bill = bill;
	}
	

	

}