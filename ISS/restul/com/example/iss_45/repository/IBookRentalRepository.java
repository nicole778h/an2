package com.example.iss_45.repository;

import com.example.iss_45.domain.BookRental;

public interface IBookRentalRepository extends Repository<Integer, BookRental>{
    public void returnBook(BookRental bookRental);
}