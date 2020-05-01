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
	
	 public void addServices(Services services) {
	        this.services.add(services);
	        services.getBill().add(this);
	    }

	    public void removeServices(Services services) {
	        this.services.remove(services);
	        services.getBill().remove(this);
	    }
}
