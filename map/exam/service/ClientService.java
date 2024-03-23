package com.example.exam.service;

import com.example.exam.domain.Client;
import com.example.exam.repository.ClientDbRepository;



    public class ClientService {
        private final ClientDbRepository clientDbRepository;
        //obiect de timp cldbrepo pentru a manipula instantele din db

//constructor
        public ClientService(ClientDbRepository clientDbRepository) {
            this.clientDbRepository = clientDbRepository;
        }

        public Client findOne(String username) {
            return clientDbRepository.findOne(username);
        }
    }

