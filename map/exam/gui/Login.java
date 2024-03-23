package com.example.exam.gui;

import com.example.exam.HelloApplication;
import com.example.exam.domain.Client;
import com.example.exam.service.ClientService;
import com.example.exam.service.FlightService;
import com.example.exam.service.TicketService;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Login {


    public Label errorLabel;
    public TextField usernameTextField;
    private ClientService clientService;
    private FlightService flightService;
    private TicketService ticketService;
// verifică dacă câmpul de introducere a username-ului este gol
// și afișează o eroare în consecință. În caz contrar, obține un Client
    public void loginHandle() throws IOException {
        if (usernameTextField.getText().isEmpty()) {
            errorLabel.setVisible(true);
            errorLabel.setText("You did not introduce an username");
        } else {
            String username = usernameTextField.getText();
            Client client = clientService.findOne(username);
            if (client == null) {
                errorLabel.setVisible(true);
                errorLabel.setText("You are not registered as a client");
            } else {
                client.setId(username);
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("table-window.fxml"));
                Scene scene = new Scene(fxmlLoader.load());

                FlightTableController ctr = fxmlLoader.getController();
                ctr.setAll(flightService, ticketService, client);

                Stage stage = new Stage();
                stage.setScene(scene);

                stage.setTitle(client.getName());
                stage.show();

                closeWindow();
            }
        }
    }

    public void setService(ClientService clientService, FlightService flightService, TicketService ticketService) {
        this.clientService = clientService;
        this.flightService = flightService;
        this.ticketService = ticketService;
    }

    private void closeWindow() {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }
//metodă apelată atunci când utilizatorul dorește să înceapă o nouă sesiune.
// Aceasta încarcă o nouă fereastră de autentificare
    public void newSessionRequest() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Login login = fxmlLoader.getController();
        login.setService(clientService, flightService, ticketService);
        Stage stage = new Stage();
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }
}