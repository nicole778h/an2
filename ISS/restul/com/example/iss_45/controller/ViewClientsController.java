package com.example.iss_45.controller;

import com.example.iss_45.domain.Client;
import com.example.iss_45.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewClientsController {
    private Service service;

    public void setService(Service service){
        this.service = service;
        getClients();
    }

    ObservableList<Client> clients = FXCollections.observableArrayList();

    public void getClients(){
        // Set up the columns in the table
        idClm.setCellValueFactory(new PropertyValueFactory<Client, Integer>("id"));
        usernameClm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsername()));
        passwlm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPassword()));
        fnameClm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirst_name()));
        lnameClm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLast_name()));
        emailClm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        phoneClm.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhone_number()));

        // Load the data from the service
        clients.addAll(service.getAllClients());
        tabelClienti.setItems(clients);
    }

    @FXML
    private TableView<Client> tabelClienti;

    @FXML
    private TableColumn<Client, Integer> idClm;

    @FXML
    private TableColumn<Client, String> usernameClm;

    @FXML
    private TableColumn<Client, String> passwlm;

    @FXML
    private TableColumn<Client, String> fnameClm;

    @FXML
    private TableColumn<Client, String> lnameClm;

    @FXML
    private TableColumn<Client, String> emailClm;

    @FXML
    private TableColumn<Client, String> phoneClm;
}
