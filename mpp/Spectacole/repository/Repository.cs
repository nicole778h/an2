using System.Collections.Generic;
using lab_mpp3.domain;

namespace lab_mpp3.repository
{
    public interface Repository<T, Tid> where T : Entity<Tid>
    {
        void save(T elem);
        void delete(T elem);
        void update(T elem, Tid id);
        T findOne(Tid id);
        IEnumerable<T> findAll(); // Specify the type parameter for IEnumerable
    }
}