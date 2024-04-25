package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.domain.Spectacol;
import org.example.service.MainService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class MainController {
    @FXML
    private TableView<Spectacol> spectacolTable;
    @FXML
    private TextField searchDateField;

    @FXML
    private TableColumn<Spectacol, String> artistColumn;

    @FXML
    private TableColumn<Spectacol, String> locationColumn;

    @FXML
    private TableColumn<Spectacol, Integer> availableSeatsColumn;

    @FXML
    private TableColumn<Spectacol, LocalDate> dateColumn;

    @FXML
    private TableColumn<Spectacol, Integer> soldSeatsColumn;

    @FXML
    private TextField buyerNameField;

    @FXML
    private TextField numSeatsField;

    private MainService mainService;

    public MainController() {
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
        initializeTableView();
    }

    @FXML
    private void initialize() {
        // Inițializare
    }

    private void initializeTableView() {
        spectacolTable.getItems().clear();

        // Setarea CellValueFactory pentru fiecare coloană
        artistColumn.setCellValueFactory(new PropertyValueFactory<>("numeArtist"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numarLocDisponibile"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("loc"));
        soldSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numarLocVandute"));

        // Obținerea listei de spectacole din service
        List<Spectacol> allSpectacole = mainService.findAllSpectacole();

        // Convertirea listei obișnuite într-o ObservableList
        ObservableList<Spectacol> observableSpectacole = FXCollections.observableList(allSpectacole);

        // Adăugarea spectacolelor în TableView
        spectacolTable.setItems(observableSpectacole);

        // Setarea CellFactory pentru coloana de locuri disponibile
        availableSeatsColumn.setCellFactory(column -> new TableCell<Spectacol, Integer>() {
            @Override
            protected void updateItem(Integer locuri, boolean empty) {
                super.updateItem(locuri, empty);
                if (locuri == null || empty) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(String.valueOf(locuri));
                    if (locuri == 0) {
                        setText("SOLD OUT");
                        setTextFill(javafx.scene.paint.Color.RED);
                    }
                }
            }
        });
    }

    @FXML
    private void logout() {
        // Logica pentru logout
        // Redirectare către fereastra de autentificare sau închiderea aplicației
        // Creăm o nouă scenă pentru fereastra de autentificare (login)
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/LoginWindow.fxml"));
            Parent root = loader.load();

            // Obținem controlerul pentru fereastra de login
            LoginWindowController loginController = loader.getController();

            // Inițializăm serviciul pentru controlerul ferestrei de login
            loginController.setMainService(mainService);

            // Obținem stadiul actual și setăm o nouă scenă pentru login
            Stage stage = (Stage) spectacolTable.getScene().getWindow();
            stage.setScene(new Scene(root));

            // Închidem scena curentă (ferestra principală)
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void searchByDate() {
        String searchText = searchDateField.getText();
        if (!searchText.isEmpty()) {
            try {
                LocalDate searchDate = LocalDate.parse(searchText); // Convertim textul introdus într-un obiect LocalDate
                List<Spectacol> spectacole = mainService.findSpectacoleByDate(searchDate);
                refreshSpectacolTableWithData(spectacole); // Reîmprospătăm tabelul cu spectacolele găsite
            } catch (DateTimeParseException e) {
                // Tratează cazul în care textul introdus nu poate fi parsat într-o dată validă
                // De exemplu, afișează un mesaj de eroare că data introdusă nu este validă
                System.err.println("Data introdusă nu este validă!");
            }
        } else {
            // Tratează cazul în care câmpul de căutare pentru dată este gol
            // De exemplu, afișează un mesaj de eroare că câmpul de căutare este gol
            System.err.println("Introduceți o dată pentru căutare!");
        }
    }

    public void refreshSpectacolTable() {
        List<Spectacol> spectacole = mainService.findAllSpectacole();
        refreshSpectacolTableWithData(spectacole);
    }

    private void refreshSpectacolTableWithData(List<Spectacol> spectacole) {
        ObservableList<Spectacol> observableSpectacole = FXCollections.observableArrayList(spectacole);
        spectacolTable.getItems().setAll(observableSpectacole);
    }

    @FXML
    private void buyTickets() {
        String buyerName = buyerNameField.getText();
        int numSeats = Integer.parseInt(numSeatsField.getText());

        Spectacol selectedSpectacol = spectacolTable.getSelectionModel().getSelectedItem();
        if (selectedSpectacol != null) {
            if (numSeats <= selectedSpectacol.getNumarLocDisponibile()) {
                // Actualizăm numărul de locuri disponibile pentru spectacolul selectat
                selectedSpectacol.setNumarLocDisponibile(selectedSpectacol.getNumarLocDisponibile() - numSeats);

                // Actualizăm numărul de locuri vândute pentru spectacolul selectat
                selectedSpectacol.setNumarLocVandute(selectedSpectacol.getNumarLocVandute() + numSeats);

                mainService.updateSpectacol(selectedSpectacol);

                // Actualizăm tabela spectacolelor
                refreshSpectacolTable();
            } else {
                System.err.println("Numărul de locuri dorite este mai mare decât numărul de locuri disponibile!");
            }
        } else {
            System.err.println("Nu a fost selectat niciun spectacol!");
        }
    }

}
