package application;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.prefs.Preferences;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
/**
 * Kontroler obslugujacy Barek
 */
public class CabinetController {
	
	@FXML
	private CheckBox waterBox;
	
	@FXML
	private CheckBox colaBox;
	
	@FXML
	private CheckBox beerBox;
	
	@FXML
	private CheckBox wineBox;
	
	@FXML
	private CheckBox champagneBox;
	
	@FXML
	private CheckBox fantaBox;
	
	@FXML
	private CheckBox teaBox;
	
	@FXML
	private CheckBox whiskeyBox;
	
	@FXML
	private Button confirmButton;
	
	private String loggedUserName;
	private int loggedId;
	private int loggedJobId;
	private int taskId;
	@FXML
    private void initialize() 
    {
    	Preferences userPreferences = Preferences.userRoot();
    	loggedUserName = userPreferences.get("loggedUsername","");
    	loggedId=Integer.parseInt(userPreferences.get("loggedId","o"));
    	loggedJobId=Integer.parseInt(userPreferences.get("loggedJobId","o"));
    }
	
	 void setTaskId(int taskid) {
		this.taskId=taskid;
	}
	 
	 
	 /**
	  * metoda potwierdzajca zadanie
	  * {@value} task - obiekt klasy Task
	  * {@value} bill - obiekt klasy Bill
	  * {@value} service - obiekt klasy Services
	  * {@value} reservations - lista obiektow klasy Rezerwacji
	  * @param event zdarzenie wywolane przez przycisk
	  */
	@FXML
	void confirmTask(ActionEvent event)  throws Exception {
		Reservation current;
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();
	 	Session session=sessionFactory.openSession();
	 	Task task=session.get(Task.class,taskId);
	 	
	 	session.beginTransaction();
		session.getTransaction().commit();
		int roomId =task.getRoom().getId(); 
	 	Calendar calobj = Calendar.getInstance();
	 	session.beginTransaction();	
	 	Query query = session.createQuery("from Reservation r WHERE r.room.id=:roomid and :todaydate BETWEEN r.dates AND r.endDate");
	 	query.setParameter("roomid", roomId);
	 	query.setParameter("todaydate",calobj.getTime() );
	 	List<Reservation>reservations =(List<Reservation>) query.list();	
	 	current=reservations.get(0);
		session.getTransaction().commit();
		
		session.beginTransaction();	
	 	Bill bill=session.get(Bill.class, current.getId());
	 	
	 	if(colaBox.isSelected()) {
	 	Services service=session.get(Services.class, 1);
		bill.addServices(service);
	 	}
	 	if(beerBox.isSelected()) {
	 	Services service=session.get(Services.class, 2);
		bill.addServices(service);
	 	}
	 	if(wineBox.isSelected()) {
	 		Services service=session.get(Services.class, 3);
			bill.addServices(service);
	 	}
	 	if(champagneBox.isSelected()) {
	 		Services service=session.get(Services.class, 4);
			bill.addServices(service);
	 	}
	 	if(fantaBox.isSelected()) {
	 		Services service=session.get(Services.class, 5);
			bill.addServices(service);
	 	}
	 	if(teaBox.isSelected()) {
	 		Services service=session.get(Services.class, 6);
			bill.addServices(service);
	 	}
	 	if(whiskeyBox.isSelected()) {
	 		Services service=session.get(Services.class, 7);
			bill.addServices(service);
	 	}
	 	if(waterBox.isSelected()) {
	 		Services service=session.get(Services.class, 8);
			bill.addServices(service);
	 	}
	 	task.setStatus(false);
	 	bill.addServices(task.getService());
	 	session.save(task);
	 	session.save(bill);
		session.getTransaction().commit();
		session.close();
		Stage stage = (Stage) confirmButton.getScene().getWindow();
	    stage.close();
		
	}

}
