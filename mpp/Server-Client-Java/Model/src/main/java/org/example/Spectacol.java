package org.example;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Spectacol extends Entity<Long> implements Serializable {
    private LocalDateTime data;
    private String loc;
    private int numarLocDisponibile;
    private int numarLocVandute;
    private String numeArtist;

    public Spectacol(Long id, LocalDateTime data, String loc, int numarLocDisponibile, int numarLocVandute, String numeArtist) {
        super(id);
        this.data = data;
        this.loc = loc;
        this.numarLocDisponibile = numarLocDisponibile;
        this.numarLocVandute = numarLocVandute;
        this.numeArtist = numeArtist;
    }



    public LocalDateTime getData() {
        return data;
    }




    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public String getLoc() {
        return loc;
    }

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public int getNumarLocDisponibile() {
        return numarLocDisponibile;
    }

    public void setNumarLocDisponibile(int numarLocDisponibile) {
        this.numarLocDisponibile = numarLocDisponibile;
    }

    public int getNumarLocVandute() {
        return numarLocVandute;
    }

    public void setNumarLocVandute(int numarLocVandute) {
        this.numarLocVandute = numarLocVandute;
    }

    public String getNumeArtist() {
        return numeArtist;
    }

    public void setNumeArtist(String numeArtist) {
        this.numeArtist = numeArtist;
    }
}
