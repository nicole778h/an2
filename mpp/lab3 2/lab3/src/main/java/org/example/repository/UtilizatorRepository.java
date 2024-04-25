package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Utilizator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizatorRepository implements Repository<Long, Utilizator> {
    private static final String URL = "jdbc:postgresql://localhost:5432/spectacol";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final Logger logger = LogManager.getLogger(UtilizatorRepository.class);

    @Override
    public Iterable<Utilizator> find_all() {
        List<Utilizator> utilizatori = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM utilizatori")) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String username = resultSet.getString("username");
                String parola = resultSet.getString("parola");
                Utilizator utilizator = new Utilizator(id, username, parola);
                utilizatori.add(utilizator);
            }
        } catch (SQLException e) {
            logger.error("didnt find", e);
        }
        return utilizatori;
    }
    public Utilizator findByUsername(String username) {
        Utilizator utilizator = null;
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM utilizatori WHERE username = ?")) {
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                long id = resultSet.getLong("id");
                String parola = resultSet.getString("parola");
                utilizator = new Utilizator(id, username, parola);
            }
        } catch (SQLException e) {
            logger.error("Error finding utilizator by username", e);
        }
        return utilizator;
    }

    @Override
    public void delete(Utilizator utilizator) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM utilizatori WHERE id = ?")) {
            statement.setLong(1, utilizator.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("didnt delete", e);
        }
    }

    @Override
    public void save(Utilizator utilizator) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO utilizatori(username, parola) VALUES (?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, utilizator.getUsername());
            statement.setString(2, utilizator.getParola());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                utilizator.setId(id);
            }
        } catch (SQLException e) {
            logger.error("didnt save", e);
        }
    }

    @Override
    public void update(Utilizator utilizator) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE utilizatori SET username = ?, parola = ? WHERE id = ?")) {
            statement.setString(1, utilizator.getUsername());
            statement.setString(2, utilizator.getParola());
            statement.setLong(3, utilizator.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("didnt update", e);
        }
    }
}
