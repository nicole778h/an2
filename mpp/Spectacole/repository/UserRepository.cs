using System;
using System.Collections;
using System.Collections.Generic;
using System.Data.SqlClient;
using log4net;
using lab_mpp3.domain;

namespace lab_mpp3.repository
{
    public class UserRepository : Repository<User, long>
    {
        private static readonly ILog log = LogManager.GetLogger("UsersDbRepository");
        private IDictionary<string, string> props;

        public UserRepository(IDictionary<string, string> props)
        {
            log.Info("Creating UsersRepository ");
            this.props = props;
        }

        public void save(User elem)
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "insert into Users(username,password) values(@username,@password)";

                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = elem.Username;
                comm.Parameters.Add(paramUsername);

                var paramPassword = comm.CreateParameter();
                paramPassword.ParameterName = "@password";
                paramPassword.Value = elem.Password;
                comm.Parameters.Add(paramPassword);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No User added");
            }
        }

        public void delete(User elem)
        {
            var con = DBUtils.getConnection(props);
            using (var comm = con.CreateCommand())
            {
                comm.CommandText = "DELETE FROM Users WHERE username = @username";

                var paramUsername = comm.CreateParameter();
                paramUsername.ParameterName = "@username";
                paramUsername.Value = elem.Username;
                comm.Parameters.Add(paramUsername);

                var result = comm.ExecuteNonQuery();
                if (result == 0)
                    throw new Exception("No User deleted");
            }
        }

        public void update(User elem, long id)
        {
            throw new NotImplementedException();
        }

        public User findOne(long id)
        {
            throw new NotImplementedException();
        }

        public IEnumerable<User> findAll()
        {
            throw new NotImplementedException();
        }
    }
}
