package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.persistence.EntityManager;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class ReceptionistController implements Initializable{

	 @FXML
	    private CheckBox change;
	 @FXML 
		private Pane pane;
	    @FXML
	    private TableView<MenuItem> takenRooms;
	    @FXML
	    private TableView<Rooms> freeRooms;
		
		@FXML
		private TableColumn<MenuItem,Integer> numberOfSeats;
		
		@FXML
		private TableColumn <MenuItem,Integer>roomNumber;
			
		@FXML
		private TableColumn<MenuItem,String> lvl;
		@FXML
		private TableColumn<MenuItem,Date> dates;
		
		@FXML
		private TableColumn<Rooms,Integer> idRoomf;
		
		@FXML
		private TableColumn<Rooms,Integer> numberOfSeatsF;
		
		@FXML
		private TableColumn <Rooms,Integer>roomNumberF;
				
		@FXML
		private TableColumn<Rooms,String> lvlF;
		

		@FXML
		 private Label nick;
		
		String loggedUserName;
		public ObservableList <MenuItem> item;
		public ObservableList <Rooms> list;
	
		@FXML
	    void zmien(ActionEvent event) throws Exception {
	    	
		 boolean isSelected = change.isSelected();
		 if(isSelected == true)
		 {	 
			 
			 takenRooms.setVisible(true);
			 freeRooms.setVisible(false);
			 change.setText("Wolne Pokoje");
		 }
		 if(isSelected == false)
		 {
			 
			
			 takenRooms.setVisible(false);
			 freeRooms.setVisible(true);
			 change.setText("Zajête Pokoje");
		 }
	    	
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
	 
	 
	 public void initialize(URL url, ResourceBundle rbl) {
		 item=FXCollections.observableArrayList();
		 list=FXCollections.observableArrayList();

			Preferences userPreferences = Preferences.userRoot();
	    	loggedUserName = userPreferences.get("loggedUsername","");
	    	nick.setText("Zalogowany Recepjonista: "+loggedUserName);

	    	SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();	
		    Query query = session.createQuery("from Rooms");	
		    
		    List<Rooms>rooms = query.list();		
		    session.getTransaction().commit();
		    for(int i=0;i<rooms.size();i++)
		    {
		    	list.add(rooms.get(i));
		    }
		
		    for(int i=0;i<rooms.size();i++)
		    {
		    for(int j=0;j<rooms.get(i).getReservation().size();j++)
		    {
		    	MenuItem pomocnicza2=new MenuItem(rooms.get(i).getRoomNumber(),rooms.get(i).getNumberOfSeats(),rooms.get(i).getLvl(),rooms.get(i).getReservation().get(j).getDates());
		    	item.add(pomocnicza2);
		    
		    }
		    
		    }
		
	    	roomNumber.setCellValueFactory(new PropertyValueFactory<MenuItem, Integer>("roomNumber"));
	    	numberOfSeats.setCellValueFactory(new PropertyValueFactory<MenuItem, Integer>("numberOfSeats"));
	    	lvl.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("lvl"));
	    	dates.setCellValueFactory(new PropertyValueFactory<MenuItem, Date>("dates"));
	    	
	    	roomNumberF.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("roomNumber"));
	    	numberOfSeatsF.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("numberOfSeats"));
	    	lvlF.setCellValueFactory(new PropertyValueFactory<Rooms, String>("lvl"));
	    	
	    	
	    	takenRooms.setItems(item);	
	    	freeRooms.setItems(list);
	    	takenRooms.setVisible(false);
	    		    	
		}
	
}




