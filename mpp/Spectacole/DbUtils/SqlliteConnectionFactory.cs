using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;

namespace lab_mpp3.DbUtils
{

    public class SqliteConnectionFactory : ConnectionFactory
    {
        public override IDbConnection createConnection(IDictionary<string, string> properties)
        {
            if (properties.ContainsKey("ConnectionString"))
            {
                string connectionString = properties["ConnectionString"];
                Console.WriteLine("SQLite -- opening connection to ... {0}", connectionString);
                return new SQLiteConnection(connectionString);
            }
            else
            {
                throw new ArgumentException("Missing connection string in properties dictionary.", nameof(properties));
            }

        }
    }
}