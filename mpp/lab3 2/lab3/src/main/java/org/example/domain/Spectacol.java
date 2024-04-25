package org.example.domain;

import java.time.LocalDate;

public class Spectacol extends Entity<Long> {
    private LocalDate data;
    private String loc;
    private int numarLocDisponibile;
    private int numarLocVandute;
    private String numeArtist;

    public Spectacol(Long id, LocalDate data, String loc, int numarLocDisponibile, int numarLocVandute, String numeArtist) {
        super(id);
        this.data = data;
        this.loc = loc;
        this.numarLocDisponibile = numarLocDisponibile;
        this.numarLocVandute = numarLocVandute;
        this.numeArtist = numeArtist;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
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
