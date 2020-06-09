package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.TableViewSelectionModel;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import net.bytebuddy.build.HashCodeAndEqualsPlugin.Sorted;

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

import javax.swing.JOptionPane;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import generator.PdfGenerator;
import generator.ReservationPdf;
import generator.UserPDF;

/**
 * Kontroler uzytkownika o uprawnieniach administratora(zarzadzanie kontami).
 * 
 */
public class AdminController implements Initializable {

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

	@FXML
	private TableView<User> accounts;

	@FXML
	private TableColumn<User, String> tablename;

	@FXML
	private TableColumn<User, String> tablenazwisko;

	@FXML
	private TableColumn<User, String> tablelogin;

	@FXML
	private Button btndelete;
	@FXML
	private Button btnedit;
	@FXML
	private Label find;

	@FXML
	private TextField textviewfind;

	@FXML
	private TableColumn<User, Job> tablezawod;
	public ObservableList<User> list;

	public void initialize(URL url, ResourceBundle rbl) {

		list = FXCollections.observableArrayList();

		setChoiceJobs();
		btncreate.setOnAction((event) -> {

			try {
				createUser(choicejob);

			} catch (Throwable e) {

				e.printStackTrace();
			}
		});

		editableCols();
		setTables();
		updateUser();

	}

	/**
	 * Metoda sluzaca do wylogywania uzytkownika
	 * 
	 * @param event zdarzenie
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
	 * Metoda wyswietlajaca w Tabeli pracownikow i ich zawodcy.
	 *
	 * {@value} list jest to lista uzytkownikow {@value} accounts jest to tabela
	 * wyswietlajaca pracownikow
	 *
	 */
	public void setTables() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		Query query = session.createQuery("from User");

		List<User> users = query.list();
		for (int i = 0; i < users.size(); i++) {
			list.add(users.get(i));
		}

		tablename.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
		tablenazwisko.setCellValueFactory(new PropertyValueFactory<User, String>("surname"));
		tablelogin.setCellValueFactory(new PropertyValueFactory<User, String>("login"));
		tablezawod.setCellValueFactory(new PropertyValueFactory<User, Job>("job"));

		accounts.setItems(list);

