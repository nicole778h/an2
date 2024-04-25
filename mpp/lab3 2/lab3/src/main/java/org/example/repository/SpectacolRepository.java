package org.example.repository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Spectacol;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import java.sql.*;
import java.time.LocalDate; // Importă clasa LocalDate
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.domain.Spectacol;

public class SpectacolRepository implements Repository<Long, Spectacol> {
    private static final String URL = "jdbc:postgresql://localhost:5432/spectacol";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final Logger logger = LogManager.getLogger(SpectacolRepository.class);

    // Restul codului

    public List<Spectacol> findSpectacoleByDate(LocalDate date) {
        List<Spectacol> spectacoleByDate = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM spectacole WHERE data = ?");
        ) {
            // Convertim LocalDate la Timestamp
            Timestamp timestamp = Timestamp.valueOf(date.atStartOfDay());
            // Setăm parametrul în instrucțiunea SQL
            statement.setTimestamp(1, timestamp);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    long id = resultSet.getLong("id");
                    String loc = resultSet.getString("loc");
                    int numarLocDisponibile = resultSet.getInt("numar_loc_disponibile");
                    int numarLocVandute = resultSet.getInt("numar_loc_vandute");
                    String numeArtist = resultSet.getString("nume_artist");
                    // Extragem timestamp-ul din ResultSet și apoi convertim în LocalDate
                    Timestamp dataTimestamp = resultSet.getTimestamp("data");
                    LocalDate data = dataTimestamp.toLocalDateTime().toLocalDate();
                    Spectacol spectacol = new Spectacol(id, data, loc, numarLocDisponibile, numarLocVandute, numeArtist);
                    spectacoleByDate.add(spectacol);
                }
            }
        } catch (SQLException e) {
            logger.error("Error finding spectacol by date", e);
        }
        return spectacoleByDate;
    }

    @Override
    public Iterable<Spectacol> find_all() {
        List<Spectacol> spectacole = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM spectacole")) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String loc = resultSet.getString("loc");
                int numarLocDisponibile = resultSet.getInt("numar_loc_disponibile");
                int numarLocVandute = resultSet.getInt("numar_loc_vandute");
                LocalDate date = resultSet.getDate("data").toLocalDate(); // Aici extragem data din ResultSet
                String numeArtist = resultSet.getString("nume_artist");

                Spectacol spectacol = new Spectacol(id, date, loc, numarLocDisponibile, numarLocVandute, numeArtist);
                spectacole.add(spectacol);
            }
        } catch (SQLException e) {
            logger.error("Error finding all spectacole", e);
        }
        return spectacole;
    }
    @Override
    public void delete(Spectacol spectacol) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM spectacole WHERE id = ?")) {
            statement.setLong(1, spectacol.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("didnt delete", e);
        }
    }

    @Override
    public void save(Spectacol spectacol) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO spectacole(loc, numar_loc_disponibile, numar_loc_vandute) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, spectacol.getLoc());
            statement.setInt(2, spectacol.getNumarLocDisponibile());
            statement.setInt(3, spectacol.getNumarLocVandute());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                spectacol.setId(id);
            }
        } catch (SQLException e) {
            logger.error("didnt save", e);
        }
    }

    @Override
    public void update(Spectacol spectacol) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE spectacole SET loc = ?, numar_loc_disponibile = ?, numar_loc_vandute = ? WHERE id = ?")) {
            statement.setString(1, spectacol.getLoc());
            statement.setInt(2, spectacol.getNumarLocDisponibile());
            statement.setInt(3, spectacol.getNumarLocVandute());
            statement.setLong(4, spectacol.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("didnt update", e);
        }
    }
}

