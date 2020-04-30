v/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package application;

import Interface.Changable;
import Interface.Resizable;
import entityclasses.OsobaZadanie;
import entityclasses.Osoby;
import entityclasses.OsobyProjekty;
import factories.FormattedDateValueFactory;
import java.math.BigInteger;
import java.net.URL;
import java.sql.CallableStatement;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;



/**
 * FXML Controller class
 *
 * @author HP
 */
public class ManageUsersController implements Initializable, Changable, Resizable {

    @FXML
    private Button btnAddUser;
    @FXML
    private AnchorPane APaneAddOrEdit;
    @FXML
    private AnchorPane APaneShowTable;
    @FXML
    private AnchorPane AnchorPaneMain;
    @FXML
    private Button btnSave;
    @FXML
    private TextField txtFName;
    @FXML
    private TextField txtFSurname;
    @FXML
    private TextField txtFLogin;
    @FXML
    private TextField txtFEmail;
    @FXML
    private ComboBox<String> comboStatus;
    @FXML
    private ComboBox<String> comboSex;
    @FXML
    private PasswordField passFPassword;
    @FXML
    private PasswordField passFPasswordAgain;
    @FXML
    private DatePicker dateBirthDate;
    @FXML
    private Button btnBack;
    @FXML
    private Label lblErrorEmial;
    @FXML
    private Label lblErrorLogin;
    @FXML
    private Label lblErrorPass;
    @FXML
    private Label lblIncorrectEmail;
    @FXML
    private TableView tableUsers;
    @FXML
    private TableColumn<Osoby, String> colName;
    @FXML
    private TableColumn<Osoby, String> colSurname;
    @FXML
    private TableColumn<Osoby, String> colStatus;
    @FXML
    private TableColumn<Osoby, String> colBirthDate;
    @FXML
    private TableColumn<Osoby, String> colSex;
    @FXML
    private TableColumn<Osoby, String> colEmial;
    @FXML
    private TableColumn colAction;

    private EntityManager entityManager;
    Alerts alerts = new Alerts();
    private Osoby user;
    SendEmail email = new SendEmail();
    private int help = 0;
    private ObservableList<Osoby> usersList;
    private Integer id;
    private MainWindowController mainWindowController;

