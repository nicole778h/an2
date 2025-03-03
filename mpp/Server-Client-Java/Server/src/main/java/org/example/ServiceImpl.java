package org.example;

import java.time.LocalDate;
import java.util.List;

public class ServiceImpl implements IService {
    private final BiletRepository biletRepository;
    private final SpectacolRepository spectacolRepository;
    private final UtilizatorRepository utilizatorRepository;

    public ServiceImpl(BiletRepository biletRepository, SpectacolRepository spectacolRepository, UtilizatorRepository utilizatorRepository) {
        this.biletRepository = biletRepository;
        this.spectacolRepository = spectacolRepository;
        this.utilizatorRepository = utilizatorRepository;
    }

    // Metode pentru operațiile cu bilete
    public List<Bilet> findAllBilete() {
        return (List<Bilet>) biletRepository.find_all();
    }

    public void saveBilet(Bilet bilet) {
        biletRepository.save(bilet);
    }

    public void deleteBilet(Bilet bilet) {
        biletRepository.delete(bilet);
    }

    public void updateBilet(Bilet bilet) {
        biletRepository.update(bilet);
    }

    @Override
    public void buyBilet(Bilet bilet) {

    }

    // Metode pentru operațiile cu spectacole
    // Metode pentru operațiile cu spectacole
    public List<Spectacol> findAllSpectacole() {
        List<Spectacol> spectacole = (List<Spectacol>) spectacolRepository.find_all();
        System.out.println("Lista de spectacole: " + spectacole);
        return spectacole;
    }

    // public List<Spectacol> findAllSpectacole() {
    //return (List<Spectacol>) spectacolRepository.find_all();
    //}
    // public List<Spectacol> findAllSpectacole() {
    //  return (List<Spectacol>) spectacolRepository.find_all();
    //}
    public Utilizator findUtilizatorByUsername(String username) {
        return utilizatorRepository.findByUsername(username);
    }
    public List<Spectacol> findSpectacoleByDate(LocalDate date) {
        return spectacolRepository.findSpectacoleByDate(date);
    }
    public void saveSpectacol(Spectacol spectacol) {
        spectacolRepository.save(spectacol);
    }

    public void deleteSpectacol(Spectacol spectacol) {
        spectacolRepository.delete(spectacol);
    }

    public void updateSpectacol(Spectacol spectacol) {
        spectacolRepository.update(spectacol);
    }

    // Metode pentru operațiile cu utilizatori
    public List<Utilizator> findAllUtilizatori() {
        return (List<Utilizator>) utilizatorRepository.find_all();
    }

    public void saveUtilizator(Utilizator utilizator) {
        utilizatorRepository.save(utilizator);
    }

    public void deleteUtilizator(Utilizator utilizator) {
        utilizatorRepository.delete(utilizator);
    }

    public void updateUtilizator(Utilizator utilizator) {
        utilizatorRepository.update(utilizator);
    }
}