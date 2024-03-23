package com.example.exam.service;

import com.example.exam.domain.Ticket;
import com.example.exam.repository.TicketDbRepository;

public class TicketService {
    private TicketDbRepository ticketDbRepository;

    public TicketService(TicketDbRepository ticketDbRepository) {
        this.ticketDbRepository = ticketDbRepository;
    }

    public Ticket save(Ticket ticket) {
        return ticketDbRepository.save(ticket);
    }
}