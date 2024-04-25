using System;
using System.Data.SQLite;
using System.Windows.Forms;

namespace Spectacole
{
    public partial class LoginForm : Form
    {
        private TextBox usernameField;
        private TextBox passwordField;
        private Button loginButton;
        private Label messageLabel;
        public bool IsSuccessfullyLoggedIn { get; private set; }
        public LoginForm()
        {
            InitializeComponent();
        }

        private void loginButton_Click(object sender, EventArgs e)
        {
            string username = usernameField.Text;
            string password = passwordField.Text;

            if (!string.IsNullOrEmpty(username) && !string.IsNullOrEmpty(password) && !string.IsNullOrEmpty(password.Trim()))
            {
                // Verificați datele de conectare în baza de date
                if (VerificaDateConectare(username, password))
                {
                    MessageBox.Show("Autentificare reușită!");

                    // Deschideți fereastra Form2
                    // Deschideți fereastra Form2
                    Form2 form2 = new Form2();
                    form2.Show();


                    // Închideți fereastra Form1
                   // this.Close();
                }
                else
                {
                    MessageBox.Show("Nume de utilizator sau parolă incorecte!");
                }
            }
            else
            {
                MessageBox.Show("Introduceți utilizator și parolă!");
            }
        }


      


        private bool VerificaDateConectare(string username, string password)
        {
            string connectionString = "Data Source=C:\\Users\\Galdeanu\\RiderProjects\\lab_mpp3\\identifier.sqlite;Version=3";
    
            using (SQLiteConnection connection = new SQLiteConnection(connectionString))
            {
                try
                {
                    connection.Open();
                    string query = "SELECT COUNT(*) FROM users WHERE username = @username AND password = @password";

                    using (SQLiteCommand command = new SQLiteCommand(query, connection))
                    {
                        command.Parameters.AddWithValue("@username", username);
                        command.Parameters.AddWithValue("@password", password);

                        int count = Convert.ToInt32(command.ExecuteScalar());

                        if (count > 0)
                        {
                            MessageBox.Show("Autentificare reușită pentru utilizatorul: " + username);
                            return true;
                            
                        }
                        else
                        {
                            MessageBox.Show("Nume de utilizator sau parolă incorecte!");
                            return false;
                        }
                    } connection.Close();
                } 
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare la conectare la baza de date: " + ex.Message);
                    return false;
                }
            }
        }



        private void InitializeComponent()
        {
            this.usernameField = new TextBox();
            this.passwordField = new TextBox();
            this.loginButton = new Button();
            this.messageLabel = new Label();
            this.SuspendLayout();
            // 
            // usernameField
            // 
            this.usernameField.Location = new System.Drawing.Point(50, 50);
            this.usernameField.Name = "usernameField";
            this.usernameField.Size = new System.Drawing.Size(200, 20);
            this.usernameField.TabIndex = 0;
            // 
            // passwordField
            // 
            this.passwordField.Location = new System.Drawing.Point(50, 100);
            this.passwordField.Name = "passwordField";
            this.passwordField.PasswordChar = '*';
            this.passwordField.Size = new System.Drawing.Size(200, 20);
            this.passwordField.TabIndex = 1;
            // 
            // loginButton
            // 
            this.loginButton.Location = new System.Drawing.Point(50, 150);
            this.loginButton.Name = "loginButton";
            this.loginButton.Size = new System.Drawing.Size(75, 23);
            this.loginButton.TabIndex = 2;
            this.loginButton.Text = "Login";
            this.loginButton.UseVisualStyleBackColor = true;
            this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
            // 
            // messageLabel
            // 
            this.messageLabel.AutoSize = true;
            this.messageLabel.Location = new System.Drawing.Point(50, 200);
            this.messageLabel.Name = "messageLabel";
            this.messageLabel.Size = new System.Drawing.Size(0, 13);
            this.messageLabel.TabIndex = 3;
            // 
            // LoginForm
            // 
            this.ClientSize = new System.Drawing.Size(284, 261);
            this.Controls.Add(this.messageLabel);
            this.Controls.Add(this.loginButton);
            this.Controls.Add(this.passwordField);
            this.Controls.Add(this.usernameField);
            this.Name = "LoginForm";
            this.Text = "Login";
            this.ResumeLayout(false);
            this.PerformLayout();

        }
    }
}