    ObservableList<String> listStatus = FXCollections.observableArrayList(
            "Administrator",
            "Kucharz",
            "Sprzataczka"
    );
    ObservableList<String> listSex = FXCollections.observableArrayList(
            "Mężczyzna",
            "Kobieta"
    );

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        addListenerToField();
        closeEditOpenTable();
        entityManager = MyEntityManager.getInstance();
        taskOpenTable.start();

    }

    /**
     * inicjalizuje wstawienie wartości do tabeli i wstawia listener do tabeli
     */
    private void initializeTable() {
        usersList = getDataFromDataBase();
        CallableStatement statement = (CallableStatement) 
                entityManager.createStoredProcedureQuery("{call INSERT_MANAGEUSERS(?,?,?,?,?,?,?,?,?)}");
        colName.setCellValueFactory(new PropertyValueFactory<>("imie"));
        colSurname.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
        colBirthDate.setCellValueFactory(new FormattedDateValueFactory<>("dataUr", "dd/MM/yyyy"));
        colEmial.setCellValueFactory(new PropertyValueFactory<>("email"));
        colSex.setCellValueFactory(new PropertyValueFactory<>("plec"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("ranga"));

        tableUsers.setItems(usersList);
        setMouseListenerToTable();

        colAction.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Object, Boolean>, ObservableValue<Boolean>>() {
            @Override
            public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Object, Boolean> p) {
                return new SimpleBooleanProperty(p.getValue() != null);
            }
        });
        colAction.setCellFactory(new Callback<TableColumn<Object, Boolean>, TableCell<Object, Boolean>>() {
            @Override
            public TableCell<Object, Boolean> call(TableColumn<Object, Boolean> p) {
                return new ManageUsersController.ButtonCell(tableUsers);
            }
        });

    }

 
    private void setMouseListenerToTable() {
        tableUsers.setRowFactory(tv -> {
            TableRow<Osoby> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    user = row.getItem();
                    id = user.getIdOsoba();
                    clear();
                    setParametrsToField(user);
                    help = 1;
                    closeTableOpenEdit();
                }
            });
            return row;
        });
    }

    /**
     * zamyka wyśiwietlanie tabeli i otwiera dodawnie użytkownika
     *
     * @param event kliknięcie przycisku dodaj użytkownika
     */
    public void openAddUser(ActionEvent event) {
        help = 0;
        clear();
        txtFEmail.setEditable(true);
        txtFLogin.setEditable(true);
        passFPassword.setEditable(true);
        passFPasswordAgain.setEditable(true);
        closeTableOpenEdit();
    }

    /**
     * albo dadaje użytkownika albo dodaje jego dane
     *
     * @param event kliknięcie w przycisk zapisz
     */
    public void saveChanges(ActionEvent event) {

        if (help == 0) {
            if (getDataFromFields() != null) {
                try {
                    Osoby userAdd = getDataFromFields();
                    entityManager.getTransaction().begin();
                    entityManager.persist(userAdd);
                    entityManager.getTransaction().commit();
                    alerts.showInformationAlert("Pomyślnie dodano użytkownika");
                    String message = "Wiaj na podany adres zostało założone konto w programie ProjectManager. \n"
                            + "Dane twojego konta: \n" + userAdd.getImie() + "\n" + userAdd.getNazwisko() + "\nTwój login to:"
                            + userAdd.getLogin() + "\nTwoje hasło to:" + userAdd.getHaslo() + "\nŻyczymy miłego korzystania z naszej aplikacji.";
                    email.sendEmail(userAdd.getEmail(), "Założenie konata w ProjectManager", message);
                    clear();
                    taskRefreshTable.restart();
                } catch (PersistenceException ex) {
                    alerts.showErrorAlert(ex.getMessage());
                }
            }
        } else if (help == 1) {
            if (getDataFromFields() != null) {
                try {
                    Osoby userEdit = getDataFromFields();
                    userEdit.setIdOsoba(id);
                    entityManager.getTransaction().begin();
                    entityManager.merge(userEdit);
                    entityManager.getTransaction().commit();
                    alerts.showInformationAlert("Pomyślnie edytowano dane użytkownika");
                    taskRefreshTable.restart();
                } catch (PersistenceException ex) {
                    alerts.showErrorAlert(ex.getMessage());
                }
            }
        }

    }

    /**
     * Pobiera dane uzytkownika z pol
     *
     * @return zwraca uzytkownika
     */
    public Osoby getDataFromFields() {
        Osoby userData = new Osoby();
        LocalDate date = dateBirthDate.getValue();
        if (txtFName.getText().isEmpty() || txtFSurname.getText().isEmpty() || date == null
                || comboStatus.getSelectionModel().getSelectedItem() == null
                || comboSex.getSelectionModel().getSelectedItem() == null
                || txtFLogin.getText().isEmpty() || txtFEmail.getText().isEmpty()
                || passFPassword.getText().isEmpty() || passFPasswordAgain.getText().isEmpty() || lblIncorrectEmail.isVisible() == true
                || lblErrorEmial.isVisible() == true || lblErrorLogin.isVisible() == true || lblErrorPass.isVisible() == true) {
            alerts.showErrorAlert("Wszystkie pola muszą być poprawnie uzupełnione");
        } else if (passFPassword.getText().length() != 0 && passFPassword.getText().equals(passFPasswordAgain.getText())) {
            userData.setImie(txtFName.getText());
            userData.setNazwisko(txtFSurname.getText());
            userData.setRanga(comboStatus.getSelectionModel().getSelectedItem());
            Instant instant = date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
            userData.setDataUr(Date.from(instant));
            userData.setPlec(comboSex.getSelectionModel().getSelectedItem());
            userData.setLogin(txtFLogin.getText());
            userData.setEmail(txtFEmail.getText());
            userData.setHaslo(passFPassword.getText());
            return userData;
        } else {
            alerts.showErrorAlert("Podane hasła różnią się od siebie");
        }
        return null;

    }

    /**
     * czyści wszystkie pola
     */
    public void clear() {
        comboSex.setItems(listSex);
        comboStatus.setItems(listStatus);
        txtFName.setText("");
        txtFSurname.setText("");
        comboStatus.getSelectionModel().select(null);
        dateBirthDate.setValue(null);
        comboSex.getSelectionModel().select(null);
        txtFLogin.setText("");
        txtFEmail.setText("");
        passFPassword.setText("");
        passFPasswordAgain.setText("");
        lblErrorEmial.setVisible(false);
        lblErrorLogin.setVisible(false);
        lblIncorrectEmail.setVisible(false);
        txtFEmail.getStyleClass().remove("error");
        txtFLogin.getStyleClass().remove("error");
        passFPassword.getStyleClass().remove("error");
        passFPasswordAgain.getStyleClass().remove("error");
    }

    /**
     * wraca z edycji użytkownika do wyświetlania tabeli
     *
     * @param event klik w przycisk powrót
     */
    public void backToTable(ActionEvent event) {
        APaneAddOrEdit.setVisible(false);
        APaneShowTable.setVisible(true);
    }

    /**
     * sprawdza czy podany login istnieje już w bazie danych w przypadku
     * intenienia wyświetla błąd
     *
     * @param login
     */
    public void checkLoginInDatabase(String login) {
        try {
            txtFLogin.getStyleClass().remove("error");
            Osoby userLogin = (Osoby) entityManager.createQuery("select o from users o where o.login = ?1", Osoby.class)
                    .setParameter(1, login)
                    .getSingleResult();
            if (help == 1 && user.getLogin().equals(userLogin.getLogin())) {
                txtFLogin.getStyleClass().remove("error");
                lblErrorLogin.setVisible(false);
            } else {
                txtFLogin.getStyleClass().add("error");
                lblErrorLogin.setVisible(true);
            }
        } catch (PersistenceException e) {
            txtFLogin.getStyleClass().remove("error");
            lblErrorLogin.setVisible(false);
        }

    }

    /**
     * sprawdza czy podany adres email istnieje już w bazie danych w przypadku
     * intenienia wyświetla błąd
     *
     * @param email
     */
    public void checkEmailInDatabse(String email) {
        try {
            txtFEmail.getStyleClass().remove("errror");
            Osoby userEmail = (Osoby) entityManager.createQuery("select o from users o where o.email = ?1", Osoby.class)
                    .setParameter(1, email)
                    .getSingleResult();
            if (help == 1 && user.getEmail().equals(userEmail.getEmail())) {
                txtFEmail.getStyleClass().remove("error");
                lblErrorEmial.setVisible(false);
            } else {
                txtFEmail.getStyleClass().add("error");
                lblErrorEmial.setVisible(true);
            }
        } catch (PersistenceException e) {
            txtFEmail.getStyleClass().remove("error");
            lblErrorEmial.setVisible(false);
        }
    }

    /**
     * pobiera listę użytkowników z bazy danych
     *
     * @return zwraca listę użytkowników
     */
    private ObservableList<Osoby> getDataFromDataBase() {
        List<Osoby> temp;
        try {
            temp = entityManager.createNamedQuery("users.findAll", Osoby.class)
                    .getResultList();
            return FXCollections.observableList(temp);
        } catch (PersistenceException e) {
            alerts.showErrorAlert(e.getMessage());
            return null;
        }
    }

    /**
     * zadaie inicjalizujące metodę initializeTable()
     */
    final Service<Void> taskOpenTable = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() {
                    initializeTable();
                    return null;
                }
            };

        }

    };

    /**
     * zadanie inicjalizujące funkcję refreshTable()
     */
    final Service<Void> taskRefreshTable = new Service<Void>() {
        @Override
        protected Task<Void> createTask() {
            return new Task<Void>() {
                @Override
                public Void call() {
                    refreshTable();
                    return null;
                }
            };

        }

    };

    /**
     * wstawia parametry podanego usera do pól edycji
     *
     * @param user użytkownik
     */
    public void setParametrsToField(Osoby user) {
        Instant instant = Instant.ofEpochMilli(user.getDataUr().getTime());
        LocalDate birthDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault()).toLocalDate();
        txtFName.setText(user.getImie());
        txtFSurname.setText(user.getNazwisko());
        txtFEmail.setText(user.getEmail());
        txtFLogin.setText(user.getLogin());
        comboSex.getSelectionModel().select(user.getPlec());
        comboStatus.getSelectionModel().select(user.getRanga());
        dateBirthDate.setValue(birthDate);
        passFPassword.setText(user.getHaslo());
        passFPasswordAgain.setText(user.getHaslo());
    }

    /**
     * zamyka wyświetlanie tabeli i otwiera edycję użytkownika
     */
    public void closeTableOpenEdit() {
        if (help == 1) {
            txtFEmail.setEditable(false);
            txtFLogin.setEditable(false);
            passFPassword.setEditable(false);
            passFPasswordAgain.setEditable(false);
        } else {
            txtFEmail.setEditable(true);
            txtFLogin.setEditable(true);
            passFPassword.setEditable(true);
            passFPasswordAgain.setEditable(true);
        }
        APaneAddOrEdit.setVisible(true);
        APaneShowTable.setVisible(false);
    }

    /**
     * zamyka edycje użytkownika i wyświetla tabelę
     */
    public void closeEditOpenTable() {
        clear();
        APaneAddOrEdit.setVisible(false);
        APaneShowTable.setVisible(true);
    }

    /**
     * usuwa dane z tabeli i ponownie je wstawia
     */
    public void refreshTable() {
        usersList = getDataFromDataBase();
        tableUsers.getItems().clear();
        tableUsers.getItems().addAll(usersList);
    }

    /**
     * Ustawia odpowiedni rozmiar
     */
    @Override
    public void setSize() {
        AnchorPaneMain.setPrefSize(mainWindowController.getCenterPaneWidth(),
                mainWindowController.getCenterPaneHeight());

        colAction.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(1));
        colName.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(2));
        colSurname.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(2));
        colStatus.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(1));
        colBirthDate.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(1));
        colSex.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(1));
        colEmial.prefWidthProperty().bind(tableUsers.widthProperty().divide(10).multiply(2));

    }

    /**
     * wstawia odnośniki do przycisków w tabeli
     */
    private class ButtonCell extends TableCell<Object, Boolean> {

        final Hyperlink cellButtonDelete = new Hyperlink("Delete");
        final Hyperlink cellButtonEdit = new Hyperlink("Edit");
        final HBox hb = new HBox(cellButtonDelete, cellButtonEdit);

        ButtonCell(final TableView tblView) {
            hb.setSpacing(4);
            cellButtonDelete.setOnAction((ActionEvent t) -> {

                int row = getTableRow().getIndex();
                tableUsers.getSelectionModel().select(row);
                Osoby selectedUser = (Osoby) tableUsers.getSelectionModel().getSelectedItem();
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Czy na pewno chcesz usunąć użytkownika " + selectedUser.getImie() + " " + selectedUser.getNazwisko() + " ?");
                alert.initStyle(StageStyle.UTILITY);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    deleteUser(selectedUser);
                    taskRefreshTable.restart();
                }
            });
            cellButtonEdit.setOnAction((ActionEvent event) -> {

                int row = getTableRow().getIndex();
                tableUsers.getSelectionModel().select(row);
                user = usersList.get(row);
                id = user.getIdOsoba();
                setParametrsToField(user);
                help = 1;
                closeTableOpenEdit();

            });
        }

        /**
         * wstawia przyciski do tabeli
         *
         * @param t
         * @param empty
         */
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(hb);
            } else {
                setGraphic(null);
            }
        }

        /**
         * usuwa użytkownika
         *
         * @param usr
         */
        public void deleteUser(Osoby usr) {
            try {
                List<OsobaZadanie> userTask = entityManager.createNamedQuery("OsobaZadanie.findByIdOsoba", OsobaZadanie.class)
                        .setParameter("idOsoba", usr.getIdOsoba()).getResultList();
                for (OsobaZadanie oz : userTask) {
                    entityManager.getTransaction().begin();
                    entityManager.remove(oz);
                    entityManager.getTransaction().commit();
                }
                List<OsobyProjekty> userProjects = entityManager.createNamedQuery("OsobyProjekty.findByIdOsoba", OsobyProjekty.class)
                        .setParameter("idOsoba", usr.getIdOsoba()).getResultList();
                for (OsobyProjekty op : userProjects) {
                    entityManager.getTransaction().begin();
                    entityManager.remove(op);
                    entityManager.getTransaction().commit();
                }

                entityManager.getTransaction().begin();
                entityManager.remove(usr);
                entityManager.getTransaction().commit();
            } catch (PersistenceException e) {
                alerts.showErrorAlert(e.getMessage());
            }
        }

    }

    /**
     * wstawia rozmiar okna
     *
     * @param controller
     */
    @Override
    public void setMainController(MainWindowController controller) {
        mainWindowController = controller;
        setSize();
    }

    /**
     * wstawia listenery do pól czy poprawne dane są wpisane
     */
    public void addListenerToField() {
        txtFLogin.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkLoginInDatabase(txtFLogin.getText());

            }

        });

        txtFEmail.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                checkEmailInDatabse(txtFEmail.getText());
                if (txtFEmail.getText().contains("@") || txtFEmail.getText().length() == 0) {
                    lblIncorrectEmail.setVisible(false);
                    txtFEmail.getStyleClass().remove("error");
                } else {
                    lblIncorrectEmail.setVisible(true);
                    txtFEmail.getStyleClass().add("error");
                }
            }

        });

        passFPasswordAgain.textProperty().addListener(new ChangeListener<String>() {
            @Override

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (passFPassword.getText().length() != 0
                        && passFPassword.getText().equals(passFPasswordAgain.getText()) == false) {
                    lblErrorPass.setVisible(true);
                    passFPassword.getStyleClass().add("error");
                    passFPasswordAgain.getStyleClass().add("error");
                } else {
                    lblErrorPass.setVisible(false);
                    passFPassword.getStyleClass().removeAll("error");
                    passFPasswordAgain.getStyleClass().removeAll("error");
                }
            }

        });
        passFPassword.textProperty().addListener(new ChangeListener<String>() {
            @Override

            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (passFPassword.getText().length() == 0
                        && passFPassword.getText().equals(passFPasswordAgain.getText())) {
                    lblErrorPass.setVisible(false);
                    passFPassword.getStyleClass().removeAll("error");
                    passFPasswordAgain.getStyleClass().removeAll("error");
                }
            }

        });
    }

}
