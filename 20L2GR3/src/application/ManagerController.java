package application;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import javafx.scene.input.KeyEvent;
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
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.persistence.criteria.Root;

import org.controlsfx.control.table.TableRowExpanderColumn;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import generator.PdfGenerator;
import generator.ReservationPdf;
import generator.UserPDF;

/**
 * Kontroler obslugujacy Managera
 */
public class ManagerController implements Initializable {

	@FXML
	private Button btnstats;
	@FXML
	private TableView<Reservation> guestTable;
	@FXML
	private TextField textfieldtofind;
	@FXML
	private Label lblfind;
	@FXML
	private TableColumn<Reservation, Guest> name;

	@FXML
	private TableColumn<Reservation, Bill> balance;
	@FXML
	private TableView<Item> reservationRooms;

	@FXML
	private TableColumn<Item, Rooms> numberRoom;
	@FXML
	private TableColumn<Item, Long> countedreservation;
	@FXML
	private Button statstobills;

	public ObservableList<Reservation> list;
	public ObservableList<Reservation> lista;

	@FXML
	private Label profit;
	@FXML
	private PieChart piechart;
	@FXML
	private Button swapforcount;
	@FXML
	private Button billls;
	@FXML
	private Button pdfGenerate;
	public ObservableList<Item> counts;

	/**
	 * Metoda Initalize po uruchomieniu kontrolera w tabeli wyswietla sie lista
	 * gosci z ich rachunkami
	 */
	public void initialize(URL url, ResourceBundle rbl) {

		list = FXCollections.observableArrayList();
		guest();

		float value = profit();
		profit.setText("Laczny przychod: " + String.valueOf(value));
		btnstats.setOnAction((event) -> {

			stats(list);

		});

	}

	/**
	 * Metoda wyswietlajaca tabelke gosci z ich rachunkiem
	 * 
	 * @param guestTable tabela z gosciami
	 * @param resr       lista rezerwacji(bill)
	 */
	public void guest() {
		btnstats.setVisible(true);
		swapforcount.setVisible(true);
		billls.setVisible(false);
		statstobills.setVisible(false);
		reservationRooms.setVisible(false);
		guestTable.setVisible(true);
		piechart.setVisible(false);
		pdfGenerate.setVisible(true);

		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from Reservation");

		List<Reservation> resr = query.list();
		TableRowExpanderColumn<Reservation> expanderColumn = new TableRowExpanderColumn<>(this::createEditor);
		session.getTransaction().commit();
		int id = 0;
		float cena = 0;
		for (int i = 0; i < resr.size(); i++) {
			list.add(resr.get(i));

		}

		name.setCellValueFactory(new PropertyValueFactory<Reservation, Guest>("guest"));
		balance.setCellValueFactory(new PropertyValueFactory<Reservation, Bill>("bill"));

		guestTable.getColumns().addAll(expanderColumn);
		guestTable.setItems(list);
	}

	/**
	 * Metoda sluzaca do ustawienia i wyswietlenia z pokojami i liczba rezerwacji
	 * danego pokoju
	 * 
	 * @param employees   lista obiektow zwracanych przez kwerende
	 * @param pomocnicza2 obiekt klasy Item,sluzacy do wyswietlenia w tableview
	 */
	public void setTable() {
		guestTable.setVisible(false);
		reservationRooms.setVisible(true);
		swapforcount.setVisible(false);
		piechart.setVisible(false);
		statstobills.setVisible(false);
		billls.setVisible(true);
		piechart.setVisible(false);
		btnstats.setVisible(true);
		lblfind.setVisible(true);
		textfieldtofind.setVisible(true);
		pdfGenerate.setVisible(true);
		counts = FXCollections.observableArrayList();
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("select rk.room,count(rk.id) from Reservation as rk group by rk.room");

		List<Object[]> employees = (List<Object[]>) query.list();

		for (Object[] employee : employees) {

			// employee[0]
			// employee[1]
			Rooms number = (Rooms) employee[0];
			long count = (long) employee[1];

			System.out.println(number);
			System.out.println(count);
			Item pomocnicza2 = new Item(number, count);

			counts.add(pomocnicza2);

		}

		session.getTransaction().commit();

		numberRoom.setCellValueFactory(new PropertyValueFactory<Item, Rooms>("number"));
		countedreservation.setCellValueFactory(new PropertyValueFactory<Item, Long>("count"));

		reservationRooms.setItems(counts);

		session.close();

	}

	/**
	 * Metoda rozszerzajaca dane o rachunku uzytkownika(pokazuje co dokladnie kupil
	 * i za ile)
	 * 
	 * @param reservation obiekt klasy Reservation
	 * @param editor      to GridPane
	 */
	private GridPane createEditor(TableRowExpanderColumn.TableRowDataFeatures<Reservation> param) {
		GridPane editor = new GridPane();
		editor.setPadding(new Insets(10));
		Reservation reservation = param.getValue();
		if (!reservation.getBill().getServices().isEmpty()) {
			for (int i = 0; i < reservation.getBill().getServices().size(); i++) {
				editor.addRow(i, new Label(i + 1 + "." + reservation.getBill().getServices().get(i).getName() + " "
						+ reservation.getBill().getServices().get(i).getPrice() + "zl"));
			}
		}
		return editor;

	}

	/**
	 * Metoda wyliczajaca laczny przychod hotelu
	 * 
	 * @param reservations lista rezerwacji
	 * @param price        zmienna typu float przechowujaca laczny przychod firmy
	 */
	public float profit() {

		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query1 = session.createQuery("from Reservation");
		List<Reservation> reservations = query1.list();
		session.getTransaction().commit();

		float price = 0;

		for (int i = 0; i < reservations.size(); i++) {
			for (int j = 0; j < reservations.get(i).getBill().getServices().size(); j++) {
				price += reservations.get(i).getBill().getServices().get(j).getPrice();

			}

		}
		return price;
	}

