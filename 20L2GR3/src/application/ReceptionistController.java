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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
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
import java.util.Calendar;
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
	    private TableView<Rooms> dateRoomsTable;
	    
	    @FXML
		private TableColumn<Rooms,Integer> dateSizeColumn;
		
		@FXML
		private TableColumn <Rooms,Integer>dateRoomNumberColumn;
			
		@FXML
		private TableColumn<Rooms,String> dateLvlColumn;
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
		private TextField descriptionTextField;
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
		    
		    @FXML
		    private Label userChoiceLabel;
		    
		    @FXML
		    private Label roomChoiceLabel;
		    
		    @FXML
		    private Label serviceChoiceLabel;
		    
		    
		String loggedUserName;
		public ObservableList <MenuItem> item;
		public ObservableList <Rooms> list;
		Date firstDate,endDate;
		List<Rooms> roomList;
		int idservice = 0;
		int idroom=0;
		int iduser=0;
		
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
	    	descriptionTextField.setVisible(false);
	    	userChoiceLabel.setVisible(false);
		  	roomChoiceLabel.setVisible(false);
		  	serviceChoiceLabel.setVisible(false);	
	    	
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
	    	 
	    	
	    	
	    		    	
		}
	 
	 @Transactional
	 private void usun() 
		{
		 daneguesta();
		 		SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 		Session session=sessionFactory.openSession();
		 		session.beginTransaction();	
		 		Query query = session.createQuery("from Rooms");
		 		roomList = query.list();	
		 			//daneguesta();//panel dodawania goscia wraz z data	
					datapick.valueProperty().addListener((observable, oldDate, newDate)->{
						 firstDate=Date.valueOf(newDate);
						 if(firstDate!=null && endDate!=null)	 
						 refreshDateTable();
					 });
					enddatepicker.valueProperty().addListener((observable, oldDate, newDate)->{
						 endDate=Date.valueOf(newDate);
						 if(firstDate!=null && endDate!=null)
						 refreshDateTable();
				 	});			    	 		
		 					 								    			     
		}
	 private void refreshDateTable() {
			if(!roomList.isEmpty()) {		
				boolean add=true;
				 list=FXCollections.observableArrayList();
				 dateRoomNumberColumn.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("roomNumber"));
		    	dateSizeColumn.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("numberOfSeats"));
		    	dateLvlColumn.setCellValueFactory(new PropertyValueFactory<Rooms, String>("lvl"));
		    	for(int i=0;i<roomList.size();i++) {
		    		for(int j=0;j<roomList.get(i).getReservation().size();j++) {
		    		if((roomList.get(i).getReservation().get(j).getDates().before(firstDate)&&roomList.get(i).getReservation().get(j).getEndDate().after(endDate))||
		    				(roomList.get(i).getReservation().get(j).getDates().before(endDate)&&roomList.get(i).getReservation().get(j).getEndDate().after(endDate))||
		    				(roomList.get(i).getReservation().get(j).getDates().before(firstDate)&&roomList.get(i).getReservation().get(j).getEndDate().after(firstDate))||
		    				(roomList.get(i).getReservation().get(j).getDates().after(firstDate)&&roomList.get(i).getReservation().get(j).getEndDate().before(endDate))) 
		    			add=false;
		    	
		    	}
		    		if(add!=false) {
			    		list.add(roomList.get(i));
			    		}
			    		add=true;
		    	
		    	}
		    	dateRoomsTable.setItems(list);
			}
		 
	 }
	 
	 @FXML
	 private void addGuest() {
		 if(dateRoomsTable.getSelectionModel().getSelectedItems().isEmpty()==false &&firstDate.before(endDate)) {
			 SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();	
			 Session session=sessionFactory.openSession();		 	
			session.beginTransaction();	
			Rooms room=session.get(Rooms.class, dateRoomsTable.getSelectionModel().getSelectedItems().get(0).getId());
			String name=nameguest.getText();
			String surname=surnameguest.getText();
			Guest guest=new Guest();
			guest.setName(name);
			guest.setSurname(surname);
			Bill bill=new Bill();	
			Reservation reservation=new Reservation(0,room,firstDate,endDate,guest,bill);
			session.save(bill);
			session.save(guest);
			session.save(reservation);
			session.getTransaction().commit();
			addRoomToBill(bill.getId());
		 }
		 else if(!firstDate.before(endDate)) {
			   Alert a = new Alert(AlertType.NONE);
			    a.setAlertType(AlertType.INFORMATION); 
			    a.setContentText("Data konca nie moze byc przed poczatkowa");
			    a.getDialogPane().setPrefSize(200, 100);
	           a.show(); 
		 }
	 }
	
	 private void addRoomToBill(int id) {
		SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();	
		Session session=sessionFactory.openSession();		 	
		session.beginTransaction();
		Bill bill=session.get(Bill.class, id);
		Services service=session.get(Services.class, 9);
		bill.addServices(service);
		session.save(bill);
		session.save(service);
		session.getTransaction().commit();
		   Alert a = new Alert(AlertType.NONE);
		    a.setAlertType(AlertType.INFORMATION); 
		    a.setContentText("Rezerwacja dodana");
		    a.getDialogPane().setPrefSize(200, 100);
           a.show(); 
		 
	 }
	 
	 
	 @FXML
	  public void inputTask(ActionEvent event) throws Exception
		{
		   	 SessionFactory sessionFactory=new Configuration().configure().buildSessionFactory();	
			 Session session=sessionFactory.openSession();		 	
			 session.beginTransaction();	
			 String daneservice=choiceservice.getValue();
			 String daneroom=choiceroomfortask.getValue();
			 Services idservice;
			 int iduser; 
			 Rooms idroom;
			 String opis;
				String wynik1[] = null;
				wynik1 = daneservice.split(" ");
	    	  	Query query = session.createQuery("from Services s WHERE s.name=:servicename");
	    	  	query.setParameter("servicename",wynik1[0]);
	    	  	System.out.println(wynik1[0]);
	    	  	List<Services>service=query.list();
				idservice=service.get(0);

				Query query1 = session.createQuery("from Rooms r WHERE r.roomNumber=:roomnumber");
				query1.setParameter("roomnumber",Integer.parseInt(daneroom));
				List <Rooms>rooms=query1.list();
				idroom=rooms.get(0);
				opis=descriptionTextField.getText();
			 Task task=new Task(0,userForTask,idroom,idservice,opis,true);
			 session.save(task);
			 session.getTransaction().commit();   
			 Alert a = new Alert(AlertType.NONE);
			  a.setAlertType(AlertType.INFORMATION); 
			    a.setContentText("Zadanie dodane");
			    a.getDialogPane().setPrefSize(200, 100);
	            a.show(); 
			 
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
		  	addTaskButton.setVisible(true);
			mainTableButton.setVisible(false);
		  	addTaskButton.setVisible(true);
		  	lbldatereser.setVisible(false);
		  	lbldateend.setVisible(false);
		  	lblimie.setVisible(false);
		  	lblnazwisko.setVisible(false);
		  	dateRoomsTable.setVisible(false);
		  	dateSizeColumn.setVisible(false);
		  	dateLvlColumn.setVisible(false);
		  	dateRoomNumberColumn.setVisible(false);
		  	descriptionTextField.setVisible(false);
		  	userChoiceLabel.setVisible(false);
		  	roomChoiceLabel.setVisible(false);
		  	serviceChoiceLabel.setVisible(false);
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
	 	
	 
	 
	 	
	 List<Rooms> roomListToTask;
	 	List<User> userListToTask;
	 	List<Services> servicesListToTask;
	 	User userForTask;
	 	@FXML
	 	void userChoiceBoxeEvent(ActionEvent event){
	 		choiceservice.getItems().clear();
		    choiceroomfortask.getItems().clear();
		   	SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();		 	
		 	String daneuser=choiceuser.getValue();			
			Query query2 = session.createQuery("from User u WHERE u.login=:login");
			query2.setParameter("login",daneuser);
			List<User>users=query2.list();
			userForTask=users.get(0);
			int job = userForTask.getJob().getId();
			if(job==6) {

		    		  	String nameService=servicesListToTask.get(18).getName();
		    		 	float priceService=servicesListToTask.get(18).getPrice();                           		    			
		    			String service=nameService+ " " + String.valueOf(priceService)+" zl";
		    			choiceservice.getItems().add(service);		    		 				    
			}
			if(job==2) {

		    	  for(int i=9;i<17;i++)
				    {
		    		  	String nameService=servicesListToTask.get(i).getName();
		    		 	float priceService=servicesListToTask.get(i).getPrice();                          		    			
		    			String service=nameService+ " " + String.valueOf(priceService)+" zl";
		    			choiceservice.getItems().add(service);		    		 
				    }
			}
	 	}
	 	@FXML
	 	void roomChoiceBoxeEvent(ActionEvent event){
	 		
	 	}
	 	@FXML
	 	void serviceChoiceBoxeEvent(ActionEvent event){
	 		 choiceroomfortask.getItems().clear();
	 		Calendar calobj = Calendar.getInstance();
	 		Date currentDate=new Date(calobj.getTime().getTime());
	 		for(int i=0;i<roomListToTask.size();i++)
		    {
    		  for(int j=0;j<roomListToTask.get(i).getReservation().size();j++) {
    			  if(roomListToTask.get(i).getReservation().get(j).getDates().before(currentDate) && roomListToTask.get(i).getReservation().get(j).getEndDate().after(currentDate)){
    				  int roomnumber=roomListToTask.get(i).getRoomNumber();                           
    				  int numberOfSeats=roomListToTask.get(i).getNumberOfSeats();
    				  String lvl=roomListToTask.get(i).getLvl();
    				  String roomnumbe=String.valueOf(roomnumber);
    				  choiceroomfortask.getItems().add(roomnumbe);
    			  }
    		  }
	 		
		    }
	 	
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
	    	dateRoomsTable.setVisible(false);

	    	  Query query3 = session.createQuery("from Rooms");	
	    	  roomListToTask = query3.list();	
	       	  Query query4 = session.createQuery("from Services");	
	    	  servicesListToTask = query4.list();	
	    
	    	  
	    	  Query query5 = session.createQuery("from User u where u.job.id=2 or u.job.id=6 ");	
	    	  userListToTask = query5.list();	
	    	  for(int i=0;i<userListToTask.size();i++)
			    {
	    		  	String name=userListToTask.get(i).getLogin();
	    		  	choiceuser.getItems().add(name);
	  
			    }
	    		datapick.setDayCellFactory(picker -> new DateCell() {
			        public void updateItem(LocalDate date, boolean empty) {
			            super.updateItem(date, empty);
			            LocalDate today = LocalDate.now();

			            setDisable(empty || date.compareTo(today) < 0 );
			        }
			    });
	    		enddatepicker.setDayCellFactory(picker -> new DateCell() {
			        public void updateItem(LocalDate date, boolean empty) {
			            super.updateItem(date, empty);
			            LocalDate today = LocalDate.now();

			            setDisable(empty || date.compareTo(today) < 0 );
			        }
			    });
	    	  
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
		  	dateRoomsTable.setVisible(false);
		  	dateSizeColumn.setVisible(false);
		  	dateLvlColumn.setVisible(false);
		  	dateRoomNumberColumn.setVisible(false);
		  	descriptionTextField.setVisible(false);
		  	userChoiceLabel.setVisible(false);
		  	roomChoiceLabel.setVisible(false);
		  	serviceChoiceLabel.setVisible(false);
		  
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
		  	descriptionTextField.setVisible(true);
		  	userChoiceLabel.setVisible(true);
		  	roomChoiceLabel.setVisible(true);
		  	serviceChoiceLabel.setVisible(true);
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
			dateRoomsTable.setVisible(true);
		  	dateSizeColumn.setVisible(true);
		  	dateLvlColumn.setVisible(true);
		  	dateRoomNumberColumn.setVisible(true);
		  	descriptionTextField.setVisible(false);
		  	userChoiceLabel.setVisible(false);
		  	roomChoiceLabel.setVisible(false);
		  	serviceChoiceLabel.setVisible(false);
	    }
	    
	
}