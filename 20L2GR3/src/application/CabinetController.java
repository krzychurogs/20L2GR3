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
	 	Query query = session.createQuery("from Reservation r WHERE r.room.id=:roomid and r.dates=:todaydate");
	 	query.setParameter("roomid", roomId);
	 	query.setParameter("todaydate",calobj.getTime() );
	 	List<Reservation>reservations =(List<Reservation>) query.list();	
	 	current=reservations.get(0);
		session.getTransaction().commit();
		
		session.beginTransaction();	
	 	Bill bill=session.get(Bill.class, current.getId());
	 	if(waterBox.isSelected()) {
	 		
	 	}
	 	if(colaBox.isSelected()) {
	 	Services service=session.get(Services.class, 1);
		bill.addServices(service);
	 	}
	 	if(beerBox.isSelected()) {
	 	Services service=session.get(Services.class, 2);
		bill.addServices(service);
	 	}
	 	if(wineBox.isSelected()) {
			
	 	}
	 	if(champagneBox.isSelected()) {
			
	 	}
	 	if(fantaBox.isSelected()) {
			
	 	}
	 	if(teaBox.isSelected()) {
			
	 	}
	 	if(whiskeyBox.isSelected()) {
			
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
