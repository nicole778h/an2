package com.example.iss_45.repository.orm;

import com.example.iss_45.domain.Client;
import com.example.iss_45.repository.IClientRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ClientRepository implements IClientRepository {
    public ClientRepository() {
    }

    public Client findByUserAndPass(String username, String password) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Client client = session.createQuery("from Client where username = :username and password = :password", Client.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            System.out.println("Client found: " + client); // Adăugăm print pentru a verifica valoarea clientului
            return client;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la gasirea clientului: " + ex);
            return null;
        }
    }

    @Override
    public Client findOne(Integer integer) throws IllegalArgumentException {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Client client = session.get(Client.class, integer);
            session.getTransaction().commit();
            System.out.println("Client found by id: " + client); // Adăugăm print pentru a verifica valoarea clientului
            return client;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la gasirea clientului: " + ex);
            return null;
        }
    }

    @Override
    public List<Client> getAll() {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Client> clients = session.createQuery("from Client", Client.class).list();
            session.getTransaction().commit();
            System.out.println("All clients retrieved: " + clients); // Adăugăm print pentru a verifica lista de clienți
            return clients;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la obtinerea listei de clienti: " + ex);
            return null;
        }
    }


    @Override
    public void clear() {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from Client").executeUpdate();
            session.getTransaction().commit();
            System.out.println("All clients deleted successfully."); // Adăugăm print pentru a confirma ștergerea cu succes a tuturor clienților
        } catch (RuntimeException ex) {
            System.err.println("Eroare la stergerea tuturor clientilor: " + ex);
        }
    }


    @Override
    public void save(Client entity) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.save(entity);
                tx.commit();
                System.out.println("Client saved successfully: " + entity); // Adăugăm print pentru a confirma salvarea cu succes a clientului
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare: " + ex);
                if (tx != null) tx.rollback();
            }
        }
    }

    @Override
    public void update(Client entity) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Client client = session.get(Client.class, entity.getId());
                if (client != null) {
                    client.setFirst_name(entity.getFirst_name());
                    client.setLast_name(entity.getLast_name());
                    client.setEmail(entity.getEmail());
                    client.setPhone_number(entity.getPhone_number());
                    client.setUsername(entity.getUsername());
                    client.setPassword(entity.getPassword());
                    session.update(client);
                    tx.commit();
                    System.out.println("Client updated successfully: " + entity); // Adăugăm print pentru a confirma actualizarea cu succes a clientului
                }
            } catch (RuntimeException ex) {
                System.err.println("Eroare la update: " + ex);
                if (tx != null) tx.rollback();
            }
        }
    }


    @Override
    public void delete(Integer integer) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                Client client = session.get(Client.class, integer);
                if (client != null) {
                    session.delete(client);
                    tx.commit();
                    System.out.println("Client deleted successfully with id: " + integer); // Adăugăm print pentru a confirma ștergerea cu succes a clientului
                }
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere: " + ex);
                if (tx != null) tx.rollback();
            }
        }
    }
}
