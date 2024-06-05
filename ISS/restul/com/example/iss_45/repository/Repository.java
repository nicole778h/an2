package com.example.iss_45.repository;

import com.example.iss_45.domain.Entity;

import java.util.List;

public interface Repository<ID, E extends Entity<ID>> {

        /**
         * Returns the entity that has the given id
         * @param id ID - the id of the entity to be returned
         *           id must not be null
         * @return the entity with the specified id
         * @throws IllegalArgumentException if the id is null.
         */
        E findOne(ID id) throws IllegalArgumentException;

        /**
         * Returns all entities in the repository
         * @return - an iterable object that contains all entities
         */
        List<E> getAll();

        /**
         * Clears the repository's data
         */
        void clear();

        /**
         * Add an entity
         */
        void save(E entity);

        /**
         * Update an entity
         */
        void update(E entity);

        /**
         * Delete an entity
         */
        void delete(ID id);
}

