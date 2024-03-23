package com.example.exam.domain;

import java.io.Serializable;
import java.util.Objects;


public class Entity<ID> {
    ID id;

    public ID getId() {
        return id;
    }

    public void setId(ID id) {
        this.id = id;
    }
}
