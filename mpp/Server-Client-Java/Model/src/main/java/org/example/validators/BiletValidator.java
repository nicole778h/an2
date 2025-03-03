package org.example.validators;


import org.example.Bilet;

public class BiletValidator implements Validator<Bilet> {
    @Override
    public void validate(Bilet bilet) throws ValidationException {
        if (bilet == null) {
            throw new ValidationException("Biletul nu poate fi null.");
        }
        if (bilet.getNumeCumparator() == null || bilet.getNumeCumparator().trim().isEmpty()) {
            throw new ValidationException("Numele cumpărătorului nu poate fi gol.");
        }
        if (bilet.getNumarLocSelectate() <= 0) {
            throw new ValidationException("Numărul de locuri selectate trebuie să fie mai mare decât 0.");
        }
        // Alte reguli de validare pot fi adăugate aici
    }
}