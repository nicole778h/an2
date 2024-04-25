package org.example.repository;

import org.example.domain.Entity;

public interface Repository<ID,E extends Entity<ID>> {
    Iterable<E> find_all();
    void delete(E entity);
    void save(E entity);
    void update(E entity);
}