package com.example.exam;

import com.example.exam.gui.Login;
import com.example.exam.repository.ClientDbRepository;
import com.example.exam.repository.FlightDbRepository;
import com.example.exam.repository.TicketDbRepository;
import com.example.exam.service.ClientService;
import com.example.exam.service.FlightService;
import com.example.exam.service.TicketService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private static String[] sysArgs;
    private ClientService clientService;
    private FlightService flightService;
    private TicketService ticketService;

    private void setup() {
        String urlDb = "jdbc:postgresql://localhost:5432/zboruri";
        String usernameDb = "postgres";
        String passDb = "1234";
        ClientDbRepository clientDbRepository = new ClientDbRepository(urlDb, usernameDb, passDb);
        FlightDbRepository flightDbRepository = new FlightDbRepository(urlDb, usernameDb, passDb);
        TicketDbRepository ticketDbRepository = new TicketDbRepository(urlDb, usernameDb, passDb);
        clientService = new ClientService(clientDbRepository);
        flightService = new FlightService(flightDbRepository);
        ticketService = new TicketService(ticketDbRepository);
    }
    @Override
    public void start(Stage stage) throws IOException {
        setup();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/exam/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Login login = fxmlLoader.getController();
        login.setService(clientService, flightService, ticketService);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        sysArgs = args;
        launch();
    }
}