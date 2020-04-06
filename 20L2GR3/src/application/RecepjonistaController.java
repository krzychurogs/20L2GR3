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

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
public class RecepjonistaController implements Initializable{

	 @FXML
	    private CheckBox zmiana;
	 @FXML 
		private Pane pane;
	    @FXML
	    private TableView<Rooms> zajetepokoje;
	    @FXML
	    private TableView<Rooms> wolnepokoje;
		@FXML
		private TableColumn<Rooms,Integer> idRoom;
		
		@FXML
		private TableColumn<Rooms,Integer> number_of_seats;
		
		@FXML
		private TableColumn <Rooms,Integer>room_number;
				
		@FXML
		private TableColumn<Rooms,String> lvl;
		
		
		@FXML
		private TableColumn<Rooms,Integer> idRoomf;
		
		@FXML
		private TableColumn<Rooms,Integer> number_of_seatsf;
		
		@FXML
		private TableColumn <Rooms,Integer>room_numberf;
				
		@FXML
		private TableColumn<Rooms,String> lvlf;
		
		@FXML
		private TableColumn<Rooms,Date> dates;
		
		
		public ObservableList <Rooms> list;
		public ObservableList <Rooms> list1;
	 @FXML
	    void zmien(ActionEvent event) throws Exception {
	    	
		 boolean isSelected = zmiana.isSelected();
		 if(isSelected == true)
		 {	 
			 
			 zajetepokoje.setVisible(true);
			 wolnepokoje.setVisible(false);
			 zmiana.setText("Wolne Pokoje");
		 }
		 if(isSelected == false)
		 {
			 
			
			 zajetepokoje.setVisible(false);
			 wolnepokoje.setVisible(true);
			 zmiana.setText("Zajête Pokoje");
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
		 list=FXCollections.observableArrayList();
		 list1=FXCollections.observableArrayList();

		 Date data= new Date();
		 data.getTime();	
	     Rooms test = new Rooms(1,2,312,"High",data);
	     Rooms test1 = new Rooms(2,2,314,"Medium");
	     list.add(test);
	     list1.add(test1);
	    	
	    	idRoom.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("id"));
	    	number_of_seats.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("number_of_seats"));
	    	room_number.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("room_number"));
	    	lvl.setCellValueFactory(new PropertyValueFactory<Rooms, String>("lvl"));
	    	dates.setCellValueFactory(new PropertyValueFactory<Rooms, Date>("data"));
	    	
	    	
	    	idRoomf.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("id"));
	    	number_of_seatsf.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("number_of_seats"));
	    	room_numberf.setCellValueFactory(new PropertyValueFactory<Rooms, Integer>("room_number"));
	    	lvlf.setCellValueFactory(new PropertyValueFactory<Rooms, String>("lvl"));
	    	
	    	
	    	zajetepokoje.setItems(list);	
	    	wolnepokoje.setItems(list1);
	    	zajetepokoje.setVisible(false);
	    	
	    	
	    	
	    	
	    	
	    	
	    	
	    	
		}
	
}
