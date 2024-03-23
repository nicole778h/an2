package com.example.exam.repository;

import com.example.exam.domain.Ticket;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDbRepository implements Repository<Long, Ticket> {
    private final String urlDb;
    private final String usernameDb;
    private final String passwordDb;
//constructor
    public TicketDbRepository(String urlDb, String usernameDb, String passwordDb) {
        this.urlDb = urlDb;
        this.usernameDb = usernameDb;
        this.passwordDb = passwordDb;
    }

    @Override
    public Ticket findOne(Long aLong) {
        return null;
    }

    @Override
    public List<Ticket> findAll() {
        return null;
    }
//adaugă un nou bilet în baza de date
    @Override
    public Ticket save(Ticket entity) {
        String sql = "INSERT INTO tickets(username, flight_id, purchase_time) VALUES " +
                "                                (?, ?, now())";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, entity.getUsername());
            preparedStatement.setLong(2, entity.getFlightId());

            preparedStatement.executeUpdate();
            return entity;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    //returnează o listă de bilete cumpărate într-o anumită dată.
    public List<Ticket> getTicketsForDate(Date date) {
        List<Ticket> tickets = new ArrayList<>();
        String sql = "SELECT * FROM tickets WHERE DATE(purchase_time) = ?";
        try (
                Connection connection = DriverManager.getConnection(urlDb, usernameDb, passwordDb);
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setDate(1, new java.sql.Date(date.getTime()));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                // Obțineți datele din ResultSet și creați obiectul Ticket corespunzător
                // Adăugați obiectul Ticket la lista rezultatelor
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public Ticket delete(Long aLong) {
        return null;
    }

    @Override
    public Ticket update(Ticket entity) {
        return null;
    }
}