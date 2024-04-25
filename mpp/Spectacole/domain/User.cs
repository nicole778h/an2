namespace lab_mpp3.domain
{

    public class User : Entity<long>
    {
        private string username;
        private string password;

        public User()
        {
            username = "";
            password = "";
        }

        public User(string username, string password)
        {
            this.username = username;
            this.password = password;
        }

        // Properties
        public string Username
        {
            get { return username; }
            set { username = value; }
        }

        public string Password
        {
            get { return password; }
            set { password = value; }
        }

        // ToString method
        public override string ToString()
        {
            return $"Username: {username}, Password: {password}";
        }

        // Equals method
        public override bool Equals(object obj)
        {
            if (obj == null || GetType() != obj.GetType())
            {
                return false;
            }

            User otherAgent = (User)obj;
            return username == otherAgent.username && password == otherAgent.password;
        }



    }
}