package application;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.prefs.Preferences;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
/**
 * Kontroler obslugujacy Kucharza/Sprzatacza
 */
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
	    /**
	     * Metoda wyswietlajaca zadania dla danego pracownika
	     * {@value} taskTableView - tabela z zadaniami okreslenego pracownika
	     */ 	 
     void populateTable() {
    	 taskTableView.getItems().clear();
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
    
    }
     /**
	   * Metoda sluzaca do wylogywania uzytkownika
	   * @param event zdarzenie wywolane na przycisku
	   */
    @FXML
    void wyloguj(ActionEvent event) throws Exception {	
   	 Parent application = FXMLLoader.load(getClass().getResource("Login.fxml"));
     Scene applicationScene = new Scene(application);
     Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
     applicationScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
     window.setScene(applicationScene);
     window.show();
    	
    } 
    /**
	   * Metoda sluzaca do zatwierdzenia zadania
	   * @param event zdarzenie wywolane na przycisku
	   */
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
    	        stage.showAndWait();
    	        populateTable();
    	}
    }
    	/**
	   * Metoda sluzaca do zatwierdzenia zadania przez Kucgaraza
	   * {@value} task obiekt klasy Task
	   * {@value} current obiekt klasy Reservation
	   * {@value} bill obiekt klasy Bill
	   */
    private void confirmCookTask() {
    	if(taskTableView.getSelectionModel().getSelectedItems().isEmpty()==false) {
    		Reservation current;
			System.out.println(taskTableView.getSelectionModel().getSelectedItems().get(0).getId()+"lalala");
			SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();	
			Session session=sessionFactory.openSession();		 	
			session.beginTransaction();		
			Task task=session.get(Task.class,taskTableView.getSelectionModel().getSelectedItems().get(0).getId());
			task.setStatus(false);
			int roomId =task.getRoom().getId(); 
		 	Calendar calobj = Calendar.getInstance();
		 	Query query = session.createQuery("from Reservation r WHERE r.room.id=:roomid and :todaydate BETWEEN r.dates AND r.endDate ");
		 	query.setParameter("roomid", roomId);
		 	query.setParameter("todaydate",calobj.getTime() );
		 	List<Reservation>reservations =(List<Reservation>) query.list();	
		 	current=reservations.get(0);

		 	Bill bill=session.get(Bill.class, current.getId());
		 	bill.addServices(task.getService());
		 	session.getTransaction().commit();
		 	 Alert a = new Alert(AlertType.NONE);
			 a.setAlertType(AlertType.INFORMATION); 
			 a.setContentText("Zadanie wykonane");
			 a.getDialogPane().setPrefSize(200, 100);
	         a.show(); 
		
		}
		populateTable();
    }
    @FXML
    private void printOutput() 
    {
        
    }
	
}