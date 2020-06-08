package application;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.prefs.Preferences;

import javax.security.auth.login.LoginContext;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.hibernate.service.ServiceRegistry;
/**
 * Klasa reprezentujaca logowanie(Startowa)
 */
public class Main extends Application {
	
	private static SessionFactory factory;
    private static ServiceRegistry serviceRegistry;
	@FXML 
	private Pane pane;
	
	@FXML
	private TextField loginfield;
	@FXML
	private PasswordField passwordfield;
	Pane content;
	
	
	  
    /**
     * Metoda reprezentujaca logowanie.
     * @param loggedUser obiekt modelu user
     * @param passwordfield jest to PasswordField,z ktorego pobieramy wartosc haslo i sprawdzamy czy jest taki w bazie
     * @param loginfield jest to TextField,z ktorego pobieramy wartosc loginu i sprawdzamy czy jest taki w bazie
     
    
     */
	public void Login(ActionEvent event)throws Exception
	{	
		SessionFactory factory=new Configuration().configure("hibernate.cfg.xml").addAnnotatedClass(User.class).buildSessionFactory();
		Session session=factory.openSession();
		User loggedUser = null;
		try {
		session.beginTransaction();	
		Query query = session.createQuery("from User u WHERE u.login=:username");
		query.setParameter("username",loginfield.getText());
		List<User>users = query.list();
		if(users.isEmpty()==false)
		loggedUser=users.get(0);
		
		session.getTransaction().commit();
		
		
		}
		finally {
			factory.close();
		}
		
        
		if(loggedUser!=null) {
            
		if(loggedUser.password.equals(passwordfield.getText())&&loggedUser.getJob().getId()==1)
		{			
			Preferences userPreferences = Preferences.userRoot();
	        userPreferences.put("loggedId",Integer.toString(loggedUser.id));
	        userPreferences.put("loggedUsername",loggedUser.login);
	        userPreferences.put("loggedJobId",Integer.toString(loggedUser.getJob().getId()));
			Pane panes = FXMLLoader.load(this.getClass().getResource("Receptionist.fxml"));
	        pane.getChildren().setAll(panes);
	        
			
		}
		else if(loggedUser.password.equals(passwordfield.getText())&&loggedUser.getJob().getId()==2)
		{		
	        Preferences userPreferences = Preferences.userRoot();
	        userPreferences.put("loggedId",Integer.toString(loggedUser.id));
	        userPreferences.put("loggedUsername",loggedUser.login);
	        userPreferences.put("loggedJobId",Integer.toString(loggedUser.getJob().getId()));
	        Pane panes = FXMLLoader.load(this.getClass().getResource("cookCleaner.fxml"));
			pane.getChildren().setAll(panes);
			
		}
		else if(loggedUser.password.equals(passwordfield.getText())&&loggedUser.getJob().getId()==3)
		{
			
			Preferences userPreferences = Preferences.userRoot();
	        userPreferences.put("loggedId",Integer.toString(loggedUser.id));
	        userPreferences.put("loggedUsername",loggedUser.login);
	        userPreferences.put("loggedJobId",Integer.toString(loggedUser.getJob().getId()));
			Pane panes = FXMLLoader.load(this.getClass().getResource("manager.fxml"));
	        pane.getChildren().setAll(panes);
	        
			
		}
		else if(loggedUser.password.equals(passwordfield.getText())&&loggedUser.getJob().getId()==4)
		{
			Preferences userPreferences = Preferences.userRoot();
	        userPreferences.put("loggedId",Integer.toString(loggedUser.id));
	        userPreferences.put("loggedUsername",loggedUser.login);
	        userPreferences.put("loggedJobId",Integer.toString(loggedUser.getJob().getId()));
			Pane panes = FXMLLoader.load(this.getClass().getResource("account.fxml"));
	        pane.getChildren().setAll(panes);
	        
			
		}
		else if(loggedUser.password.equals(passwordfield.getText())&&loggedUser.getJob().getId()==5)
		{
			
			Preferences userPreferences = Preferences.userRoot();
	        userPreferences.put("loggedId",Integer.toString(loggedUser.id));
	        userPreferences.put("loggedUsername",loggedUser.login);
	        userPreferences.put("loggedJobId",Integer.toString(loggedUser.getJob().getId()));
			Pane panes = FXMLLoader.load(this.getClass().getResource("account.fxml"));
	        pane.getChildren().setAll(panes);
	     		
		}
		else if(loggedUser.password.equals(passwordfield.getText())&&loggedUser.getJob().getId()==6)
		{			
	        Preferences userPreferences = Preferences.userRoot();
	        userPreferences.put("loggedId",Integer.toString(loggedUser.id));
	        userPreferences.put("loggedUsername",loggedUser.login);
	        userPreferences.put("loggedJobId",Integer.toString(loggedUser.getJob().getId()));
	        Pane panes = FXMLLoader.load(this.getClass().getResource("cookCleaner.fxml"));
			pane.getChildren().setAll(panes);
	     		
		}
		else {
			Alert a = new Alert(AlertType.NONE);
		    a.setAlertType(AlertType.INFORMATION); 
		    a.setContentText("Zle dane logowania");
		    a.getDialogPane().setPrefSize(200, 100);
           a.show(); 
		}
		
		}
		else {
			Alert a = new Alert(AlertType.NONE);
		    a.setAlertType(AlertType.INFORMATION); 
		    a.setContentText("Zle dane logowania");
		    a.getDialogPane().setPrefSize(200, 100);
           a.show(); 
		}
		
	}
	
	  
    /**
     * Metoda ³adujaca formatke logowania(startowa)
   
     */
	@Override
	public void start(Stage primaryStage) {
		
		
		try {	
			 Parent root=FXMLLoader.load(getClass().getResource("Login.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setResizable(false);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
		//Guest guest=session.get(Guest.class,1);
		//Date data=new Date(1,2,2000);
		//Bill bill=new Bill();
		//Reservation reservation = new Reservation(3,room,guest,data,bill);
		//session.save(reservation);
		//List<Services> services=reservation.getBill().getServices();
		//session.beginTransaction();
		//session.getTransaction().commit();
		
		//for(int i=0;i<services.size();i++)
		//	System.out.print("cena"+services.get(i).getPrice());
	
	
		try {
		//User tempuser=new User(1,"user","12345",1);
		//session.beginTransaction();
			//session.save(tempuser);
			//session.getTransaction().commit();
		}
		finally {
		//	factory.close();
		}
		
		
		
		
	}
	
	  
    /**
     * Metoda Main
     */
	public static void main(String[] args)  {
		launch(args);
	}
}