package org.example.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.domain.Utilizator;
import org.example.service.MainService;
import org.example.Main;

import java.io.IOException;

public class LoginWindowController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label messageLabel;

    private MainService mainService;
    private Main main;

    public void setMainService(MainService mainService) {
        this.mainService = mainService;
    }

    public void setMain(Main main) {
        this.main = main;
    }
    @FXML
    private void initialize() {
        // Inițializarea tabelei cu spectacolele disponibile la început
     //   refreshSpectacolTable();
    }
    @FXML
    private void handleLoginButtonAction() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        Utilizator utilizator = mainService.findUtilizatorByUsername(username);

        if (utilizator != null && utilizator.getParola().equals(password)) {
            // Autentificare reușită, deschideți fereastra principală sau efectuați alte acțiuni necesare
            messageLabel.setText("Autentificare reusita");

            // Deschiderea ferestrei principale
            openMainWindow();
        } else {
            messageLabel.setText("Utilizator sau parolă incorecte");
        }

    }
    @FXML
    public void openMainWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
            Parent root = loader.load();

            // Obține controller-ul asociat ferestrei principale
            MainController mainController = loader.getController();

            // Setează service-ul principal în controller-ul ferestrei principale
            mainController.setMainService(mainService);


            // Creează o nouă scenă
            Scene scene = new Scene(root);

            // Obține stage-ul curent
            Stage stage = new Stage();

            // Setează scena și afișează stagiul
            //mainController.refreshSpectacolTable();

            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
