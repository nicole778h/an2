package org.example.dto;

import java.io.Serializable;



public class dtoBilet implements Serializable {
    private Long id;
    private String numeCumparator;
    private int numarLocSelectate;
    private long idSpectacol;

    public dtoBilet(Long id, String numeCumparator, int numarLocSelectate, long idSpectacol) {
        this.id = id;
        this.numeCumparator = numeCumparator;
        this.numarLocSelectate = numarLocSelectate;
        this.idSpectacol = idSpectacol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeCumparator() {
        return numeCumparator;
    }

    public void setNumeCumparator(String numeCumparator) {
        this.numeCumparator = numeCumparator;
    }

    public int getNumarLocSelectate() {
        return numarLocSelectate;
    }

    public void setNumarLocSelectate(int numarLocSelectate) {
        this.numarLocSelectate = numarLocSelectate;
    }

    public long getIdSpectacol() {
        return idSpectacol;
    }

    public void setIdSpectacol(long idSpectacol) {
        this.idSpectacol = idSpectacol;
    }

    @Override
    public String toString() {
        return "BiletDTO{" +
                "id=" + id +
                ", numeCumparator='" + numeCumparator + '\'' +
                ", numarLocSelectate=" + numarLocSelectate +
                ", idSpectacol=" + idSpectacol +
                '}';
    }
}
