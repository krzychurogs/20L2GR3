package application;

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
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CookCleanerController {
	@FXML
	private Button confirmButton;
	
	@FXML
	private TableView<Task> taskTableView;
	
	@FXML
	private TableColumn<Task,Integer> idColumn;
	
	@FXML
	private TableColumn<Task,Integer> userColumn;
	
	@FXML
	private TableColumn <Task,Integer>roomColumn;
	
	@FXML
	private TableColumn<Task,Integer> serviceColumn;
	
	@FXML
	private TableColumn<Task,String> descriptionColumn;
	
	public ObservableList <Task> list;
	
	private String loggedUserName;
	private String loggedId;
	
	
	public CookCleanerController() 
    {
    }
     
    @FXML
    private void initialize() 
    {
    	Preferences userPreferences = Preferences.userRoot();
    	loggedUserName = userPreferences.get("loggedUsername","");
    	loggedId=userPreferences.get("loggedId","o");
    	SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(Task.class).buildSessionFactory();
		Session session=factory.openSession();
		list=FXCollections.observableArrayList();
		try {
		session.beginTransaction();	
		Query query = session.createQuery("from Task t WHERE t.user=:userid");
		query.setParameter("userid",Integer.parseInt(loggedId));
		List<Task>tasks = query.list();		
		session.getTransaction().commit();
		
		for(int i=0;i<tasks.size();i++) {
			list.add(tasks.get(i));
		}
		
		
		}
		finally {
			factory.close();
		}
    	
    	
    	
    	
    	idColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("id"));
    	userColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("user"));
    	roomColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("room"));
    	serviceColumn.setCellValueFactory(new PropertyValueFactory<Task, Integer>("service"));
    	descriptionColumn.setCellValueFactory(new PropertyValueFactory<Task, String>("description"));
        
    	taskTableView.setItems(list);
    	
    	
    	
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
    private void printOutput() 
    {
        
    }
	
}
