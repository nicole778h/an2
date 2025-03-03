package org.example;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LoginWindowController {

    private IService server;
    private Stage primaryStage;

    public void setServer(IService server) {
        this.server = server;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    public void handleLoginButtonAction() {
        if (passwordField.getText().isEmpty() || usernameField.getText().isEmpty()) {
            Alert message = new Alert(Alert.AlertType.INFORMATION);
            message.setTitle("ERROR!");
            message.setContentText("YOU MUST INPUT AN USERNAME AND A PASSWORD!");
            message.showAndWait();
        } else {
            String username = usernameField.getText();
            String password = passwordField.getText();

            // Autentificare în serviciu
            Utilizator utilizator = this.server.findUtilizatorByUsername(username);

            if (utilizator == null || !utilizator.getParola().equals(password)) {
                Alert message = new Alert(Alert.AlertType.INFORMATION);
                message.setTitle("ERROR!");
                message.setContentText("WRONG USERNAME OR PASSWORD!");
                message.showAndWait();
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainWindow.fxml"));
                    Scene scene = new Scene(loader.load());
                    MainController mainController = loader.getController();
                    mainController.setUtilizator(utilizator);
                    mainController.setServer(server);

                    Stage stage = new Stage();
                    stage.setTitle("Main Window");
                    stage.setScene(scene);
                    stage.show();

                    // Close login window
                    primaryStage.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
