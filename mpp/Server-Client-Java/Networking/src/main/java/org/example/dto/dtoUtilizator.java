package org.example.dto;

import java.io.Serializable;

public class dtoUtilizator implements Serializable {
    private String username;
    private String parola;

    public dtoUtilizator( String username, String parola) {

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

    @Override
    public String toString() {
        return "Utilizator{" +
                "username='" + username + '\'' +
                ", parola='" + parola + '\'' +
                '}';
    }
}
