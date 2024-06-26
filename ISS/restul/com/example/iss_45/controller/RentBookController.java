package com.example.iss_45.controller;



import com.example.iss_45.domain.Book;
import com.example.iss_45.domain.BookRental;
import com.example.iss_45.domain.Client;
import com.example.iss_45.service.Service;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
//import service.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RentBookController {
    private Service service;
    private Client client;

    public void setService(Service service) {
        this.service = service;
    }

    public void setClient(Client client) {
        this.client = client;

        // Initialize columns
        clmId.setCellValueFactory(new PropertyValueFactory<Book,Integer>("id"));
        clmTitle.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        clmAutor.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAuthor()));
        clmEditura.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPublishing_house()));
        clmAn.setCellValueFactory(new PropertyValueFactory<Book,Integer>("year_of_publication"));

        // Fetch and display not rented books
        books.addAll(service.getNotRentedBooks());
        tabelBooks.setItems(books);
    }


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

    ObservableList<Book> books = FXCollections.observableArrayList();
    ObservableList<Book> selectedBooks = FXCollections.observableArrayList();

    @FXML
    void finishRentalClick(MouseEvent event) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("com/example/iss_45/RentalFormularView.fxml"));
        Parent root = fxmlLoader.load();
        RentalFormularController ctrl = fxmlLoader.getController();
        ctrl.setService(service);

        // transmitem o lista de imprumuri
        List<BookRental> rentals = new ArrayList<BookRental>();
        for(Book book: selectedBooks){
//            System.err.println(book);
            BookRental bookRental = new BookRental(client.getId(), book.getId(),0,0);
            rentals.add(bookRental);
        }

        Stage st = (Stage) tabelBooks.getScene().getWindow();
        st.close();

        ctrl.setRentals(rentals);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    void pickBook(MouseEvent event) {
        Book book = tabelBooks.getSelectionModel().getSelectedItem();
        selectedBooks.add(book);
    }

    @FXML
    void showRentedBooksButton(MouseEvent event) {
        books.clear();
        for(Book b: selectedBooks)
            books.add(b);
        tabelBooks.setItems(books);
    }

}