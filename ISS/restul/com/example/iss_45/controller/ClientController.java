package com.example.iss_45.controller;


import com.example.iss_45.domain.Client;
import com.example.iss_45.service.Service;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;
import java.io.IOException;


public class ClientController {
    private Client client;
    public void setClient(Client client){
        this.client = client;
        welcomeLabel.setText("Welcome "+ client.getFirst_name() + " " + client.getLast_name());
    }

    private Service service;
    public void setService(Service service){
        this.service = service;
    }

    @FXML
    private Label welcomeLabel;

    @FXML
    void clickRentBook(MouseEvent event) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/RentBookView.fxml"));
        //Parent root = fxmlLoader.load();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/RentBookView.fxml"));
        Parent root = fxmlLoader.load();
        RentBookController controller = fxmlLoader.getController();
        controller.setService(service);
        controller.setClient(client);

        RentBookController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        ctrl.setClient(client);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickReturnBook(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/ReturnBooksView.fxml"));
        Parent root = fxmlLoader.load();
        ReturnBooksController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        ctrl.setClient(client);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickViewRentalHistory(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/ViewRentalHistoryView.fxml"));
        Parent root = fxmlLoader.load();
        ViewRentalHistoryController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        ctrl.setClient(client);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}