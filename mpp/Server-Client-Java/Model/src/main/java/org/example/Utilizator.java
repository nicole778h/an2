package org.example;

import java.io.Serializable;

public class Utilizator extends Entity<Long> implements Serializable {
    private String username;
    private String parola;


    public Utilizator(Long id, String username, String parola) {
        super(id);
        this.username = username;
        this.parola = parola;
    }

    // Constructorul fără parametri pentru deserializare
    // Acesta este adăugat pentru a remedia eroarea de deserializare
    public Utilizator() {
        super(0L); // Set default ID value
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }
}