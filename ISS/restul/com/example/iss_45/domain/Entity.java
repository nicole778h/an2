package com.example.iss_45.domain;

import java.io.Serializable;

public interface Entity<ID> extends Serializable {
    public ID getId();
    public void setId(ID id);
}