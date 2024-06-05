package com.example.iss_45.repository.orm;

import com.example.iss_45.domain.BookRental;
import com.example.iss_45.repository.IBookRentalRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class BookRentalRepository implements IBookRentalRepository {

    public BookRentalRepository() {}

    @Override
    public BookRental findOne(Integer integer) throws IllegalArgumentException {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            BookRental bookRental = session.get(BookRental.class, integer);
            System.out.println("Detalii imprumut gasit: " + bookRental); // Adăugare print pentru a verifica detalii
            session.getTransaction().commit();
            return bookRental;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la gasirea imprumutului: " + ex);
            return null;
        }
    }

    @Override
    public List<BookRental> getAll() {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<BookRental> bookRentals = session.createQuery("from BookRental", BookRental.class).list();
            System.out.println("Lista de imprumuturi: " + bookRentals); // Adăugare print pentru a verifica lista de imprumuturi
            session.getTransaction().commit();
            return bookRentals;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la obtinerea listei de imprumuturi: " + ex);
            return null;
        }
    }

    @Override
    public void save(BookRental entity) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                session.save(entity);
                System.out.println("Imprumut salvat cu succes: " + entity); // Adăugare print pentru a verifica salvarea cu succes
                tx.commit();
            } catch (RuntimeException ex) {
                System.err.println("Eroare la inserare: " + ex);
                if (tx != null) tx.rollback();
            }
        }
    }
    @Override
    public void clear() {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.createQuery("delete from BookRental").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            System.err.println("Eroare la stergerea tuturor imprumuturilor: " + ex);
        }
    }



    @Override
    public void update(BookRental entity) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                BookRental bookRental = session.get(BookRental.class, entity.getId());
                if (bookRental != null) {
                    bookRental.setClient(entity.getClient());
                    bookRental.setBook(entity.getBook());
                    bookRental.setReturned(entity.getReturned());
                    bookRental.setDays(entity.getDays());
                    session.update(bookRental);
                    tx.commit();
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
                BookRental bookRental = session.get(BookRental.class, integer);
                if (bookRental != null) {
                    session.delete(bookRental);
                    tx.commit();
                }
            } catch (RuntimeException ex) {
                System.err.println("Eroare la stergere: " + ex);
                if (tx != null) tx.rollback();
            }
        }
    }

    @Override
    public void returnBook(BookRental entity) {
        SessionFactory sessionFactory = HibernateSession.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                BookRental bookRental = session.get(BookRental.class, entity.getId());
                if (bookRental != null) {
                    bookRental.setReturned(1);
                    session.update(bookRental);
                    tx.commit();
                }
            } catch (RuntimeException ex) {
                System.err.println("Eroare la returnarea cartii: " + ex);
                if (tx != null) tx.rollback();
            }
        }
    }
}
