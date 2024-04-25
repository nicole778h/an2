using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Data.SQLite;
using log4net;
using lab_mpp3.domain;

namespace lab_mpp3.repository
{
    public class SpectacolRepository : Repository<Spectacol, long>, IDisposable
    {
        private static readonly ILog log = LogManager.GetLogger("SpectacolDbRepository");
        private IDictionary<string, string> props;
        private IDbConnection connection;

        public SpectacolRepository(IDictionary<string, string> props)
        {
            log.Info("Creating SpectacolRepository ");
            this.props = props;
            Console.WriteLine("Props dictionary initialized with the following values:");
            foreach (var prop in props)
            {
                Console.WriteLine($"{prop.Key}: {prop.Value}");
            }
            
            // Initializează conexiunea
            this.connection = DBUtils.getConnection(props);
            Console.WriteLine("Connection object created.");
        }

        // Implementează metoda Dispose pentru a elibera resursele
        
        public void Dispose()
        {
            if (connection != null && connection.State != ConnectionState.Closed)
            {
                connection.Close();
                connection.Dispose();
            }
        }

        public void save(Spectacol elem)
        {
            using (var comm = connection.CreateCommand())
            {
                comm.CommandText =
                    "INSERT INTO Spectacol(data, loc, numarLocDisponibile, numarLocVandute, numeArtist) " +
                    "VALUES (@data, @loc, @numarLocDisponibile, @numarLocVandute, @numeArtist)";

                var paramData = comm.CreateParameter();
                paramData.ParameterName = "@data";
                paramData.Value = elem.Data;
                comm.Parameters.Add(paramData);

                var paramLoc = comm.CreateParameter();
                paramLoc.ParameterName = "@loc";
                paramLoc.Value = elem.Loc;
                comm.Parameters.Add(paramLoc);

                var paramNumarLocDisponibile = comm.CreateParameter();
                paramNumarLocDisponibile.ParameterName = "@numarLocDisponibile";
                paramNumarLocDisponibile.Value = elem.NumarLocDisponibile;
                comm.Parameters.Add(paramNumarLocDisponibile);

                var paramNumarLocVandute = comm.CreateParameter();
                paramNumarLocVandute.ParameterName = "@numarLocVandute";
                paramNumarLocVandute.Value = elem.NumarLocVandute;
                comm.Parameters.Add(paramNumarLocVandute);

                var paramNumeArtist = comm.CreateParameter();
                paramNumeArtist.ParameterName = "@numeArtist";
                paramNumeArtist.Value = elem.NumeArtist;
                comm.Parameters.Add(paramNumeArtist);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No Spectacol added");
            }
        }

        public void delete(Spectacol elem)
        {
            using (var comm = connection.CreateCommand())
            {
                comm.CommandText = "DELETE FROM Spectacol WHERE id = @id";

                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = elem.Id; // Assuming Id is the primary key for Spectacol
                comm.Parameters.Add(paramId);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No Spectacol deleted");
            }
        }

        public void update(Spectacol elem, long id)
        {
            var comm = connection.CreateCommand();
            try
            {
                using (var command = connection.CreateCommand())
                {
                    comm.CommandText =
                        "UPDATE Spectacol SET data = @data, loc = @loc, numarLocDisponibile = @numarLocDisponibile, numarLocVandute = @numarLocVandute, numeArtist = @numeArtist WHERE id = @id";

                    var paramData = comm.CreateParameter();
                    paramData.ParameterName = "@data";
                    paramData.Value = elem.Data;
                    comm.Parameters.Add(paramData);

                    var paramLoc = comm.CreateParameter();
                    paramLoc.ParameterName = "@loc";
                    paramLoc.Value = elem.Loc;
                    comm.Parameters.Add(paramLoc);

                    var paramNumarLocDisponibile = comm.CreateParameter();
                    paramNumarLocDisponibile.ParameterName = "@numarLocDisponibile";
                    paramNumarLocDisponibile.Value = elem.NumarLocDisponibile;
                    comm.Parameters.Add(paramNumarLocDisponibile);

                    var paramNumarLocVandute = comm.CreateParameter();
                    paramNumarLocVandute.ParameterName = "@numarLocVandute";
                    paramNumarLocVandute.Value = elem.NumarLocVandute;
                    comm.Parameters.Add(paramNumarLocVandute);

                    var paramNumeArtist = comm.CreateParameter();
                    paramNumeArtist.ParameterName = "@numeArtist";
                    paramNumeArtist.Value = elem.NumeArtist;
                    comm.Parameters.Add(paramNumeArtist);

                    var paramId = comm.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = id;
                    comm.Parameters.Add(paramId);

                    var result = comm.ExecuteNonQuery();
                    if (result == 0)
                        throw new Exception("No Spectacol updated");
                }

                this.connection.Close();

            }
            catch
            {
                
            }
        }

        public Spectacol findOne(long id)
        {
            using (var comm = connection.CreateCommand())
            {
                comm.CommandText = "SELECT * FROM Spectacol WHERE id = @id";
                var paramId = comm.CreateParameter();
                paramId.ParameterName = "@id";
                paramId.Value = id;
                comm.Parameters.Add(paramId);

                using (var reader = comm.ExecuteReader())
                {
                    if (reader.Read())
                    {
                        DateTime data = reader.GetDateTime(reader.GetOrdinal("data"));
                        string loc = reader.GetString(reader.GetOrdinal("loc"));
                        int numarLocDisponibile = reader.GetInt32(reader.GetOrdinal("numarLocDisponibile"));
                        int numarLocVandute = reader.GetInt32(reader.GetOrdinal("numarLocVandute"));
                        string numeArtist = reader.GetString(reader.GetOrdinal("numeArtist"));

                        return new Spectacol(data, loc, numarLocDisponibile, numarLocVandute, numeArtist);
                    }
                    else
                    {
                        return null;
                    }
                }
            }
        }

