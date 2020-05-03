package application;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.prefs.Preferences;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.sun.jmx.snmp.tasks.TaskServer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CookCleanerController {
	@FXML
	private Button confirmButton;
	
	@FXML
	private TableView<Task> taskTableView;
	
	@FXML
	private TableColumn<Task,Integer> idColumn;
	
	@FXML
	private TableColumn<Task,String> userColumn;
	
	@FXML
	private TableColumn <Task,String>roomColumn;
	
	@FXML
	private TableColumn<Task,Integer> serviceColumn;
	
	@FXML
	private TableColumn<Task,String> descriptionColumn;
	
	public ObservableList <Task> list;
	
	private String loggedUserName;
	private int loggedId;
	private int loggedJobId;
	
	public CookCleanerController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	Preferences userPreferences = Preferences.userRoot();
    	loggedUserName = userPreferences.get("loggedUsername","");
    	loggedId=Integer.parseInt(userPreferences.get("loggedId","o"));
    	loggedJobId=Integer.parseInt(userPreferences.get("loggedJobId","o"));
    	populateTable();
    	
     	
    }
    
    private void populateTable() {
    	System.out.println("id"+loggedId);
		list=FXCollections.observableArrayList();  	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
    	userColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("user"));
    	roomColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("room"));
    	serviceColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("service"));
    	descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));     
    	SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();	
    	Session session=sessionFactory.openSession();		 	
    	session.beginTransaction();			   
    	Query query = session.createQuery("from Task where user.id=:userid");	
    	query.setParameter("userid",loggedId);
    	List<Task>tasks = query.list();				  
    	session.getTransaction().commit();		    
    	for(int i=0;i<tasks.size();i++) {	
    		if(tasks.get(i).isStatus())
    		list.add(tasks.get(i));
    		}
    	taskTableView.setItems(list);
    	session.close();
    }
    
    @FXML
    void wyloguj(ActionEvent event) throws Exception {	
	 Parent application = FXMLLoader.load(getClass().getResource("Uzytkownik.fxml"));
     Scene applicationScene = new Scene(application);
     Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
     applicationScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     window.setScene(applicationScene);
     window.show();
    	
    } 
    
    @FXML
    void confirmTask(ActionEvent event) throws Exception {
    	if(loggedJobId==2) {
    		confirmCookTask();
    	}
    	else {
    		confirmCleanerTask();
    	}
    }
    private void confirmCleanerTask() throws IOException {
    	if(taskTableView.getSelectionModel().getSelectedItems().isEmpty()==false) {
    		 FXMLLoader fxmlLoader = new FXMLLoader();
    	        fxmlLoader.setLocation(getClass().getResource("cabinet.fxml"));
    	        Parent root = fxmlLoader.load();
    	        CabinetController controller = fxmlLoader.getController();
    	        controller.setTaskId(taskTableView.getSelectionModel().getSelectedItems().get(0).getId());
    	        Scene scene = new Scene(root, 600, 400);
    	        Stage stage = new Stage();
    	        stage.setTitle("New Window");
    	        stage.setScene(scene);
    	        stage.show();
    	}
    }
    private void confirmCookTask() {
    	if(taskTableView.getSelectionModel().getSelectedItems().isEmpty()==false) {
    		Reservation current;
			System.out.println(taskTableView.getSelectionModel().getSelectedItems().get(0).getId()+"lalala");
			SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();	
			Session session=sessionFactory.openSession();		 	
			session.beginTransaction();		
			Task task=session.get(Task.class,taskTableView.getSelectionModel().getSelectedItems().get(0).getId());
			task.setStatus(false);
			session.getTransaction().commit();
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
		 	bill.addServices(task.getService());
		 	session.getTransaction().commit();
			session.close();
		}
		populateTable();
    }
    @FXML
    private void printOutput() 
    {
        
    }
	
}
