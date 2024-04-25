using System;

namespace lab_mpp3.domain
{
    public class Spectacol : Entity<long>
    {
        private DateTime data;
        private string loc;
        private int numarLocDisponibile;
        private int numarLocVandute;
        private string numeArtist;

        public Spectacol()
        {
            data = DateTime.MinValue;
            loc = "";
            numarLocDisponibile = 0;
            numarLocVandute = 0;
            numeArtist = "";
        }

        public Spectacol(DateTime data, string loc, int numarLocDisponibile, int numarLocVandute, string numeArtist)
        {
            this.data = data;
            this.loc = loc;
            this.numarLocDisponibile = numarLocDisponibile;
            this.numarLocVandute = numarLocVandute;
            this.numeArtist = numeArtist;
        }

        public DateTime Data
        {
            get { return data; }
            set { data = value; }
        }

        public string Loc
        {
            get { return loc; }
            set { loc = value; }
        }

        public int NumarLocDisponibile
        {
            get { return numarLocDisponibile; }
            set { numarLocDisponibile = value; }
        }

        public int NumarLocVandute
        {
            get { return numarLocVandute; }
            set { numarLocVandute = value; }
        }

        public string NumeArtist
        {
            get { return numeArtist; }
            set { numeArtist = value; }
        }

        public override string ToString()
        {
            return $"Spectacol: {numeArtist}, Data: {data}, Loc: {loc}, Numar Locuri Disponibile: {numarLocDisponibile}, Numar Locuri Vandute: {numarLocVandute}";
        }
    }
}