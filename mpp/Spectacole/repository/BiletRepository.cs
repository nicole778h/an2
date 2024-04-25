using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using log4net;
using lab_mpp3.domain;

namespace lab_mpp3.repository
{
    public class BiletRepository : Repository<Bilet, long>
    {
        private static readonly ILog log = LogManager.GetLogger("BiletDbRepository");
        private IDictionary<string, string> props;
        private SpectacolRepository spectacolRepository;
        private IDbConnection connection;

        public BiletRepository(IDictionary<string, string> props, SpectacolRepository spectacolRepository,
            IDbConnection connection)
        {
            log.Info("Creating BiletRepository ");
            this.props = props;
            Console.WriteLine("Props dictionary initialized with the following values:");
            foreach (var prop in props)
            {
                Console.WriteLine($"{prop.Key}: {prop.Value}");
            }

            this.spectacolRepository = spectacolRepository;
            // Initializează conexiunea
            this.connection = DBUtils.getConnection(props);
            Console.WriteLine("Connection object created.");
        }


        /* public BiletRepository(IDictionary<string, string> props, SpectacolRepository spectacolRepository, IDbConnection connection)
         {
             log.Info("Creating BiletRepository ");
             this.props = props;
             this.spectacolRepository = spectacolRepository;
             this.connection = connection;
         }*/
        public void save(Bilet elem)
        {
            var con = DBUtils.getConnection(props);
                try{
                this.connection.Open();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "INSERT INTO Bilet(idSpectacol, numeCumparator, numarLocSelectate) " +
                                       "VALUES (@idSpectacol, @numeCumparator, @numarLocSelectate)";

                    var paramIdSpectacol = comm.CreateParameter();
                    paramIdSpectacol.ParameterName = "@idSpectacol";
                    paramIdSpectacol.Value = elem.IdSpectacol;
                    comm.Parameters.Add(paramIdSpectacol);

                    var paramNumeCumparator = comm.CreateParameter();
                    paramNumeCumparator.ParameterName = "@numeCumparator";
                    paramNumeCumparator.Value = elem.NumeCumparator;
                    comm.Parameters.Add(paramNumeCumparator);

                    var paramNumarLocSelectate = comm.CreateParameter();
                    paramNumarLocSelectate.ParameterName = "@numarLocSelectate";
                    paramNumarLocSelectate.Value = elem.NumarLocSelectate;
                    comm.Parameters.Add(paramNumarLocSelectate);

                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No Bilet added");
                } this.connection.Close();
            }
                catch (Exception e)
                {
                    throw new Exception(e.Message);
                }
        }

        public void delete(Bilet elem)
        {
            using (var con = DBUtils.getConnection(props))
            {
                con.Open();
                using (var comm = con.CreateCommand())
                {
                    comm.CommandText = "DELETE FROM Bilet WHERE idSpectacol = @idSpectacol";

                    var paramIdSpectacol = comm.CreateParameter();
                    paramIdSpectacol.ParameterName = "@idSpectacol";
                    paramIdSpectacol.Value = elem.IdSpectacol;
                    comm.Parameters.Add(paramIdSpectacol);

                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No Bilet deleted");
                }
            }
        }

        public void update(Bilet elem, long id)
        {
            throw new NotImplementedException();
        }

        public Bilet findOne(long id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<Bilet> findAll()
        {
            throw new NotImplementedException();
        }




        public void BuyTickets(long spectacolId, int numarLocuriSelectate)
        {
            Console.WriteLine("Buying tickets...");
            var connection = DBUtils.getConnection(props);
            
            try
            {
                // Deschide conexiunea
                this.connection.Open();
                Console.WriteLine("Connection opened");

                // Creează comanda SQL
                using (var command = this.connection.CreateCommand())
                {
                    command.CommandText =
                        "UPDATE Spectacol SET numarLocDisponibile = numarLocDisponibile - @numarLocSelectate, numarLocVandute = numarLocVandute + @numarLocSelectate WHERE id = @id";

                    // Adaugă parametrii
                    var paramNumberOfTickets = command.CreateParameter();
                    paramNumberOfTickets.ParameterName = "@numarLocSelectate";
                    paramNumberOfTickets.Value = numarLocuriSelectate;
                    command.Parameters.Add(paramNumberOfTickets);

                    var paramSpectacolId = command.CreateParameter();
                    paramSpectacolId.ParameterName = "@id";
                    paramSpectacolId.Value = spectacolId;
                    command.Parameters.Add(paramSpectacolId);

                    // Execută comanda SQL
                    Console.WriteLine("Executing SQL command...");
                    command.ExecuteNonQuery();

                    Console.WriteLine("Tickets bought successfully!");
                } this.connection.Close();
            }
            catch (Exception e)
            {
                Console.WriteLine("An error occurred while buying tickets:");
                Console.WriteLine(e.Message);
            }
            
        }
    }
}