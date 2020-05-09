package application;

import javafx.beans.binding.Bindings;
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
import javafx.scene.chart.PieChart;
import javafx.scene.chart.PieChart.Data;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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

import org.controlsfx.control.table.TableRowExpanderColumn;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;



public class ManagerController implements Initializable{

	
    @FXML
    private Button btnstats;
	@FXML
    private TableView<Reservation> guestTable;

    @FXML
    private TableColumn<Reservation, Guest> name;

    @FXML
    private TableColumn<Reservation,Bill> balance;

    public ObservableList <Reservation> list;
    public ObservableList <Reservation> lista;
    @FXML
    private Label profit;
    @FXML
    private PieChart piechart;
    
    @FXML
    private Button billls;
	 public void initialize(URL url, ResourceBundle rbl) {

		 	list=FXCollections.observableArrayList();
		 
	
			SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();	
		 	
			    Query query = session.createQuery("from Reservation");	
			    
			    List<Reservation>resr = query.list();	
			   TableRowExpanderColumn<Reservation> expanderColumn=new TableRowExpanderColumn<>(this::createEditor);
			    session.getTransaction().commit();
			    int id=0;
		    float cena=0;
		    for(int i=0;i<resr.size();i++)
		    {
		    	list.add(resr.get(i));
		    
		    	
		    }	
		    
				
		    name.setCellValueFactory(new PropertyValueFactory<Reservation, Guest>("guest"));
		    balance.setCellValueFactory(new PropertyValueFactory<Reservation, Bill>("bill"));
	    	
		 	guestTable.getColumns().addAll(expanderColumn);
	    	guestTable.setItems(list);
	    	float value=profit();
	    	profit.setText("£aczny przychod: "+String.valueOf(value));
	    	 btnstats.setOnAction((event) -> {

		 		    	
		 				stats(list);
		 				
		 			
		 		});
	    	 
	    
	    
	 }
	 
	 
	 private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Reservation> param) {
		 GridPane editor=new GridPane();
		 editor.setPadding(new Insets(10));
		 Reservation reservation=param.getValue();
		 if(!reservation.getBill().getServices().isEmpty()) {
		 for(int i=0;i<reservation.getBill().getServices().size();i++) {
			 editor.addRow(i, new Label(i+1+"."+reservation.getBill().getServices().get(i).getName()+" " +reservation.getBill().getServices().get(i).getPrice()+"zl"));
		 }
		 }
		 return editor;
		 
	 }
	 
	 public float profit()
	 {
		 
		 	SessionFactory sessionFactory=new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		 	Session session=sessionFactory.openSession();
		 	session.beginTransaction();	
		    Query query1 = session.createQuery("from Reservation");	
		    List<Reservation>reservations = query1.list();	
		    session.getTransaction().commit();
		    
		    float price=0;
	    
		 
	
	    	for(int i=0;i<reservations.size();i++)
		    	{
	    		for(int j=0;j<reservations.get(i).getBill().getServices().size();j++)
		    		{
	    				price+=reservations.get(i).getBill().getServices().get(j).getPrice();
		    
		    
		    		}
		  
		    	}
	    	return price;
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
	 public void stats(ObservableList<Reservation> list2) {
		 	guestTable.setVisible(false);
		 	profit.setVisible(false);
		 	btnstats.setVisible(false);
		 	
		 	piechart.setVisible(true);
		 	billls.setVisible(true);
		 	float cola=0;
		 	float piwo=0;
		 	float pizza=0;
		 	float sprzatanie=0;
		 	for(int i=0;i<list2.size();i++)
	    	{
    		for(int j=0;j<list2.get(i).getBill().getServices().size();j++)
	    		{
    				if(list2.get(i).getBill().getServices().get(j).getName().equals("cola"))
    				cola+=list2.get(i).getBill().getServices().get(j).getPrice();
    				if(list2.get(i).getBill().getServices().get(j).getName().equals("piwo"))
        			piwo+=list2.get(i).getBill().getServices().get(j).getPrice();
    				if(list2.get(i).getBill().getServices().get(j).getName().equals("pizza"))
            			pizza+=list2.get(i).getBill().getServices().get(j).getPrice();
    				if(list2.get(i).getBill().getServices().get(j).getName().equals("sprzatanie"))
            			sprzatanie+=list2.get(i).getBill().getServices().get(j).getPrice();
	    
	    
	    		}
	    	}
		 
		 	
			ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList( 
					   new PieChart.Data("Cola", cola), 
					   new PieChart.Data("Piwo", piwo),
					   new PieChart.Data("Pizza", pizza),
					   new PieChart.Data("Sprzatanie", sprzatanie)
					   ); 
			piechart.setData(pieChartData);
			piechart.setTitle("Wykres Finansowy Uslug");
			piechart.setLabelsVisible(true);
			pieChartData.forEach(data ->
	        data.nameProperty().bind(
	                Bindings.concat(
	                        data.getName(), " ", data.pieValueProperty(), " Zl"
	                )
	        )
	);
			
			billls.setOnAction((event) -> {

				guestTable.setVisible(true);
			 	profit.setVisible(true);
			 	btnstats.setVisible(true);
 				piechart.setVisible(false);
 				billls.setVisible(false);
 				
 			
 		});
	}
	
	 
}