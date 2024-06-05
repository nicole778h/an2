package com.example.iss_45.controller;

import com.example.iss_45.domain.Book;
import com.example.iss_45.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ViewBooksController {
    private Service service;
    private ObservableList<Book> books;

    @FXML
    private TableView<Book> tabelBooks;

    @FXML
    private TableColumn<Book, Integer> clmId;

    @FXML
    private TableColumn<Book, String> clmTitle;

    @FXML
    private TableColumn<Book, String> clmAutor;

    @FXML
    private TableColumn<Book, String> clmEditura;

    @FXML
    private TableColumn<Book, Integer> clmAn;

    public ViewBooksController() {
        // Inițializare lista de cărți în constructor
        //books = FXCollections.observableArrayList();
        books = FXCollections.observableArrayList();
    }

    public void setService(Service service){
        this.service = service;
        getBooks();
    }

    public void getBooks() {
        // Set up the columns in the table
        clmId.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
        clmTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        clmAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        clmEditura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublishing_house()));
        clmAn.setCellValueFactory(new PropertyValueFactory<Book,Integer>("year_of_publication"));

        // Load the data from the service
        if (books == null) {
            books = FXCollections.observableArrayList();
        } else {
            books.clear();
        }
        books.addAll(service.getAllBooks());
        tabelBooks.setItems(books);
    }

}
