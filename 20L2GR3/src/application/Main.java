	package application;
	
import javax.security.auth.login.LoginContext;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class Main extends Application {
	@FXML 
	private Pane pane;
	
	@FXML
	private TextField loginfield;
	@FXML
	private PasswordField passwordfield;
	Pane content;
	public void Login(ActionEvent event)throws Exception
	
	{
		if(loginfield.getText().equals("recepjonista") && passwordfield.getText().equals("pass"))
		{
			
			Pane panes = FXMLLoader.load(this.getClass().getResource("Recepjonista.fxml"));
	        pane.getChildren().setAll(panes);
			
		}
		else if(loginfield.getText().equals("kucharz") && passwordfield.getText().equals("pass"))
		{
			
			Pane panes = FXMLLoader.load(this.getClass().getResource("cookCleaner.fxml"));
	        pane.getChildren().setAll(panes);
			
		}
		else if(loginfield.getText().equals("manager") && passwordfield.getText().equals("pass"))
		{
			
			Pane panes = FXMLLoader.load(this.getClass().getResource("manager.fxml"));
	        pane.getChildren().setAll(panes);
			
		}
		else if(loginfield.getText().equals("admin") && passwordfield.getText().equals("pass"))
		{
			
			Pane panes = FXMLLoader.load(this.getClass().getResource("admin.fxml"));
	        pane.getChildren().setAll(panes);
			
		}
		else if(loginfield.getText().equals("konto") && passwordfield.getText().equals("pass"))
		{
			
			Pane panes = FXMLLoader.load(this.getClass().getResource("account.fxml"));
	        pane.getChildren().setAll(panes);
			
		}
		
	}
	
	
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Button btnButton=new Button();
			
			Parent root=FXMLLoader.load(getClass().getResource("Uzytkownik.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		System.out.println("dziala");
		launch(args);
	}
}