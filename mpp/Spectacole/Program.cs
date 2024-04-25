using System;
using System.Windows.Forms;

namespace Spectacole
{
    static class Program
    {
        /// <summary>
        /// The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);

            LoginForm loginForm = new LoginForm();
            Application.Run(loginForm);

            if (loginForm.IsSuccessfullyLoggedIn)
            {
                Form2 mainWindow = new Form2();
                mainWindow.FormClosed += (sender, e) =>
                {
                    Application.Exit();
                }; // Închide aplicația când fereastra MainWindow este închisă
                Application.Run(mainWindow); // Menține aplicația deschisă până când fereastra MainWindow este închisă
            }
        }
    }
}