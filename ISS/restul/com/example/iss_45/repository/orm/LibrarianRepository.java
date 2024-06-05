package com.example.iss_45.repository.orm;

import com.example.iss_45.domain.Librarian;
import com.example.iss_45.repository.ILibrarianRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class LibrarianRepository implements ILibrarianRepository {

    public LibrarianRepository() {
    }

    @Override
    public Librarian findByUserAndPass(String username, String password) {
        try (SessionFactory sessionFactory = HibernateSession.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Librarian librarian = session.createQuery("from Librarian where username = :username and password = :password", Librarian.class)
                    .setParameter("username", username)
                    .setParameter("password", password)
                    .uniqueResult();
            session.getTransaction().commit();
            return librarian;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la gasirea bibliotecarului: " + ex);
            return null;
        }
    }

    @Override
    public Librarian findOne(Integer integer) throws IllegalArgumentException {
        try (SessionFactory sessionFactory = HibernateSession.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Librarian librarian = session.get(Librarian.class, integer);
            session.getTransaction().commit();
            return librarian;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la gasirea bibliotecarului: " + ex);
            return null;
        }
    }

    @Override
    public List<Librarian> getAll() {
        try (SessionFactory sessionFactory = HibernateSession.getSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Librarian> librarians = session.createQuery("from Librarian", Librarian.class).list();
            session.getTransaction().commit();
            return librarians;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la obtinerea listei de bibliotecari: " + ex);
            return null;
        }
    }


    // Restul metodelor (clear, save, update, delete) rămân la fel


    @Override
    public void clear() {

    }

    @Override
    public void save(Librarian entity) {

    }

    @Override
    public void update(Librarian entity) {

    }

    @Override
    public void delete(Integer integer) {

    }
}