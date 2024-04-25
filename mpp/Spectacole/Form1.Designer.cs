using System;
using System.Data.SQLite;
using System.Windows.Forms;

namespace Spectacole
{
    public partial class Form1 : Form
    {
        private TextBox usernameField;
        private TextBox passwordField;
        private Button loginButton;
        private Label messageLabel;

        public Form1()
        {
            InitializeComponent();
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
            this.usernameField.Location = new System.Drawing.Point(50, 32);
            this.usernameField.Size = new System.Drawing.Size(220, 20);
            this.usernameField.Anchor = AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Top;
            // 
            // passwordField
            // 
            this.passwordField.Location = new System.Drawing.Point(50, 76);
            this.passwordField.Size = new System.Drawing.Size(220, 20);
            this.passwordField.Anchor = AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Top;
            // 
            // loginButton
            // 
            this.loginButton.Location = new System.Drawing.Point(100, 120);
            this.loginButton.Size = new System.Drawing.Size(120, 30);
            this.loginButton.Anchor = AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Top;
            this.loginButton.Text = "Conectare";
            this.loginButton.UseVisualStyleBackColor = true;
            this.loginButton.Click += new System.EventHandler(this.loginButton_Click);
            // 
            // messageLabel
            // 
            this.messageLabel.Location = new System.Drawing.Point(50, 170);
            this.messageLabel.Size = new System.Drawing.Size(220, 20);
            this.messageLabel.Anchor = AnchorStyles.Left | AnchorStyles.Right | AnchorStyles.Top;
            // 
            // LoginForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(320, 200);
            this.Controls.Add(this.messageLabel);
            this.Controls.Add(this.loginButton);
            this.Controls.Add(this.passwordField);
            this.Controls.Add(this.usernameField);
            this.Name = "LoginForm";
            this.Text = "LoginForm";
            this.ResumeLayout(false);
            this.PerformLayout();
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

                    

                    // Închideți fereastra Form1
                  //  this.Close();
                  this.DialogResult = DialogResult.OK;
                  //this.Close();
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
                    }
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare la conectare la baza de date: " + ex.Message);
                    return false;
                }
            }
        }
      
    }
}
