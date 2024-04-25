package org.example.domain;

public class Entity<ID> {
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