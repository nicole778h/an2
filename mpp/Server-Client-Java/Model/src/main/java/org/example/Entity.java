package org.example;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private long id;

    // Constructor
    public Entity(long id) {
        this.id = id;
    }

    // Metode pentru a accesa și modifica câmpul id
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


}