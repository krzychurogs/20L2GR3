package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
public class RecepjonistaController implements Initializable{
	 @FXML
	    private CheckBox zmiana;
	 @FXML 
		private Pane pane;
	    @FXML
	    private TableView<Pokoje> zajetepokoje;
	    @FXML
	    private TableView<Pokoje> wolnepokoje;
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
			
		 zajetepokoje.setVisible(false);
		}
	
}
