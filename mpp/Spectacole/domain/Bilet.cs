namespace lab_mpp3.domain
{
    public class Bilet : Entity<long>
    {
        private long idSpectacol;
        private string numeCumparator;
        private int numarLocSelectate;

        public Bilet()
        {
            idSpectacol = 0;
            numeCumparator = "";
            numarLocSelectate = 0;
        }

        public Bilet(long idSpectacol, string numeCumparator, int numarLocSelectate)
        {
            this.idSpectacol = idSpectacol;
            this.numeCumparator = numeCumparator;
            this.numarLocSelectate = numarLocSelectate;
        }

        public long IdSpectacol
        {
            get { return idSpectacol; }
            set { idSpectacol = value; }
        }

        public string NumeCumparator
        {
            get { return numeCumparator; }
            set { numeCumparator = value; }
        }

        public int NumarLocSelectate
        {
            get { return numarLocSelectate; }
            set { numarLocSelectate = value; }
        }

        public override string ToString()
        {
            return $"ID Spectacol: {idSpectacol}, Nume Cumparator: {numeCumparator}, Numar Locuri Selectate: {numarLocSelectate}";
        }
    }
}