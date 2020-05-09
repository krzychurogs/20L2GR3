package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.converter.DateStringConverter;
import net.bytebuddy.asm.Advice.Local;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.persistence.EntityManager;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.transaction.Transactional;

import org.controlsfx.control.table.TableRowExpanderColumn;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
public class ReceptionistController implements Initializable{
	@FXML
    private DatePicker datapick;
	 @FXML
	    private CheckBox change;
	  @FXML
	    private Pane paneorder;
	  @FXML
	    private Pane mainpane;
	    @FXML
	    private Button createreservation;

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
		private TableColumn<MenuItem,Date> enddate;
		
		@FXML
		private TableColumn<Rooms,Integer> idRoomf;
		
		@FXML
		private TableColumn<Rooms,Integer> numberOfSeatsF;
		
		@FXML
		private TableColumn <Rooms,Integer>roomNumberF;
				
		@FXML
		private TableColumn<Rooms,String> lvlF;
		
		  @FXML
		    private TextField opistask;
		@FXML
		 private Label nick;
		
		 @FXML
		    private Button dodajroom;
		 @FXML
		    private Button addTaskButton;
		 @FXML
		    private ChoiceBox<String> choiceservice;
		    @FXML
		    private ChoiceBox<String> choiceroomfortask;
		    @FXML
		    private Button addtask;
		    @FXML
		    private Button mainTableButton;
		    @FXML
		    private TextField nameguest;

		    @FXML
		    private TextField surnameguest;

		    @FXML
		    private ChoiceBox<String> choiceuser;    
		    @FXML
		    private DatePicker enddatepicker;

		    @FXML
		    private Label lbldatereser;

		    @FXML
		    private Label lbldateend;

		    @FXML
		    private Label lblimie;

