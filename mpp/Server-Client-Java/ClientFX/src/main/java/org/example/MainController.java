package org.example;

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
import org.example.Spectacol;
import org.example.rpcProtocol.RpcProxy;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TableColumn<Spectacol, LocalDateTime> dateColumn;

    @FXML
    private TableColumn<Spectacol, Integer> soldSeatsColumn;

    @FXML
    private TextField buyerNameField;

    @FXML
    private TextField numSeatsField;

    private MainService mainService;
    private IService server;
    private Utilizator utilizator; // Adăugăm membrul de tip Utilizator

    private RpcProxy rpcProxy; // Add RpcProxy instance
    public MainController() {
        rpcProxy = new RpcProxy("localhost", 1234);
        rpcProxy.initializeConnection();// Initialize RpcProxy instance with host and port
    }

    public void setServer(IService server) {
        this.server = server;
        initializeTableView();
    }

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
        initializeTableView();
    }

    public void setUtilizator(Utilizator utilizator) {
        this.utilizator = utilizator;
    }

    @FXML
    private void initialize() {
        // Inițializare
    }

    private void initializeTableView() {
        spectacolTable.getItems().clear();

        artistColumn.setCellValueFactory(new PropertyValueFactory<>("numeArtist"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("loc"));
        availableSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numarLocDisponibile"));
        soldSeatsColumn.setCellValueFactory(new PropertyValueFactory<>("numarLocVandute"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("data"));

        dateColumn.setCellFactory(column -> new TableCell<Spectacol, LocalDateTime>() {
            @Override
            protected void updateItem(LocalDateTime item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
                }
            }
        });

        refreshSpectacolTable();
    }

    @FXML
    private void logout() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/chatclientfx/LoginWindow.fxml"));
            Parent root = loader.load();

            LoginWindowController loginController = loader.getController();
            //loginController.setMainService(mainService); // Aici nu este necesar, dar ar putea fi folosit în altă parte

            Stage stage = (Stage) spectacolTable.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*  @FXML
      private void searchByDate() {
          String searchText = searchDateField.getText();
          if (!searchText.isEmpty()) {
              try {
                  LocalDate searchDate = LocalDate.parse(searchText);
                  List<Spectacol> spectacole = mainService.findSpectacoleByDate(searchDate);
                  refreshSpectacolTableWithData(spectacole);
              } catch (DateTimeParseException e) {
                  System.err.println("Data introdusă nu este validă!");
              }
          } else {
              System.err.println("Introduceți o dată pentru căutare!");
          }
      }*/
    @FXML
    private void searchByDate() {
        String searchText = searchDateField.getText();
        if (!searchText.isEmpty()) {
            try {
                LocalDate searchDate = LocalDate.parse(searchText);
                List<Spectacol> spectacole = server.findSpectacoleByDate(searchDate);
                refreshSpectacolTableWithData(spectacole);
            } catch (DateTimeParseException e) {
                System.err.println("Data introdusă nu este validă!");
            }
        } else {
            System.err.println("Introduceți o dată pentru căutare!");
        }
    }


    @FXML
    private void refreshTable() {
        refreshSpectacolTable();
    }

    public void refreshSpectacolTable() {
        //   List<Spectacol> spectacole = mainService.findAllSpectacole();
        // refreshSpectacolTableWithData(spectacole);


        List<Spectacol> spectacole;
        if (server != null) {
            spectacole = server.findAllSpectacole();
        } else {
            System.err.println("Server-ul nu este inițializat.");
            return;
        }
        refreshSpectacolTableWithData(spectacole);

    }

    /*private void refreshSpectacolTableWithData(List<Spectacol> spectacole) {
        ObservableList<Spectacol> observableSpectacole = FXCollections.observableArrayList(spectacole);
        spectacolTable.getItems().setAll(observableSpectacole);
    }*/
    private void refreshSpectacolTableWithData(List<Spectacol> spectacole) {
        if (spectacole != null) {
            ObservableList<Spectacol> observableSpectacole = FXCollections.observableArrayList(spectacole);
            spectacolTable.getItems().setAll(observableSpectacole);
        } else {
            System.err.println("Lista de spectacole este null.");
        }
    }

   /* @FXML
    private void buyTickets() {
        String buyerName = buyerNameField.getText();
        int numSeats = Integer.parseInt(numSeatsField.getText());

        Spectacol selectedSpectacol = spectacolTable.getSelectionModel().getSelectedItem();
        if (selectedSpectacol != null) {
            if (numSeats <= selectedSpectacol.getNumarLocDisponibile()) {
                selectedSpectacol.setNumarLocDisponibile(selectedSpectacol.getNumarLocDisponibile() - numSeats);
                selectedSpectacol.setNumarLocVandute(selectedSpectacol.getNumarLocVandute() + numSeats);

                mainService.updateSpectacol(selectedSpectacol);
                refreshSpectacolTable();
            } else {
                System.err.println("Numărul de locuri dorite este mai mare decât numărul de locuri disponibile!");
            }
        } else {
            System.err.println("Nu a fost selectat niciun spectacol!");
        }
    }*/
   @FXML
   private void buyTickets() {
       String buyerName = buyerNameField.getText();
       int numSeats = Integer.parseInt(numSeatsField.getText());

       Spectacol selectedSpectacol = spectacolTable.getSelectionModel().getSelectedItem();
       if (selectedSpectacol != null) {
           if (numSeats <= selectedSpectacol.getNumarLocDisponibile()) {
               selectedSpectacol.setNumarLocDisponibile(selectedSpectacol.getNumarLocDisponibile() - numSeats);
               selectedSpectacol.setNumarLocVandute(selectedSpectacol.getNumarLocVandute() + numSeats);

               // Update the Spectacol on the server
               server.updateSpectacol(selectedSpectacol);

               // Initialize RpcProxy connection
               rpcProxy.initializeConnection();

               // Purchase the tickets through RpcProxy
               // Pass a Long value for the last parameter
               Bilet bilet = new Bilet(selectedSpectacol.getId(), buyerName, numSeats, selectedSpectacol.getId());
               rpcProxy.buyBilet(bilet);

               // Refresh the table after successful purchase
               refreshSpectacolTable();
           } else {
               System.err.println("Numărul de locuri dorite este mai mare decât numărul de locuri disponibile!");
           }
       } else {
           System.err.println("Nu a fost selectat niciun spectacol!");
       }
   }




    public static class DateTimeCellFactory extends TableCell<Spectacol, LocalDateTime>{
        @Override
        protected void updateItem(LocalDateTime item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
            } else {
                setText(item.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        }
    }
}