package org.example.repository;

import org.example.domain.Bilet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import static org.example.repository.JdbcUtils.logger;


public class BiletRepository implements Repository<Long, Bilet> {
    private static final String URL = "jdbc:postgresql://localhost:5432/spectacol";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final Logger logger = LogManager.getLogger(BiletRepository.class);


    @Override
    public Iterable<Bilet> find_all() {
        List<Bilet> bilete = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM bilete")) {

            while (resultSet.next()) {
                long id = resultSet.getLong("id");
                String numeCumparator = resultSet.getString("nume_cumparator");
                int numarLocSelectate = resultSet.getInt("numar_loc_selectate");
                long idSpectacol = resultSet.getLong("id_spectacol");
                Bilet bilet = new Bilet(id, numeCumparator, numarLocSelectate, idSpectacol);
                bilete.add(bilet);
            }
        } catch (SQLException e) {
            logger.error("didnt find", e);
        }
        return bilete;
    }

    @Override
    public void delete(Bilet bilet) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("DELETE FROM bilete WHERE id = ?")) {
            statement.setLong(1, bilet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("didnt delete", e);
        }
    }

    @Override
    public void save(Bilet bilet) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("INSERT INTO bilete(nume_cumparator, numar_loc_selectate, id_spectacol) VALUES (?, ?, ?)",
                     Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, bilet.getNumeCumparator());
            statement.setInt(2, bilet.getNumarLocSelectate());
            statement.setLong(3, bilet.getIdSpectacol());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                long id = resultSet.getLong(1);
                bilet.setId(id);
            }
        } catch (SQLException e) {
            logger.error("didnt save", e);
        }
    }

    @Override
    public void update(Bilet bilet) {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
             PreparedStatement statement = connection.prepareStatement("UPDATE bilete SET nume_cumparator = ?, numar_loc_selectate = ?, id_spectacol = ? WHERE id = ?")) {
            statement.setString(1, bilet.getNumeCumparator());
            statement.setInt(2, bilet.getNumarLocSelectate());
            statement.setLong(3, bilet.getIdSpectacol());
            statement.setLong(4, bilet.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("didnt update", e);
        }
    }
}
