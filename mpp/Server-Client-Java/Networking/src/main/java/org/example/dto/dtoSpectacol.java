package org.example.dto;

import java.io.Serializable;
import java.time.LocalDateTime;


public class dtoSpectacol implements Serializable {
    private Long id;
    private LocalDateTime data;
    private String loc;
    private int numarLocDisponibile;
    private int numarLocVandute;
    private String numeArtist;

    public dtoSpectacol(Long id, LocalDateTime data, String loc, int numarLocDisponibile, int numarLocVandute, String numeArtist) {
        this.id = id;
        this.data = data;
        this.loc = loc;
        this.numarLocDisponibile = numarLocDisponibile;
        this.numarLocVandute = numarLocVandute;
        this.numeArtist = numeArtist;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "dtoSpectacol{" +
                "id=" + id +
                ", data=" + data +
                ", loc='" + loc + '\'' +
                ", numarLocDisponibile=" + numarLocDisponibile +
                ", numarLocVandute=" + numarLocVandute +
                ", numeArtist='" + numeArtist + '\'' +
                '}';
    }
}
