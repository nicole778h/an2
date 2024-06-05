package com.example.iss_45;

import com.example.iss_45.controller.LoginController;
import com.example.iss_45.repository.orm.*;
import com.example.iss_45.service.Service;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainFX extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Inițializare repository-uri
        BookRepository bookRepository = new BookRepository();
        ClientRepository clientRepository = new ClientRepository();
        LibrarianRepository librarianRepository = new LibrarianRepository();
        BookRentalRepository bookRentalRepository = new BookRentalRepository();

        // Inițializare serviciu
        Service service = new Service(bookRepository, clientRepository, librarianRepository, bookRentalRepository);

        try {
            // Încărcare interfață grafică și setare controler
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/iss_45/LoginView.fxml"));
            Parent root = loader.load();
            LoginController ctrl = loader.getController();
            ctrl.setService(service);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Hello");
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        // Închide conexiunea la baza de date la închiderea aplicației
        HibernateSession.close();
    }
}
