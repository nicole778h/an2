using System;
using System.Configuration;
using System.Windows.Forms;

namespace lab2_sgbd
{
    internal static class Program
    {
        /// <summary>
        ///  The main entry point for the application.
        /// </summary>
        [STAThread]
        static void Main()
        {
            // Citirea valorilor din fișierul de configurare
            string settingValue = ConfigurationManager.AppSettings["SettingKey"];

            // Inițializează aplicația și pornește formularul principal
            Application.EnableVisualStyles();
            Application.SetCompatibleTextRenderingDefault(false);
            Application.Run(new Form1());
        }
    }
}