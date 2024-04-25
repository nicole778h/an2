package org.example.domain;

import java.util.UUID;

public class Bilet extends Entity <Long>{
    private String numeCumparator;
    private int numarLocSelectate;
    private long idSpectacol;

    public Bilet(Long id, String numeCumparator, int numarLocSelectate, long idSpectacol) {
        super(id);
        this.numeCumparator = numeCumparator;
        this.numarLocSelectate = numarLocSelectate;
        this.idSpectacol = idSpectacol;
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


}