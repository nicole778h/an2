package org.example.validators;

public interface Validator<T> {
    void validate(T entity) throws ValidationException;
}
