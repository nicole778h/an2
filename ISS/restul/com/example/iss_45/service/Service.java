package com.example.iss_45.service;




import com.example.iss_45.domain.*;
import com.example.iss_45.repository.IBookRentalRepository;
import com.example.iss_45.repository.IBookRepository;
import com.example.iss_45.repository.IClientRepository;
import com.example.iss_45.repository.ILibrarianRepository;

import java.util.List;


import java.util.ArrayList;
import java.util.List;


public class Service {
    private IBookRepository bookRepository;
    private IClientRepository clientRepository;
    private ILibrarianRepository librarianRepository;
    private IBookRentalRepository bookRentalRepository;

    public Service(IBookRepository bookRepository, IClientRepository clientRepository, ILibrarianRepository librarianRepository, IBookRentalRepository bookRentalRepository) {
        this.bookRepository = bookRepository;
        this.clientRepository = clientRepository;
        this.librarianRepository = librarianRepository;
        this.bookRentalRepository = bookRentalRepository;
    }

    public User findUser(String username, String password){
        Client cl = clientRepository.findByUserAndPass(username,password);
        if(cl != null){
            return cl;
        }
        Librarian lr = librarianRepository.findByUserAndPass(username,password);
        if(lr != null){
            return lr;
        }
        return null;
    }

    public void addBook(Book book){
        bookRepository.save(book);
    }

    //public List<Book> getAllBooks(){
      //  return bookRepository.getAll();
    //}
    public List<Book> getAllBooks(){return bookRepository.getAll();}

    public List<Client> getAllClients(){
        return clientRepository.getAll();
    }

    public void addClient(Client client){clientRepository.save(client);}

    public void deleteClient(int id){clientRepository.delete(id);}

    public void updateClient(Client client){clientRepository.update(client);}

    public void deleteBook(int id){bookRepository.delete(id);}


    public void borrowBook(BookRental bookRental){bookRentalRepository.save(bookRental);}
    public void returnBook(Book book, Client client)
    {
        for(BookRental bookRental: bookRentalRepository.getAll())
            if(bookRental.getBook() == book.getId() && bookRental.getClient() == client.getId())
                bookRentalRepository.returnBook(bookRental);
    }

    public List<Book> getNotRentedBooks() {
        List<Book> books = new ArrayList<>();
        List<Book> allBooks = bookRepository.getAll();
        List<BookRental> allRentals = bookRentalRepository.getAll();

        for (Book book : allBooks) {
            boolean isRented = false;
            for (BookRental rental : allRentals) {
                if (Integer.valueOf(rental.getBook()).equals(book.getId()) && rental.getReturned() == 0) {
                    isRented = true;
                    break;
                }
            }
            if (!isRented) {
                books.add(book);
            }
        }
        return books;
    }



    public Book getBook(int id){return bookRepository.findOne(id);}

    public List<BookRental> getRentalHistory(Client client){
        List<BookRental> bookRentals = new ArrayList<BookRental>();
        for(BookRental bookRental: bookRentalRepository.getAll())
            if(bookRental.getClient() == client.getId())
                bookRentals.add(bookRental);
        return bookRentals;
    }

    public List<Book> getRentedBooks(Client client){
        List<Book> books = new ArrayList<Book>();
        for(BookRental bookRental: bookRentalRepository.getAll())
            if(bookRental.getClient() == client.getId() && bookRental.getReturned()==0){
                Book book = getBook(bookRental.getBook());
                books.add(book);
            }
        return books;
    }

}