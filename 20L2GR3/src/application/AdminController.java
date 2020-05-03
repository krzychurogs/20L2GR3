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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

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

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class AdminController implements Initializable{

    @FXML
    private TextField surname;

    @FXML
    private TextField login;

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField rpassword;

    @FXML
    private ChoiceBox<String> choicejob;
    @FXML
    private Text header;
    @FXML
    private Button btncreate;
    @FXML
    private Button back;

    @FXML
    private Label lblname;

    @FXML
    private Label lblsurname;

    @FXML
    private Label lbllogin;

    @FXML
    private Label lblpassword;

    @FXML
    private Label lblrpassword;

    @FXML
    private Label lbjob;
	 
	 public void initialize(URL url, ResourceBundle rbl) {
	
	    	
	    		    setChoiceJobs();	
	    		    btncreate.setOnAction((event) -> {
			 		    // Button was clicked, do something...
			 		    try {
			 				createUser(choicejob);
			 				
			 			} catch (Throwable e) {
			 				// TODO Auto-generated catch block
			 				e.printStackTrace();
			 			}
			 		});
	 
	 }
	 

	 
	 public void setChoiceJobs()
	 {
		 SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 Session session=sessionFactory.openSession();
		 session.beginTransaction();	
		  Query query = session.createQuery("from Job");	
    	  List<Job>jobs = query.list();	
    	  int id = 0;
    	  
    	  for(int i=0;i<jobs.size();i++)
		    {
    		 	
    		 	String lvl=jobs.get(i).getName();
    			choicejob.getItems().add(lvl);
    			choicejob.getValue();
    
		    }
    	
	 }
	 
	 public void createUser(ChoiceBox<String> choicejob)
	 {
	
		 
		 String dane=choicejob.getValue();
		
		 

		 SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 Session session=sessionFactory.openSession();
		 session.beginTransaction();	
		
		 
		 
		 String haslo=password.getText();
		 String rhaslo=rpassword.getText();
		 
		 String imie=name.getText();
		 String nazwisko=surname.getText();
		 String logi=login.getText();
		 Job job=session.get(Job.class,2);
		 session.getTransaction().commit();

		 session.beginTransaction();
		 Job job1=session.get(Job.class,1);
		 session.getTransaction().commit();
		 
		 session.beginTransaction();
		 Job job2=session.get(Job.class,3);
		 session.getTransaction().commit();
		 
		 
		 session.beginTransaction();
		 
		 if(password.equals(rpassword))
		 {
			 
		 
				 if(dane.equals("Recepjonista"))
				 {
				 	User user=new User(imie,nazwisko,logi,haslo,job);
				 	session.save(user);
					session.getTransaction().commit();
					
				 }
				 else if (dane.equals("Kucharz")) {
					 
					 	User user=new User(imie,nazwisko,logi,haslo,job2);
					 	session.save(user);
						session.getTransaction().commit();
				}
				 else if (dane.equals("Sprzataczka")) {
					 
					 	User user=new User(imie,nazwisko,logi,haslo,job1);
					 	session.save(user);
						session.getTransaction().commit();
						
				}
		}
		 else {
			 Alert a1=new Alert(Alert.AlertType.ERROR);
				a1.setContentText("Has³a siê nie zgadzaja");
				a1.setTitle("Blad");
				a1.setHeaderText(null);
				a1.show();
		}
		 
		 
	 }
	 @FXML
	    void showTable(ActionEvent event) {
		  	surname.setVisible(false);
		  	name.setVisible(false);
		  	login.setVisible(false);
		  	password.setVisible(false);
		  	rpassword.setVisible(false);
		  	btncreate.setVisible(false);
		  	choicejob.setVisible(false);
		  	lbllogin.setVisible(false);
		  	lblname.setVisible(false);
		  	lblpassword.setVisible(false);
		  	lblrpassword.setVisible(false);
		  	lblsurname.setVisible(false);
		  	lbjob.setVisible(false);
		  	back.setVisible(false);
		  	header.setVisible(false);
	    }
	 @FXML
	    void showCreate(ActionEvent event) {
		  	surname.setVisible(true);
		  	name.setVisible(true);
		  	login.setVisible(true);
		  	password.setVisible(true);
		  	rpassword.setVisible(true);
		  	btncreate.setVisible(true);
		  	choicejob.setVisible(true);
		  	lbllogin.setVisible(true);
		  	lblname.setVisible(true);
		  	lblpassword.setVisible(true);
		  	lblrpassword.setVisible(true);
		  	lblsurname.setVisible(true);
		  	lbjob.setVisible(true);
		  	back.setVisible(true);
		  	header.setVisible(true);
	    }
	
	

	

	
}