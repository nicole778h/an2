package com.example.iss_45.controller;

import com.example.iss_45.domain.Librarian;
import com.example.iss_45.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


import java.io.IOException;


public class LibrarianController {
    private Librarian librarian;
    public void setLibrarian(Librarian librarian){
        this.librarian = librarian;
    }

    private Service service;
    public void setService(Service service){
        this.service = service;
    }

    @FXML
    void clickAddBook(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/AddBookView.fxml"));
        Parent root = fxmlLoader.load();
        AddBookController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickAddClient(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/AddClientView.fxml"));
        Parent root = fxmlLoader.load();
        AddClientController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickDeleteBook(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/DeleteBookView.fxml"));
        Parent root = fxmlLoader.load();
        DeleteBookController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickDeleteClient(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/DeleteClientView.fxml"));
        Parent root = fxmlLoader.load();
        DeleteClientController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickUpdateClient(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/UpdateClientView.fxml"));
        Parent root = fxmlLoader.load();
        UpdateClientController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickViewBooks(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/ViewBooksView.fxml"));
        Parent root = fxmlLoader.load();
        ViewBooksController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void clickViewClients(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/ViewClientsView.fxml"));
        Parent root = fxmlLoader.load();
        ViewClientsController ctrl = fxmlLoader.getController();
        ctrl.setService(service);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

}