        public IEnumerable<Spectacol> findAll()
        {
            List<Spectacol> spectacole = new List<Spectacol>();
            using (var comm = connection.CreateCommand())
            {
                try
                {
                    //connection.Open();

                    if (connection.State != ConnectionState.Open)
                    {
                        connection.Open();
                        Console.WriteLine("Connection opened successfully.");
                    }

                    comm.CommandText = "SELECT * FROM Spectacol";

                    using (var reader = comm.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            DateTime data = reader.GetDateTime(reader.GetOrdinal("data"));
                            string loc = reader.GetString(reader.GetOrdinal("loc"));
                            int numarLocDisponibile = reader.GetInt32(reader.GetOrdinal("numarLocDisponibile"));
                            int numarLocVandute = reader.GetInt32(reader.GetOrdinal("numarLocVandute"));
                            string numeArtist = reader.GetString(reader.GetOrdinal("numeArtist"));

                            Spectacol spectacol =
                                new Spectacol(data, loc, numarLocDisponibile, numarLocVandute, numeArtist);
                            spectacole.Add(spectacol);
                        }
                    }
                    connection.Close();
                }
                catch (SqlException e)
                {
                    System.Console.WriteLine(e);
                }
                finally
                {
                  /*  if (connection.State == ConnectionState.Open)
                    {
                        Console.WriteLine("Connection closed.");
                    }*/
                }
            }

            return spectacole;
        }
        public IEnumerable<Spectacol> FindSpectacoleByData(DateTime data)
        {
            List<Spectacol> spectacole = new List<Spectacol>();

            //using (var connection = DBUtils.getConnection(props))
            var connection = DBUtils.getConnection(props);

            try
            {
                this.connection.Open();

                using (var command = connection.CreateCommand())
                {
                    // Definim comanda SQL cu parametri pentru data de început și sfârșit
                    command.CommandText = "SELECT * FROM Spectacol WHERE data >= @dataStart AND data < @dataEnd";

                    // Adăugăm parametrul pentru data de început
                    var paramDataStart = command.CreateParameter();
                    paramDataStart.ParameterName = "@dataStart";
                    paramDataStart.Value = data.Date; // Data introdusă fără ore, minute și secunde
                    command.Parameters.Add(paramDataStart);

                    // Adăugăm parametrul pentru data de sfârșit
                    var paramDataEnd = command.CreateParameter();
                    paramDataEnd.ParameterName = "@dataEnd";
                    paramDataEnd.Value = data.Date.AddDays(1); // Adăugăm o zi pentru a acoperi întreaga zi
                    command.Parameters.Add(paramDataEnd);

                    // Executăm comanda și citim rezultatele
                    using (var reader = command.ExecuteReader())
                    {
                        while (reader.Read())
                        {
                            // Procesăm fiecare rând și adăugăm spectacolele în lista rezultatelor
                            long id = reader.GetInt64(reader.GetOrdinal("id"));
                            DateTime dataSpectacol = reader.GetDateTime(reader.GetOrdinal("data"));
                            string loc = reader.GetString(reader.GetOrdinal("loc"));
                            int numarLocDisponibile = reader.GetInt32(reader.GetOrdinal("numarLocDisponibile"));
                            int numarLocVandute = reader.GetInt32(reader.GetOrdinal("numarLocVandute"));
                            string numeArtist = reader.GetString(reader.GetOrdinal("numeArtist"));

                            Spectacol spectacol = new Spectacol(dataSpectacol, loc, numarLocDisponibile,
                                numarLocVandute, numeArtist);
                            spectacol.Id = id;
                            spectacole.Add(spectacol);
                        }
                    }
                }
                this.connection.Close();

            }
            catch (Exception e)
            {
                throw new Exception(e.Message);
            }


            return spectacole;
        }


        public Spectacol GetSpectacolById(long id)
        {
            Spectacol spectacol = null;

            var connection = DBUtils.getConnection(props);
            try
            { this.connection.Open();
                
                using (var command = connection.CreateCommand())
                {
                    // Definim comanda SQL pentru a selecta un spectacol după ID
                    command.CommandText = "SELECT * FROM Spectacol WHERE id = @id";

                    // Adăugăm parametrul pentru ID-ul spectacolului căutat
                    var paramId = command.CreateParameter();
                    paramId.ParameterName = "@id";
                    paramId.Value = id;
                    command.Parameters.Add(paramId);

                    // Executăm comanda și citim rezultatele
                    using (var reader = command.ExecuteReader())
                    {
                        if (reader.Read())
                        {
                            // Construim obiectul Spectacol folosind datele din rândul curent al cititorului
                            DateTime data = reader.GetDateTime(reader.GetOrdinal("data"));
                            string loc = reader.GetString(reader.GetOrdinal("loc"));
                            int numarLocDisponibile = reader.GetInt32(reader.GetOrdinal("numarLocDisponibile"));
                            int numarLocVandute = reader.GetInt32(reader.GetOrdinal("numarLocVandute"));
                            string numeArtist = reader.GetString(reader.GetOrdinal("numeArtist"));

                            spectacol = new Spectacol(data, loc, numarLocDisponibile, numarLocVandute, numeArtist);
                            spectacol.Id = id;
                        }
                    }
                } this.connection.Close();
            } 
            catch (Exception e)
            {
                throw new Exception(e.Message);
            }
                

            return spectacol;
        }
       // public void Dispose()
        //{
          //  if (connection != null && connection.State != ConnectionState.Closed)
            //{
             //   connection.Close();
               // connection.Dispose();
            //}
        //}


    }

}