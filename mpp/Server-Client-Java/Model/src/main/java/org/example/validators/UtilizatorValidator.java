package org.example.validators;


import org.example.Utilizator;

public class UtilizatorValidator implements Validator<Utilizator> {
    @Override
    public void validate(Utilizator utilizator) throws ValidationException {
        if (utilizator == null) {
            throw new ValidationException("Utilizatorul nu poate fi null.");
        }
        if (utilizator.getUsername() == null || utilizator.getUsername().trim().isEmpty()) {
            throw new ValidationException("Numele de utilizator nu poate fi gol.");
        }
        if (utilizator.getParola() == null || utilizator.getParola().trim().isEmpty()) {
            throw new ValidationException("Parola nu poate fi goală.");
        }
        // Alte reguli de validare pot fi adăugate aici
    }
}