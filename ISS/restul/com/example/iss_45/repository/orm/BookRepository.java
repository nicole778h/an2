package com.example.iss_45.repository.orm;

import com.example.iss_45.domain.Book;
import com.example.iss_45.repository.IBookRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class BookRepository implements IBookRepository {

    private final SessionFactory sessionFactory;

    public BookRepository() {
        this.sessionFactory = HibernateSession.getSessionFactory();
    }

    @Override
    public Book findOne(Integer integer) throws IllegalArgumentException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Book book = session.get(Book.class, integer);
            session.getTransaction().commit();
            System.out.println("Carte gasita: " + book);
            return book;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la gasirea cartii: " + ex);
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public List<Book> getAll() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            List<Book> books = session.createQuery("from Book", Book.class).list();
            session.getTransaction().commit();
            System.out.println("Lista de carti: " + books);
            return books;
        } catch (RuntimeException ex) {
            System.err.println("Eroare la obtinerea listei de carti: " + ex);
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
            return null;
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void clear() {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            session.createQuery("delete from Book").executeUpdate();
            session.getTransaction().commit();
        } catch (RuntimeException ex) {
            System.err.println("Eroare la stergerea tuturor cartilor: " + ex);
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void save(Book entity) {
        Session session = null;
        try {
            session = HibernateSession.getSessionFactory().openSession();
            System.out.println("Session opened: " + session);
            session.beginTransaction();
            System.out.println("Transaction started");
            session.save(entity);
            session.getTransaction().commit();
            System.out.println("Transaction committed");
        } catch (RuntimeException ex) {
            System.err.println("Eroare la inserare: " + ex);
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
                System.out.println("Transaction rolled back");
            }
        } finally {
            if (session != null) {
                session.close();
                System.out.println("Session closed");
            }
        }
    }


    @Override
    public void update(Book entity) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Book book = session.get(Book.class, entity.getId());
            if (book != null) {
                book.setTitle(entity.getTitle());
                book.setAuthor(entity.getAuthor());
                book.setPublishing_house(entity.getPublishing_house());
                book.setYear_of_publication(entity.getYear_of_publication());
                session.update(book);
                session.getTransaction().commit();
            }
        } catch (RuntimeException ex) {
            System.err.println("Eroare la update: " + ex);
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void delete(Integer integer) {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.beginTransaction();
            Book book = session.get(Book.class, integer);
            if (book != null) {
                session.delete(book);
                session.getTransaction().commit();
            }
        } catch (RuntimeException ex) {
            System.err.println("Eroare la stergere: " + ex);
            if (session != null && session.getTransaction().isActive()) {
                session.getTransaction().rollback();
            }
        } finally {
            if (session != null) {
                session.close();
            }
        }
    }
}
