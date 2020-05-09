package application;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bills")
public class Bill {
			
	@Id 
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "ID")	
	int id;
	
	@OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID")
    private Reservation reservation;
	
	@ManyToMany(
	        cascade = {CascadeType.MERGE, CascadeType.PERSIST}
	    )
	    @JoinTable(
	        name = "bills_services",
	        joinColumns = @JoinColumn(name = "bill_id"),
	        inverseJoinColumns = @JoinColumn(name = "service_id")
	    )
 	private List<Services> services;
	
	
	 public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}

	public void addServices(Services services) {
	        this.services.add(services);
	        services.getBill().add(this);
	    }

	    public void removeServices(Services services) {
	        this.services.remove(services);
	        services.getBill().remove(this);
	    }
	   public float totalbill()
	    {
	    	float price=0;
	    	for(int i=0;i<reservation.getBill().getServices().size();i++)
	    	{
	    		 price+=reservation.getBill().getServices().get(i).getPrice();
	    	}
	    	return price;
	    } 
	   @Override
	    public String toString() {
	    return this.totalbill()+"";
	    }
}