		    @FXML
		    private Label lblnazwisko;
		    
		    
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
			 change.setText("Zajete Pokoje");
		 }
	    	
	    }
	 @FXML
	    void wyloguj(ActionEvent event) throws Exception {	
		 Parent application = FXMLLoader.load(getClass().getResource("Login.fxml"));
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
	    	nick.setText("Zalogowany: "+loggedUserName);
	    	nameguest.setVisible(false);
	    	surnameguest.setVisible(false);
	  

	    	
	    	setTables();
	    	
	    	
	    	 dodajroom.setOnAction((event) -> {
	 		    // Button was clicked, do something...
	 		    try {
	 		    	
	 				usun();
	 				
	 			} catch (Throwable e) {
	 				// TODO Auto-generated catch block
	 				e.printStackTrace();
	 			}
	 		});
	    	 
	    	 addtask.setOnAction((event) -> {
		 		    // Button was clicked, do something...
		 		    try {
		 				addTask(choiceservice, choiceroomfortask);
		 				
		 			} catch (Throwable e) {
		 				// TODO Auto-generated catch block
		 				e.printStackTrace();
		 			}
		 		});
	    	
	    		    	
		}
	 
	 @Transactional
	 private void usun() 
		{
		 			try {
		 			if(!freeRooms.getSelectionModel().getSelectedItems().isEmpty()) 
		 			{
					
		 			daneguesta();//panel dodawania goscia wraz z data

					SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
				 	Session session=sessionFactory.openSession();
				 	session.beginTransaction();	
				    System.out.println("dd:"+freeRooms.getSelectionModel().getSelectedItems().get(0).getRoomNumber());
				    Query query = session.createQuery("from Rooms r WHERE r.roomNumber=:roomnumber");
					query.setParameter("roomnumber",freeRooms.getSelectionModel().getSelectedItems().get(0).getRoomNumber());
					List<Rooms>rooms = query.list();	
					Rooms pickedRoom=rooms.get(0);	
					System.out.println(pickedRoom.getRoomNumber()+"pokoj");
				     datapick.setOnAction((event) -> {
				    	 		Date data = Date.valueOf(datapick.getValue());
				    	 			enddatepicker.setOnAction((event3) -> {
				 		    		Date enddata = Date.valueOf(enddatepicker.getValue());
								      createreservation.setOnAction((event1) -> { //przycisk tworzacy guesta 
								 		   for(int i=0;i<pickedRoom.getReservation().size();i++) {
								 			   if(!(pickedRoom.getReservation().get(i).getDates().before(data)&&pickedRoom.getReservation().get(i).getEndDate().after(data))&&
								 					  pickedRoom.getReservation().get(i).getDates().before(enddata)&&pickedRoom.getReservation().get(i).getEndDate().after(enddata)) {
								 				  Alert a1=new Alert(Alert.AlertType.ERROR);
									 				a1.setContentText("Data jest juz zajeta");
									 				a1.setTitle("Blad");
									 				a1.setHeaderText(null);
									 				a1.show();
									 				return;
								 			   }
								 		   }
								 		    try {
									 		    	int id = 0;
													for(int i=0;i<rooms.size();i++) {
														id=rooms.get(i).getId(); //id roomu wybranego
													}
													String imie=nameguest.getText();
													String nazwisko=surnameguest.getText();

													Guest guest=new Guest(0,imie,nazwisko,null);
													
													if(!nameguest.getText().equals("") && !surnameguest.getText().equals(""))
													{
	
														session.save(guest);
														session.close();
														input(guest,id,data,enddata);//stworzenie rezerwacji
													}
													else {
														Alert a1=new Alert(Alert.AlertType.ERROR);
										 				a1.setContentText("Nie poda3es danych");
										 				a1.setTitle("Blad");
										 				a1.setHeaderText(null);
										 				a1.show();
													}
													
												
								 		    	
								 				
								 			} catch (Exception e) {

								 				
								 			}
								 		});			
				     		});
				 			
				 		});
				   
		 			}
		 			}
		 			catch (Exception e) {
		 				Alert a1=new Alert(Alert.AlertType.ERROR);
		 				a1.setContentText("Nie wybrales pokoju");
		 				a1.setTitle("Blad");
		 				a1.setHeaderText(null);
		 				a1.show();
		 				glowna();
		 			
					}
		 			
		 								    			     
		}
	 
	 
	 private void addTask(ChoiceBox<String> choiceservice,ChoiceBox<String> choiceroomfortask) throws Exception
		{
			
		 			try {
						
		 				
					
		 			String opis=opistask.getText();
					String dane=choiceservice.getValue();
					String wynik1[] = null;
					wynik1 = dane.split("_");
					
					//	System.out.print(wynik1[0]);
					SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
				 	Session session=sessionFactory.openSession();
				 	session.beginTransaction();	
				    
				 	
				    Query query = session.createQuery("from Services s WHERE s.name=:servicename");
					query.setParameter("servicename",wynik1[0]);
					
					
					List<Services>service=query.list();
					int idservice = 0;
					for(int i=0;i<service.size();i++) {
						idservice=service.get(i).getId(); 
					}
					
				
					String daneroom=choiceroomfortask.getValue();
					String wynik2[]=null;
					wynik2 = daneroom.split("_");
					
					Query query1 = session.createQuery("from Rooms r WHERE r.roomNumber=:roomnumber");
					query1.setParameter("roomnumber",Integer.parseInt(wynik2[0]));
					List <Rooms>rooms=query1.list();
					int idroom=0;
					
					for(int i=0;i<rooms.size();i++)
					{
						idroom=rooms.get(i).getId();
					}
					
					String daneuser=choiceuser.getValue();
					String wynik3[]=null;
					wynik3 = daneuser.split("_");
					
					Query query2 = session.createQuery("from User u WHERE u.login=:login");
					query2.setParameter("login",(wynik3[0]));
					
					
					
					List<User>users=query2.list();
					int iduser=0;
					
					for(int i=0;i<users.size();i++)
					{
						iduser=users.get(i).getId();
					}
					session.close();
					inputTask(idroom,iduser,idservice,opis);
					
		 			}
		 			catch (Exception e) {
		 				Alert a1=new Alert(Alert.AlertType.ERROR);
		 				a1.setContentText("Z3e wype3nione dane");
		 				a1.setTitle("Blad");
		 				a1.setHeaderText(null);
		 				a1.show();
					}
		}
	 
	 
	public void inputTask(int idroom,int iduser,int idservice,String opis) throws Exception
	{
		 Connection con=getconnection();
		 String query = " insert into task(description,status,room_ID,service_ID,user_ID)"
				 + " values (?,?,?,?,?)";
			 PreparedStatement preparedStmt = con.prepareStatement(query);
			 preparedStmt.setString(1, opis);
		     preparedStmt.setBoolean(2, true);
		     preparedStmt.setInt(3, idroom);
		     preparedStmt.setInt(4, idservice);
		     preparedStmt.setInt(5, iduser);
		  
		   
		    int i=preparedStmt.executeUpdate();
		    if(i>0)
		    {
		    	glowna();
		    }
		    
		 
	}
	 
	 
	 
	 
	    void glowna() {
		  	takenRooms.setVisible(true);
		  	freeRooms.setVisible(true);
		  	takenRooms.setVisible(true);
		  	change.setVisible(true);
		  	dodajroom.setVisible(true);
		  	opistask.setVisible(false);
		  	choiceroomfortask.setVisible(false);
		  	choiceservice.setVisible(false);
		  	addtask.setVisible(false);
		  	choiceuser.setVisible(false);
		  	datapick.setVisible(false);
		  	nameguest.setVisible(false);
		  	surnameguest.setVisible(false);
		  	createreservation.setVisible(false);
		 	enddatepicker.setVisible(false);
		 	lbldateend.setVisible(false);
		  	lbldatereser.setVisible(false);
		  	lblimie.setVisible(false);
		  	lblnazwisko.setVisible(false);
		  	mainTableButton.setVisible(false);
		  	addTaskButton.setVisible(true);
		  	}
	 
	 
	 public void input(Guest guest,int roomId,Date data,Date enddata) throws Exception
	 {
		 SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();	
			Rooms room=session.get(Rooms.class, roomId);
			Bill bill =new Bill();
			Reservation reservation = new Reservation(0,room,data,enddata,guest,bill);
			session.save(bill);
			session.save(reservation);	
		    session.getTransaction().commit();
		      takenRooms.getItems().clear();
		      freeRooms.getItems().clear();
		      setTables();
				      
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
	 public void setTables()
	 {
		 
		 item=FXCollections.observableArrayList();
		 list=FXCollections.observableArrayList();
		 
		 SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();	
		    List<Reservation>reservation = new ArrayList<Reservation>() ;
		    TableRowExpanderColumn<Rooms> expanderColumn=new TableRowExpanderColumn<>(this::createEditor);
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
		    	MenuItem pomocnicza2=new MenuItem(rooms.get(i).getRoomNumber(),rooms.get(i).getNumberOfSeats(),rooms.get(i).getLvl(),rooms.get(i).getReservation().get(j).getDates(),rooms.get(i).getReservation().get(j).getEndDate());
		    	item.add(pomocnicza2);
		    
		    }
		    
		    }
		    
		
	    	roomNumber.setCellValueFactory(new PropertyValueFactory<MenuItem, Integer>("roomNumber"));
	    	numberOfSeats.setCellValueFactory(new PropertyValueFactory<MenuItem, Integer>("numberOfSeats"));
	    	lvl.setCellValueFactory(new PropertyValueFactory<MenuItem, String>("lvl"));
	    	dates.setCellValueFactory(new PropertyValueFactory<MenuItem, Date>("dates"));
	    	enddate.setCellValueFactory(new PropertyValueFactory<MenuItem, Date>("enddate"));
	    	
	    	roomNumberF.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("roomNumber"));
	    	numberOfSeatsF.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("numberOfSeats"));
	    	lvlF.setCellValueFactory(new PropertyValueFactory<Rooms, String>("lvl"));
	    	
	    	freeRooms.getColumns().addAll(expanderColumn);
	    	takenRooms.setItems(item);	
	    	freeRooms.setItems(list);
	    	takenRooms.setVisible(false);
	    	

	    	  Query query3 = session.createQuery("from Rooms");	
	    	  List<Rooms>rooms2 = query3.list();	
	    	  for(int i=0;i<rooms2.size();i++)
			    {
	    		 	int roomnumber=rooms2.get(i).getRoomNumber();                            //choicebox wolnych pokoi
	    			int numberOfSeats=rooms2.get(i).getNumberOfSeats();
	    		 	String lvl=rooms2.get(i).getLvl();
	    			String roomnumbe=String.valueOf(roomnumber)+"_"+String.valueOf(numberOfSeats)+ "_"+lvl;
	    			choiceroomfortask.getItems().add(roomnumbe);
	    		 
			    }
	    	  
	    	  Query query4 = session.createQuery("from Services");	
	    	  List<Services>services = query4.list();	
	    	  for(int i=0;i<services.size();i++)
			    {
	    		  	String nameService=services.get(i).getName();
	    		 	float priceService=services.get(i).getPrice();                           //choicebox wolnych pokoi
	    			
	    			String service=nameService+ "_" + String.valueOf(priceService);
	    			choiceservice.getItems().add(service);
	    		 
			    }
	    	  
	    	  Query query5 = session.createQuery("from User");	
	    	  List<User>users = query5.list();	
	    	  for(int i=0;i<users.size();i++)
			    {
	    		  	String name=users.get(i).getLogin();
	    		  	choiceuser.getItems().add(name);
	  
			    }
	    	  
	    	  
	    	  session.close();
		 
	 }
	 private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Rooms> param) {
		 GridPane editor=new GridPane();
		 editor.setPadding(new Insets(10));
		 Rooms room=param.getValue();
		 if(!room.getReservation().isEmpty()) {
		 for(int i=0;i<room.getReservation().size();i++) {
			 editor.addRow(i, new Label(i+1+". OD: "+room.getReservation().get(i).getDates().toString()+" DO:"+room.getReservation().get(i).getEndDate().toString() ));
		 }
		 }
		 return editor;
		 
	 }
	  @FXML
	    void glowna(ActionEvent event) {
		  	takenRooms.setVisible(true);
		  	freeRooms.setVisible(true);
		  	takenRooms.setVisible(true);
		  	change.setVisible(true);
		  	dodajroom.setVisible(true);
		  	opistask.setVisible(false);
		  	choiceroomfortask.setVisible(false);
		  	choiceservice.setVisible(false);
		  	addtask.setVisible(false);
		  	choiceuser.setVisible(false);
		  	datapick.setVisible(false);
		  	nameguest.setVisible(false);
		  	surnameguest.setVisible(false);
		  	createreservation.setVisible(false);
		  	enddatepicker.setVisible(false);
		  	addTaskButton.setVisible(true);
			mainTableButton.setVisible(false);
		  	addTaskButton.setVisible(true);
		  	lbldatereser.setVisible(false);
		  	lbldateend.setVisible(false);
		  	lblimie.setVisible(false);
		  	lblnazwisko.setVisible(false);
		  	
		  
	    }
	  @FXML
	    void dodajzadanie(ActionEvent event) {
		  	takenRooms.setVisible(false);
		  	freeRooms.setVisible(false);
		  	change.setVisible(false);
		  	dodajroom.setVisible(false);
		  	opistask.setVisible(true);
		  	choiceservice.setVisible(true);
		  	choiceroomfortask.setVisible(true);
		  	addtask.setVisible(true);
		  	choiceuser.setVisible(true);
		  	datapick.setVisible(false);
		  	nameguest.setVisible(false);
		  	surnameguest.setVisible(false);
		  	createreservation.setVisible(false);
		  	enddatepicker.setVisible(false);
			lbldateend.setVisible(false);
		  	lbldatereser.setVisible(false);
		  	lblimie.setVisible(false);
		  	lblnazwisko.setVisible(false);
			addTaskButton.setVisible(false);
			mainTableButton.setVisible(true);
		  	addTaskButton.setVisible(false);
	    }
	  
	  
	    void daneguesta() {
		  	takenRooms.setVisible(false);
		  	freeRooms.setVisible(false);
		  	takenRooms.setVisible(false);
		  	change.setVisible(false);
		  	dodajroom.setVisible(false);
		  	opistask.setVisible(false);
		  	choiceroomfortask.setVisible(false);
		  	choiceservice.setVisible(false);
		  	addtask.setVisible(false);
		  	choiceuser.setVisible(false);
		  	datapick.setVisible(true);
		  	nameguest.setVisible(true);
		  	surnameguest.setVisible(true);
		  	createreservation.setVisible(true);
		  	enddatepicker.setVisible(true);
		  	lbldateend.setVisible(true);
		  	lbldatereser.setVisible(true);
		  	lblimie.setVisible(true);
		  	lblnazwisko.setVisible(true);
		  	mainTableButton.setVisible(true);
		  	addTaskButton.setVisible(false);
	    }
	    
	
}