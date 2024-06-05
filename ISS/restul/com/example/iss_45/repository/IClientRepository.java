package com.example.iss_45.repository;


import com.example.iss_45.domain.Client;

public interface IClientRepository extends Repository<Integer, Client>{
    Client findByUserAndPass(String username, String password);
}