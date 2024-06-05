package com.example.iss_45.controller;


import com.example.iss_45.service.Service;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class DeleteBookController {
    private Service service;

    public void setService(Service service) {
        this.service = service;
    }


    @FXML
    private TextField txtId;

    @FXML
    void clickDeleteBook(MouseEvent event) throws IOException {
        if(Objects.equals(txtId.getText(), "")){
            // Afiseaza un mesaj de eroare daca campul este gol
            displayErrorMessage("Introduceti id-ul!");
        } else {
            try {
                int idDel = Integer.parseInt(txtId.getText());
                // Daca conversia la int este reusita, sterge cartea
                service.deleteBook(idDel);
                displaySuccessMessage("Cartea a fost stearsa!");
                // Inchide fereastra curenta
                closeWindow();
            } catch (NumberFormatException e) {
                // Afiseaza un mesaj de eroare daca id-ul nu este numeric
                displayErrorMessage("Id-ul trebuie sa fie numeric!");
            }
        }
    }

    private void displayErrorMessage(String message) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/example/iss_45/Message.fxml"));
        Parent root = fxmlLoader.load();
        MessageController ctrl = fxmlLoader.getController();
        ctrl.setMessage(message);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    private void displaySuccessMessage(String message) throws IOException {
        displayErrorMessage(message);
    }

    private void closeWindow() {
        Stage st = (Stage) txtId.getScene().getWindow();
        st.close();
    }


}