		session.close();
	}

	/**
	 * Metoda umozliwia modyfikacje poszczegolnych komorek w tabeli uzytkonikow
	 *
	 * {@value} jobs lista prac {@value}list jest to pracownikow
	 * 
	 */

	public void editableCols() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Job");
		List<Job> jobs = query.list();
		int id = 0;
		ObservableList list = FXCollections.observableArrayList();

		for (int i = 0; i < jobs.size(); i++) {

			list.addAll(jobs.get(i));
		}
		session.close();
		tablename.setCellFactory(TextFieldTableCell.forTableColumn());
		tablename.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setName(e.getNewValue());
		});

		tablenazwisko.setCellFactory(TextFieldTableCell.forTableColumn());
		tablenazwisko.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setSurname(e.getNewValue());
		});
		tablelogin.setCellFactory(TextFieldTableCell.forTableColumn());
		tablelogin.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setLogin(e.getNewValue());
		});

		tablezawod.setCellFactory(ChoiceBoxTableCell.forTableColumn(list));
		tablezawod.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setJob(e.getNewValue());
		});

		accounts.setEditable(true);

		tablenazwisko.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
			@Override
			public void handle(CellEditEvent<User, String> t) {
				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setSurname(t.getNewValue());
			}
		});

		tablename.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
			@Override
			public void handle(CellEditEvent<User, String> t) {
				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setName(t.getNewValue());
			}
		});

		tablelogin.setOnEditCommit(new EventHandler<CellEditEvent<User, String>>() {
			@Override
			public void handle(CellEditEvent<User, String> t) {
				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setLogin(t.getNewValue());
			}
		});
		tablezawod.setOnEditCommit(new EventHandler<CellEditEvent<User, Job>>() {
			@Override
			public void handle(CellEditEvent<User, Job> t) {
				((User) t.getTableView().getItems().get(t.getTablePosition().getRow())).setJob(t.getNewValue());
			}
		});

	}

	/**
	 * Metoda zapelniajaca choicebox zawodami dostepnymi w firmie
	 *
	 * {@value} jobs jest to lista zawodow {@value} choicejob jest to
	 * choiceBox,gdzie do wyboru mamy zawody
	 * 
	 */
	public void setChoiceJobs() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();
		Query query = session.createQuery("from Job");
		List<Job> jobs = query.list();
		int id = 0;

		for (int i = 0; i < jobs.size(); i++) {

			String lvl = jobs.get(i).getName();
			choicejob.getItems().add(lvl);
			choicejob.getValue();

		}
		session.close();
	}

	/**
	 * Metoda umozliwiajaca tworzenie Uzytkownika.
	 *
	 * {@value} choicejob jest to choiceBox z pracami do wyboru {@value} job obiekt
	 * klasy Job {@value} haslo textfield z haslem
	 * 
	 */
	public void createUser(ChoiceBox<String> choicejob) {

		String dane = choicejob.getValue();

		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
		Session session = sessionFactory.openSession();
		session.beginTransaction();

		String haslo = password.getText();
		String rhaslo = rpassword.getText();

		String imie = name.getText();
		String nazwisko = surname.getText();
		String logi = login.getText();
		Job job = session.get(Job.class, 1);
		session.getTransaction().commit();

		session.beginTransaction();
		Job job1 = session.get(Job.class, 2);
		session.getTransaction().commit();

		session.beginTransaction();
		Job job2 = session.get(Job.class, 6);
		session.getTransaction().commit();

		session.beginTransaction();
		Job job3 = session.get(Job.class, 3);
		session.getTransaction().commit();

		session.beginTransaction();

		if (haslo.equals(rhaslo)) {

			if (dane.equals("recepcjonista")) {
				User user = new User(imie, nazwisko, logi, haslo, job);
				session.save(user);
				session.getTransaction().commit();
				Alert as = new Alert(AlertType.NONE);
				as.setAlertType(AlertType.INFORMATION);
				as.setContentText("Uzytkownik dodany");
				as.getDialogPane().setPrefSize(200, 100);
				as.show();
				setTables();

			} else if (dane.equals("kucharz")) {

				User user = new User(imie, nazwisko, logi, haslo, job1);
				session.save(user);
				session.getTransaction().commit();
				Alert as = new Alert(AlertType.NONE);
				as.setAlertType(AlertType.INFORMATION);
				as.setContentText("Uzytkownik dodany");
				as.getDialogPane().setPrefSize(200, 100);
				as.show();
				setTables();
			} else if (dane.equals("sprzatacz")) {

				User user = new User(imie, nazwisko, logi, haslo, job2);
				session.save(user);
				session.getTransaction().commit();
				Alert as = new Alert(AlertType.NONE);
				as.setAlertType(AlertType.INFORMATION);
				as.setContentText("Uzytkownik dodany");
				as.getDialogPane().setPrefSize(200, 100);
				as.show();
				setTables();

			} else if (dane.equals("manager")) {

				User user = new User(imie, nazwisko, logi, haslo, job3);
				session.save(user);
				session.getTransaction().commit();
				Alert as = new Alert(AlertType.NONE);
				as.setAlertType(AlertType.INFORMATION);
				as.setContentText("Uzytkownik dodany");
				as.getDialogPane().setPrefSize(200, 100);
				as.show();
				setTables();

			}

		} else {
			Alert a1 = new Alert(Alert.AlertType.ERROR);
			a1.setContentText("Has³a sie nie zgadzaja");
			a1.setTitle("Blad");
			a1.setHeaderText(null);
			a1.show();
		}
		accounts.getItems().clear();
		setTables();
		session.close();
	}

	/**
	 * Metoda wyswietlajaca formatke z spisem pracownikow i potrzebnymi polami do
	 * tego
	 * 
	 * @param event dzialacy na przycisk
	 */
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
		back.setVisible(true);
		header.setVisible(false);
		accounts.setVisible(true);
		btndelete.setVisible(true);
		btnedit.setVisible(true);
		textviewfind.setVisible(true);
		find.setVisible(true);
	}

	/**
	 * Metoda wyswietlajaca formatke z tworzeniem konta i potrzebnymi polami do
	 * tego.
	 * 
	 * @param event dzialacy na przycisk
	 */
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
		accounts.setVisible(false);
		btndelete.setVisible(false);
		btnedit.setVisible(false);
		textviewfind.setVisible(false);
		find.setVisible(false);

	}

	/**
	 * Metoda umozliwiajaca usuwanie pracownika.
	 * 
	 * @param event dzialacy na przycisk {@value} selectedRow lista wybranych
	 *              wierszow Uzytkownikow {@value} allPeople tabela wszystkich
	 *              pracownikow {@value} haslo textfield z haslem
	 * 
	 */
	@FXML
	void deleteuser(ActionEvent event) {
		int a = JOptionPane.showConfirmDialog(null, "Czy Napewno?", "Ostrzezenie", JOptionPane.YES_NO_CANCEL_OPTION);
		if (a == 0) {

			ObservableList<User> selectedRow, allPeople;
			allPeople = accounts.getItems();
			selectedRow = accounts.getSelectionModel().getSelectedItems();
			SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();
			Session session = sessionFactory.openSession();
			session.beginTransaction();
			for (User user : selectedRow) {

				User users = session.load(User.class, user.getId());
				session.delete(users);
				session.getTransaction().commit();
				Alert as = new Alert(AlertType.NONE);
				as.setAlertType(AlertType.INFORMATION);
				as.setContentText("Uzytkownik usuniety");
				as.setHeaderText("Wiadomosc");
				as.getDialogPane().setPrefSize(200, 100);
				as.show();
			}

			accounts.getItems().removeAll(accounts.getSelectionModel().getSelectedItem());
			accounts.getItems().clear();
			setTables();
		}
	}

	/**
	 * Metoda umozliwiajaca szukanie pracownika poprzez textfield(filtr).
	 * 
	 * @param event dzialacy na przycisk {@value} textviewfind textField do
	 *              wyszukiwania pracownikow {@value} filteredData sflitrowana lista
	 *              pracownikow {@value} sortedList sortowana lista filtrowanych
	 *              pracownikow
	 * 
	 */
	@FXML
	void search(KeyEvent event) {
		FilteredList<User> filteredData = new FilteredList<>(list, p -> true);
		textviewfind.textProperty().addListener((obsevable, oldvalue, newvalue) -> {
			filteredData.setPredicate(pers -> {
				if (newvalue == null || newvalue.isEmpty()) {
					return true;
				}
				String typedText = newvalue.toLowerCase();
				if (pers.getName().toLowerCase().indexOf(typedText) != -1) {
					return true;
				}
				if (pers.getSurname().toLowerCase().indexOf(typedText) != -1) {
					return true;
				}
				if (pers.getLogin().toLowerCase().indexOf(typedText) != -1) {
					return true;
				}
				if (pers.getJob().getName().toLowerCase().indexOf(typedText) != -1) {
					return true;
				}
				return false;
			});
			SortedList<User> sortedList = new SortedList<>(filteredData);
			sortedList.comparatorProperty().bind(accounts.comparatorProperty());
			accounts.setItems(sortedList);
		});

	}

	/**
	 * Metoda umozliwiajaca aktualizowanie Uzytkownika. {@value} btnedit przycisk do
	 * edytowania {@value} obiekt klasy User,ktory jest aktualizowany
	 * 
	 * 
	 */
	public void updateUser() {
		SessionFactory sessionFactory = new Configuration().configure("hibernate.cfg.xml").buildSessionFactory();

		btnedit.setOnAction((event) -> {

			ObservableList<User> selectedRow, allPeople;
			allPeople = accounts.getItems();
			selectedRow = accounts.getSelectionModel().getSelectedItems();
			Session session1 = sessionFactory.openSession();
			session1.beginTransaction();
			for (User user : selectedRow) {
				btnedit.setOnAction((event1) -> {

					User s = new User(user.getId(), user.getName(), user.getSurname(), user.getLogin(),
							user.getPassword(), user.getJob());

					session1.merge(s);
					session1.getTransaction().commit();
					session1.beginTransaction();
					Alert as = new Alert(AlertType.NONE);
					as.setAlertType(AlertType.INFORMATION);
					as.setContentText("Uzytkownik uaktualniony");
					as.getDialogPane().setPrefSize(200, 100);
					as.show();

				});
			}

		});
	}

}