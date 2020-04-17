	package application;
	

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.security.auth.login.LoginContext;

import org.hibernate.HibernateException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import org.hibernate.service.ServiceRegistry;

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
	
	
	public static Connection getconnection()throws Exception
	{
		try {
			String url="jdbc:mysql://localhost:3306/hotel";
			String user = "root";
		     String password = "";
		       Class.forName("com.mysql.cj.jdbc.Driver");
		        Connection con=DriverManager.getConnection(url, user, password);
		      	return con;
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
		
	}
    private int insertUser(int id, int nr,String lvl,int liczba_miejsc)
    {
        Session session = factory.openSession();
        Transaction tx = null;
        Integer userIdSaved = null;
        try {
            tx = session.beginTransaction();
            Rooms u = new Rooms(id,nr,liczba_miejsc,lvl);
            userIdSaved = (Integer) session.save(u);
            tx.commit();
        } catch(HibernateException ex) {
            if(tx != null)
                tx.rollback();
            ex.printStackTrace();
        } finally {
            session.close();
        }
         
        return userIdSaved;
    }
         
	@Override
	public void start(Stage primaryStage) {
		Configuration config = new Configuration();
        config.configure();
        config.addAnnotatedClass(Rooms.class);
        config.addResource("User.hbm.xml");
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
        factory = config.buildSessionFactory(serviceRegistry);
         
        Main hbTest = new Main();
        hbTest.insertUser(1,412,"wysoko",4);
        
        
		try {
			
			Button btnButton=new Button();
			Connection con=getconnection();
			String queryimie = " select nr from pokoj";
			 Statement stm=con.createStatement();
			 
			 ResultSet result=stm.executeQuery(queryimie);
			 while(result.next())
			 {
			 System.out.print(result.getString("nr"));
			 
			 }
			
			 
			
			 Parent root=FXMLLoader.load(getClass().getResource("Uzytkownik.fxml"));
			Scene scene = new Scene(root,600,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	

	public static void main(String[] args)  {
		
		
		System.out.println("dziala");
		launch(args);
	}
}