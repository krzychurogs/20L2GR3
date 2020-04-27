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
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
		
		 @FXML
		    private Button dodajroom;
		 @FXML
		   private ChoiceBox<String> choiceroom;
		
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
		    List<Reservation>reservation = new ArrayList<Reservation>() ;
		 	
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
	    	
	    	 Query query1 = session.createQuery("from Rooms r WHERE NOT EXISTS ( SELECT room FROM Reservation l WHERE r.id = l.room )");	
	    	  List<Rooms>rooms1 = query1.list();	
	    	  for(int i=0;i<rooms1.size();i++)
			    {
	    		 	int roomnumber=rooms1.get(i).getRoomNumber();
	    			int numberOfSeats=rooms1.get(i).getNumberOfSeats();
	    		 	String lvl=rooms1.get(i).getLvl();
	    			String roomnumbe=String.valueOf(roomnumber)+"_"+String.valueOf(numberOfSeats)+ "_"+lvl;
	    			choiceroom.getItems().add(roomnumbe);
	    		 
			    }
	    	
	    	 dodajroom.setOnAction((event) -> {
	 		    // Button was clicked, do something...
	 		    try {
	 				usun(choiceroom);
	 				
	 			} catch (Throwable e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 		});
	    	 
	    	
	    		    	
		}
	 private void usun(ChoiceBox<String> choiceroom) throws Exception
		{
			
			
	
					String dane=choiceroom.getValue();
					String wynik1[] = null;
					wynik1 = dane.split("_");
					
					//	System.out.print(wynik1[0]);
					SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
				 	Session session=sessionFactory.openSession();
				 	session.beginTransaction();	
				    
				 	
				    Query query = session.createQuery("from Rooms r WHERE r.roomNumber=:roomnumber");
					query.setParameter("roomnumber",Integer.parseInt(wynik1[0]));
					List<Rooms>rooms = query.list();	
					int id = 0;
					for(int i=0;i<rooms.size();i++) {
						id=rooms.get(i).getId(); //id roomu wybranego
					}
					String url="jdbc:mysql://localhost:3306/hotel";
					String user = "root";
				     String password = "";
				       
				     Connection con=DriverManager.getConnection(url, user, password);
					
					
					String imie=JOptionPane.showInputDialog(null,"Imie",JOptionPane.OK_CANCEL_OPTION);
					String nazwisko=JOptionPane.showInputDialog(null,"Nazwisko",JOptionPane.OK_CANCEL_OPTION);
					

				      String query3 = " insert into guest(name,surname)"
					        + " values (?,?)";
					 PreparedStatement preparedStmt1 = con.prepareStatement(query3);
					 preparedStmt1.setString(1, imie);
				      preparedStmt1.setString(2, nazwisko);
				   
				   
				      preparedStmt1.executeUpdate();
					input(imie, nazwisko, id);
					
					
					
		
  		      
  		      
		}
	 public void input(String imie,String nazwisko,int id) throws Exception
	 {
		 Connection con=getconnection();
		 SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();	
		 Query query1 = session.createQuery("from Guest g WHERE g.name=:name AND g.surname=:surname");
			query1.setParameter("name",imie);	
			query1.setParameter("surname",nazwisko);	
			List <Guest>guest=query1.list();
			int idguest = 0;
			for(int i=0;i<guest.size();i++) {
			guest.get(i).getName();//imie wyszukanego
				idguest=guest.get(i).getId();//nazwisko
			}
		
		      String query2 = " insert into reservation(date_reservation,reservation,guest)"
			        + " values (?,?,?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query2);
			 preparedStmt.setString(1, "2020-04-22");
		      preparedStmt.setInt(2, id);
		      preparedStmt.setInt(3 ,idguest);
		   
		      preparedStmt.executeUpdate();
		      
	 }
	 public static Connection getconnection()throws Exception
		{
				try {
					String url="jdbc:mysql://localhost:3306/hotel";
					String user = "root";
				     String password = "";;
			     Connection con=DriverManager.getConnection(url, user, password);
			      return con;
				
			} catch (Exception e) {
				System.out.println(e);
			}
			return null;
			
		}
	 
	
	
}



