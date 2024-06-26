package org.example.domain;

import java.util.UUID;

public class Utilizator extends Entity <Long> {
    private String username;
    private String parola;

    public Utilizator(long id, String username, String parola) {
        super(id);
        this.username = username;
        this.parola = parola;
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