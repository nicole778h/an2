using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SQLite;
using System.Linq;
using System.Windows.Forms;
using lab_mpp3.domain;
using lab_mpp3.repository;
using Spectacole.service;

namespace Spectacole
{
    public partial class Form2 : Form
    {
        private SpectacolService spectacolService;
        private BiletService biletService; // Adaugă câmpul biletService
        private TextBox usernameField;
        private IDictionary<string, string> props;
        private SpectacolRepository spectacolRepository; // Mută variabila aici
        private IDbConnection connection; // Adaugă variabila connection aici

        public Form2()
        {
            InitializeComponent();
            Load += Form2_Load;

            // Inițializează dicționarul de proprietăți și adaugă conexiunea la baza de date
            props = new Dictionary<string, string>();
            props["ConnectionString"] = "Data Source=C:\\Users\\Galdeanu\\RiderProjects\\Spectacole\\identifier.sqlite;Version=3";

            // Inițializarea SpectacolRepository cu dicționarul de proprietăți
            spectacolRepository = new SpectacolRepository(props);

            // Inițializarea SpectacolService cu SpectacolRepository
            spectacolService = new SpectacolService(spectacolRepository);

            // Initializează conexiunea cu baza de date
            connection = new SQLiteConnection(props["ConnectionString"]);

            // Crearea instanței de BiletRepository și inițializarea BiletService cu aceasta
            BiletRepository biletRepository = new BiletRepository(props, spectacolRepository, connection);
            biletService = new BiletService(biletRepository, spectacolRepository);
        }

        private void Form2_Load(object sender, EventArgs e)
        {
            LoadSpectacole();
        }

        private void searchButton_Click(object sender, EventArgs e)
        {
            string searchDateText = searchDateField.Text;

            if (!string.IsNullOrEmpty(searchDateText))
            {
                try
                {
                    // Convertim data introdusă în formatul DateTime
                    DateTime searchData = DateTime.ParseExact(searchDateText, "yyyy-MM-dd", null);
                    Console.WriteLine("Formatul datei parsate: " + searchData.ToString("yyyy-MM-dd"));

                    var spectacole = spectacolService.FindSpectacolByData(searchData);

                    // Verificăm dacă colecția returnată conține elemente
                    if (spectacole != null && spectacole.Any())
                    {
                        spectacolTable.DataSource = spectacole;
                        spectacolTable.Refresh(); // Actualizăm afișarea tabelului
                        Console.WriteLine("Spectacole găsite pentru data specificată.");
                    }
                    else
                    {
                        MessageBox.Show("Nu există spectacole pentru data specificată.");
                    }
                }
                catch (FormatException)
                {
                    MessageBox.Show("Data introdusă nu este în formatul corect (yyyy-MM-dd)!");
                }
                catch (Exception ex)
                {
                    MessageBox.Show("Eroare la efectuarea căutării: " + ex.Message);
                }
            }
            else
            {
                MessageBox.Show("Introduceți o dată pentru căutare!");
            }
        }

        private void LoadSpectacole()
        {
            try
            {
                var spectacole = spectacolService.GetAllSpectacole(); // Utilizează metoda corectă din serviciu pentru a obține toate spectacolele
                spectacolTable.DataSource = spectacole;
                Console.WriteLine("am incarcat spectacolele");
            }
            catch (Exception ex)
            {
                MessageBox.Show("Eroare la afișarea spectacolelor: " + ex.Message);
            }
        }

        private void refreshButton_Click(object sender, EventArgs e)
        {
            LoadSpectacole();
        }

        private void logoutButton_Click(object sender, EventArgs e)
        {
            this.Hide();

            // Creează o nouă instanță a ferestrei de login și o afișează
            Form1 loginForm = new Form1();
            loginForm.Show();
        }

        private void buyTicketsButton_Click(object sender, EventArgs e)
        {
            string buyerName = buyerNameField.Text;
            int numSeats = int.Parse(numSeatsField.Text);

            Spectacol selectedSpectacol = spectacolTable.CurrentRow.DataBoundItem as Spectacol;
            if (selectedSpectacol != null)
            {
                if (numSeats <= selectedSpectacol.NumarLocDisponibile)
                {
                    try
                    {
                        // Achiziționăm biletele utilizând BiletService
                        biletService.BuyTickets(selectedSpectacol.Id, numSeats);

                        // Actualizăm numărul de bilete vândute și disponibile în obiectul selectedSpectacol
                        selectedSpectacol.NumarLocVandute += numSeats;
                        selectedSpectacol.NumarLocDisponibile -= numSeats;

                        // Obținem indexul rândului selectat în tabel
                        int rowIndex = spectacolTable.CurrentRow.Index;

                        // Actualizăm rândul corespunzător din tabel cu noile valori
                        spectacolTable.Rows[rowIndex].Cells["NumarLocDisponibile"].Value = selectedSpectacol.NumarLocDisponibile;
                        spectacolTable.Rows[rowIndex].Cells["NumarLocVandute"].Value = selectedSpectacol.NumarLocVandute;

                        MessageBox.Show($"Bilete achiziționate cu succes pentru spectacolul {selectedSpectacol.NumeArtist}");
                    }
                    catch (Exception ex)
                    {
                        MessageBox.Show($"Eroare la achiziționarea biletelor: {ex.Message}");
                    }
                }
                else
                {
                    MessageBox.Show("Numărul de locuri dorite este mai mare decât numărul de locuri disponibile!");
                }
            }
            else
            {
                MessageBox.Show("Nu a fost selectat niciun spectacol!");
            }
        }
    }
}
