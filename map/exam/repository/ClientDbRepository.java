package com.example.exam.repository;

import com.example.exam.domain.Client;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDbRepository implements Repository<String, Client> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;
//constructor
    public ClientDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }
//caută un client după numele de utilizator
    //Rezultatul este mapat într-un obiect Client și returnat.
    @Override
    public Client findOne(String username) {
        Client client = null;
        String sql = "SELECT * FROM clients WHERE username = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString("client_name");
                String storedUsername = resultSet.getString("username");
                client = new Client(name, storedUsername);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratează excepția într-un mod adecvat în aplicația ta
        }
        return client;
    }

    @Override
    //returnează o listă cu toți clienții din baza de date.
    public List<Client> findAll() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM clients";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(sql);
        ) {
            while (resultSet.next()) {
                String name = resultSet.getString("client_name");
                String storedUsername = resultSet.getString("username");
                clients.add(new Client(name, storedUsername));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Tratează excepția într-un mod adecvat în aplicația ta
        }
        return clients;
    }

    @Override
    public Client save(Client entity) {
        // Implementează logica de salvare în baza de date
        return null;
    }

    @Override
    public Client delete(String username) {
        // Implementează logica de ștergere în baza de date
        return null;
    }

    @Override
    public Client update(Client entity) {
        // Implementează logica de actualizare în baza de date
        return null;
    }
}
