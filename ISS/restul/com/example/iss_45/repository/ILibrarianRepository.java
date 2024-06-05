package com.example.iss_45.repository;

import com.example.iss_45.domain.Librarian;

public interface ILibrarianRepository extends Repository<Integer, Librarian>{
    Librarian findByUserAndPass(String username, String password);
}