	/**
	 * Metoda sluzaca do wylogywania uzytkownika
	 * 
	 * @param event zdarzenie obslugiwane przez przycisk
	 */
	@FXML
	void wyloguj(ActionEvent event) throws Exception {
		Parent application = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Scene applicationScene = new Scene(application);
		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		applicationScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		window.setScene(applicationScene);
		window.show();

	}

	/**
	 * Metoda wyswietlajaca formatke z tabela Gosci i ich rachunkami
	 * 
	 */
	public void screenGuest() {
		btnstats.setVisible(true);
		swapforcount.setVisible(true);
		billls.setVisible(false);
		statstobills.setVisible(false);
		reservationRooms.setVisible(false);
		guestTable.setVisible(true);
		piechart.setVisible(false);
		pdfGenerate.setVisible(true);

	}

	/**
	 * Metoda sluzaca do filtrowania w tabeli pokoju/liczby jego rezerwacji
	 * 
	 * @param event           dziala na wpisanie cos w textFieldzie
	 * @param textfieldtofind to TextField do wyszukiwaniwa pokoju/liczby jego
	 *                        rezerwacji na nim jest event
	 * @param sortedList      posortowana lista obiekow klasy Item w Tabeli.
	 */
	@FXML
	void search(KeyEvent event) {
		FilteredList<Item> filteredData = new FilteredList<>(counts, p -> true);
		textfieldtofind.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
			filteredData.setPredicate(pers -> {
				if (newvalue == null || newvalue.isEmpty()) {
					return true;
				}
				String typedText = newvalue.toLowerCase();
				long count = pers.getCount();
				String counter = String.valueOf(count);
				String number = String.valueOf(pers.getNumber().getRoomNumber());
				if (counter.toLowerCase().indexOf(typedText) != -1) {
					return true;
				}
				if (number.toLowerCase().indexOf(typedText) != -1) {
					return true;
				}

				return false;
			});
			SortedList<Item> sortedList = new SortedList<>(filteredData);
			sortedList.comparatorProperty().bind(reservationRooms.comparatorProperty());
			reservationRooms.setItems(sortedList);
		});

	}

	/**
	 * Metoda uruchumiajaca formatke ze statystykami
	 * 
	 * @param pieChartData wykres kolowy, opisujacy przychody za okreslone uslugi
	 * @param list2        jest to lista rezerwacji
	 * 
	 */
	public void stats(ObservableList<Reservation> list2) {
		guestTable.setVisible(false);
		reservationRooms.setVisible(false);
		profit.setVisible(false);
		btnstats.setVisible(false);
		swapforcount.setVisible(true);
		statstobills.setVisible(true);
		piechart.setVisible(true);
		billls.setVisible(false);
		statstobills.setVisible(true);
		lblfind.setVisible(false);
		textfieldtofind.setVisible(false);
		pdfGenerate.setVisible(false);

		float cola = 0;
		float piwo = 0;
		float pizza = 0;
		float sprzatanie = 0;
		for (int i = 0; i < list2.size(); i++) {
			for (int j = 0; j < list2.get(i).getBill().getServices().size(); j++) {
				if (list2.get(i).getBill().getServices().get(j).getName().equals("cola"))
					cola += list2.get(i).getBill().getServices().get(j).getPrice();
				if (list2.get(i).getBill().getServices().get(j).getName().equals("piwo"))
					piwo += list2.get(i).getBill().getServices().get(j).getPrice();
				if (list2.get(i).getBill().getServices().get(j).getName().equals("pizza"))
					pizza += list2.get(i).getBill().getServices().get(j).getPrice();
				if (list2.get(i).getBill().getServices().get(j).getName().equals("sprzatanie"))
					sprzatanie += list2.get(i).getBill().getServices().get(j).getPrice();

			}
		}

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(new PieChart.Data("Cola", cola),
				new PieChart.Data("Piwo", piwo), new PieChart.Data("Pizza", pizza),
				new PieChart.Data("Sprzatanie", sprzatanie));
		piechart.setData(pieChartData);
		piechart.setTitle("Wykres Finansowy Uslug");
		piechart.setLabelsVisible(true);
		pieChartData.forEach(
				data -> data.nameProperty().bind(Bindings.concat(data.getName(), " ", data.pieValueProperty(), " Zl")));

	}

	/**
	 * Metoda generujaca plik pdf ze statystykami
	 * 
	 * @param generator obiekt klasy PdfGenerator
	 * @param users     jest to lista pracownikow
	 * 
	 */
	public void generatePdf(ActionEvent event) {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from User");

		List<User> users = query.list();
		PdfGenerator generator = new PdfGenerator();
		List<UserPDF> userpdfList = new ArrayList<UserPDF>();
		for (int i = 0; i < users.size(); i++) {
			UserPDF newuser = new UserPDF(users.get(i).id, users.get(i).name, users.get(i).surname, users.get(i).login);
			userpdfList.add(newuser);
		}

		Query queryTwo = session.createQuery("from Reservation");
		List<Reservation> reservations = queryTwo.list();
		List<ReservationPdf> reservationpdflist = new ArrayList<ReservationPdf>();
		for (int i = 0; i < reservations.size(); i++) {
			ReservationPdf newreservation = new ReservationPdf(reservations.get(i).getGuest().getName(),
					reservations.get(i).getGuest().getSurname(), reservations.get(i).getBill().totalbill(),
					reservations.get(i).getDates().toString(), reservations.get(i).getEndDate().toString());
			reservationpdflist.add(newreservation);
		}

		float value = profit();

		generator.userListPdf(userpdfList, reservationpdflist, value);

	}

}