using System;
using System.Collections.Generic;
using lab_mpp3.domain;
using lab_mpp3.repository;

namespace Spectacole.service
{
    public class BiletService
    {
        private BiletRepository biletRepository;
        private SpectacolRepository spectacolRepository;

        public BiletService(BiletRepository biletRepository, SpectacolRepository spectacolRepository)
        {
            this.biletRepository = biletRepository;
            this.spectacolRepository = spectacolRepository;
        }

        public void SaveBilet(Bilet bilet)
        {
            biletRepository.save(bilet);
        }

        public void DeleteBilet(Bilet bilet)
        {
            biletRepository.delete(bilet);
        }

        public void BuyTickets(long spectacolId, int numarBilete)
        {
            try
            {
                // Creează un nou bilet și salvează-l în baza de date
                Bilet bilet = new Bilet { IdSpectacol = spectacolId, NumeCumparator = "NumeCumparator", NumarLocSelectate = numarBilete };
                biletRepository.save(bilet);

                // Actualizează numărul de locuri disponibile pentru spectacolul specificat
                biletRepository.BuyTickets(spectacolId, numarBilete);
            }
            catch (Exception e)
            {
                Console.WriteLine($"An error occurred while buying tickets: {e.Message}");
                throw;
            }
        }
    }
}

    public class SpectacolService
    {
        private SpectacolRepository spectacolRepository;

        public SpectacolService(SpectacolRepository spectacolRepository)
        {
            this.spectacolRepository = spectacolRepository;
        }

        public Spectacol GetSpectacolById(long id)
        {
            int intId = (int)id; // Convertim id-ul de tip long în int
            return spectacolRepository.GetSpectacolById(intId);
        }

        public void SaveSpectacol(Spectacol spectacol)
        {
            spectacolRepository.save(spectacol);
        }

        public void DeleteSpectacol(Spectacol spectacol)
        {
            spectacolRepository.delete(spectacol);
        }

        public void UpdateSpectacol(Spectacol selectedSpectacol)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Spectacol> FindSpectacolByData(DateTime data)
        {
            try
            {
                return spectacolRepository.FindSpectacoleByData(data);
            }
            catch (Exception e)
            {
                Console.WriteLine($"An error occurred while retrieving spectacole by data: {e.Message}");
                throw;
            }
        }

        public IEnumerable<Spectacol> GetAllSpectacole()
        {
            try
            {
                return spectacolRepository.findAll();
            }
            catch (Exception e)
            {
                Console.WriteLine($"An error occurred while retrieving all spectacole: {e.Message}");
                throw;
            }

            return new List<Spectacol>();
        }

        public class UserService
        {
            private UserRepository userRepository;

            public UserService(UserRepository userRepository)
            {
                this.userRepository = userRepository;
            }

            public void SaveUser(User user)
            {
                userRepository.save(user);
            }

            public void DeleteUser(User user)
            {
                userRepository.delete(user);
            }

            // Alte metode pentru operatii cu utilizatori
        }
    }

