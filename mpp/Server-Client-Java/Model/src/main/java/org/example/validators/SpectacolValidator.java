package org.example.validators;


import org.example.Spectacol;

public class SpectacolValidator implements Validator<Spectacol> {
    @Override
    public void validate(Spectacol spectacol) throws ValidationException {
        if (spectacol == null) {
            throw new ValidationException("Spectacolul nu poate fi null.");
        }
        //if (spectacol.getData() == null || spectacol.getData().isBefore(LocalDateTime.now())) {
        //  throw new ValidationException("Data spectacolului trebuie să fie în viitor.");
        //}
        if (spectacol.getLoc() == null || spectacol.getLoc().trim().isEmpty()) {
            throw new ValidationException("Locul spectacolului nu poate fi gol.");
        }
        if (spectacol.getNumarLocDisponibile() < 0) {
            throw new ValidationException("Numărul de locuri disponibile nu poate fi negativ.");
        }
        if (spectacol.getNumarLocVandute() < 0) {
            throw new ValidationException("Numărul de locuri vândute nu poate fi negativ.");
        }
        if (spectacol.getNumeArtist() == null || spectacol.getNumeArtist().trim().isEmpty()) {
            throw new ValidationException("Numele artistului pentru spectacol nu poate fi gol.");
        }
        // Alte reguli de validare pot fi adăugate aici
    }
}
