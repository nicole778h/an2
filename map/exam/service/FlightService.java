package com.example.exam.service;

import com.example.exam.domain.Flight;
import com.example.exam.observer.Observable;
import com.example.exam.repository.FlightDbRepository;

import java.util.Date;
import java.util.List;

public class FlightService extends Observable {
    private FlightDbRepository flightDbRepository;//ob de tip

    public FlightService(FlightDbRepository flightDbRepository) {
        this.flightDbRepository = flightDbRepository;
    }

    public List<String> getAllFrom() {
        return flightDbRepository.getAllFrom();
    }

    public List<String> getAllTo() {
        return flightDbRepository.getAllTo();
    }

    public List<Flight> getFLightsForRoute(String from, String to, Date date) {
        return flightDbRepository.getFLightsForRoute(from, to, date);
    }

    public void book(Long id) {
        flightDbRepository.book(id);
        notifyObs();
    }

}