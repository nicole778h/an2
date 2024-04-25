package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.controller.LoginWindowController;
import org.example.controller.MainController;
import org.example.repository.BiletRepository;
import org.example.repository.SpectacolRepository;
import org.example.repository.UtilizatorRepository;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.example.service.MainService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main extends Application {
    private static final Logger logger = LogManager.getLogger(Main.class);
    private Stage mainWindowStage;
    private MainService service;
    private static Main mainInstance; // Adăugați această linie

    public static Main getInstance() {
        return mainInstance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainInstance = this;

        // Inițializarea și configurarea obiectului MainService și a dependențelor sale
        BiletRepository biletRepository = new BiletRepository();
        SpectacolRepository spectacolRepository = new SpectacolRepository();
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository();
        service = new MainService(biletRepository, spectacolRepository, utilizatorRepository);

        // Încărcarea ferestrei de autentificare și setarea obiectului MainService în controller
        FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/LoginWindow.fxml"));
        Parent loginRoot = loginLoader.load();
        LoginWindowController loginController = loginLoader.getController();
        loginController.setMainService(service);
        loginController.setMain(this);

        // Afisarea ferestrei de autentificare
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(loginRoot, 320, 240));
        primaryStage.show();
    }



    // Metoda pentru a deschide fereastra principală
   /* public void openMainWindow() {
        mainWindowStage = new Stage();
        try {
            //Parent mainWindowRoot = FXMLLoader.load(getClass().getResource("/resources/MainWindow.fxml"));
            //Parent mainWindowRoot = FXMLLoader.load(getClass().getResource("/nou.fxml"));

            MainController mainWindowRoot = FXMLLoader.load(getClass().getResource("C:\\Users\\Galdeanu\\Desktop\\facultate\\an2\\mpp_labs\\lab3\\src\\main\\resources\\MainWindow.fxml"));

            // Parent mainWindowRoot = FXMLLoader.load(getClass().getResource("resources/MainWindow.fxml"));
            //mainWindowStage.setTitle("Main Window");
            //mainWindowStage.setScene(new Scene(mainWindowRoot, 800, 600));
            //mainWindowStage.show();
        } catch (IOException e) {
            logger.error("Error loading MainWindow.fxml: {}", e.getMessage());
        }
    }*/



    // Metoda pentru a închide fereastra principală
    public void closeMainWindow() {
        if (mainWindowStage != null) {
            mainWindowStage.close();
        }
    }

    public static void main(String[] args) {
        testConnection();
        testRepositories();
        launch(args);
    }

    // Restul codului


    private static void testConnection() {
        String url = "jdbc:postgresql://localhost:5432/spectacol";
        String username = "postgres";
        String password = "1234";

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            logger.info("Conexiunea la baza de date PostgreSQL s-a realizat cu succes!");
        } catch (SQLException e) {
            logger.error("Eroare la conectarea la baza de date PostgreSQL: {}", e.getMessage());
        }
    }

    private static void testRepositories() {
        // Testarea repository-ului pentru Utilizatori
        UtilizatorRepository utilizatorRepository = new UtilizatorRepository();
        logger.info("Utilizatori din baza de date:");
        utilizatorRepository.find_all().forEach(utilizator -> logger.info(utilizator.getUsername()));

        // Testarea repository-ului pentru Spectacole
        SpectacolRepository spectacolRepository = new SpectacolRepository();
        logger.info("\nSpectacole din baza de date:");
        spectacolRepository.find_all().forEach(spectacol -> logger.info("{} - {}", spectacol.getData(), spectacol.getNumeArtist()));

        // Testarea repository-ului pentru Bilete
        BiletRepository biletRepository = new BiletRepository();
        logger.info("\nBilete din baza de date:");
        biletRepository.find_all().forEach(bilet -> logger.info("{} - {}", bilet.getNumeCumparator(), bilet.getNumarLocSelectate()));
    }
}
