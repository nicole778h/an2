using System.Windows.Forms;

namespace Spectacole
{
    partial class Form2
    {   private DataGridView spectacolTable;
        private Label searchDateLabel;
        private TextBox searchDateField;
        private Button searchButton;
        private Button refreshButton;
        private Label achizitionareLabel;
        private Label buyerNameLabel;
        private TextBox buyerNameField;
        private Label numSeatsLabel;
        private TextBox numSeatsField;
        private Button buyTicketsButton;
        private Button logoutButton;
      

      

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.spectacolTable = new System.Windows.Forms.DataGridView();
            this.searchDateLabel = new System.Windows.Forms.Label();
            this.searchDateField = new System.Windows.Forms.TextBox();
            this.searchButton = new System.Windows.Forms.Button();
            this.refreshButton = new System.Windows.Forms.Button();
            this.achizitionareLabel = new System.Windows.Forms.Label();
            this.buyerNameLabel = new System.Windows.Forms.Label();
            this.buyerNameField = new System.Windows.Forms.TextBox();
            this.numSeatsLabel = new System.Windows.Forms.Label();
            this.numSeatsField = new System.Windows.Forms.TextBox();
            this.buyTicketsButton = new System.Windows.Forms.Button();
            this.logoutButton = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.spectacolTable)).BeginInit();
            this.SuspendLayout();
            
            // Adăugare coloane pentru spectacole
           // spectacolTable.Columns.Add("id", "ID");
            //spectacolTable.Columns.Add("data", "Data");
            //spectacolTable.Columns.Add("loc", "Loc");
            //spectacolTable.Columns.Add("numarLocDisponibile", "Număr Locuri Disponibile");
            //spectacolTable.Columns.Add("numarLocVandute", "Număr Locuri Vandute");
            //spectacolTable.Columns.Add("numeArtist", "Nume Artist");

            // 
            // spectacolTable
            // 
            this.spectacolTable.Location = new System.Drawing.Point(50, 50);
            this.spectacolTable.Size = new System.Drawing.Size(700, 200);
            // 
            // searchDateLabel
            // 
            DataGridViewTextBoxColumn idColumn = new DataGridViewTextBoxColumn();
            idColumn.DataPropertyName = "id"; // înlocuiește "ID" cu numele real al coloanei ID din sursa de date
            idColumn.HeaderText = "id";
            spectacolTable.Columns.Insert(0, idColumn);
            this.searchDateLabel.Location = new System.Drawing.Point(50, 270);
            this.searchDateLabel.Text = "Căutare spectacol după dată:";
            // 
            // searchDateField
            // 
            this.searchDateField.Location = new System.Drawing.Point(220, 270);
            this.searchDateField.Size = new System.Drawing.Size(150, 20);
            DataGridViewTextBoxColumn dataColumn = new DataGridViewTextBoxColumn();
            dataColumn.DataPropertyName = "Data";
            dataColumn.HeaderText = "Data";
            //dataColumn.DefaultCellStyle.Format = "yyyy-MM-dd HH:mm:ss";
           dataColumn.DefaultCellStyle.Format = "yyyy-MM-dd HH:mm:ss";

           // dataColumn.DefaultCellStyle.Format = "yyyy-MM-dd"; // Setarea formatului de afișare a datei
            spectacolTable.Columns.Add(dataColumn);
            // 
            // searchButton
            // 
            this.searchButton.Location = new System.Drawing.Point(400, 270);
            this.searchButton.Size = new System.Drawing.Size(65, 25);
            this.searchButton.Text = "Caută";
            this.searchButton.Click += new System.EventHandler(this.searchButton_Click);
            // 
            // refreshButton
            // 
            this.refreshButton.Location = new System.Drawing.Point(470, 270);
            this.refreshButton.Size = new System.Drawing.Size(65, 25);
            this.refreshButton.Text = "Refresh";
            this.refreshButton.Click += new System.EventHandler(this.refreshButton_Click);
            // 
            // achizitionareLabel
            // 
            this.achizitionareLabel.Location = new System.Drawing.Point(50, 480);
            this.achizitionareLabel.Text = "Achiziționare bilete";
            // 
            // buyerNameLabel
            // 
            this.buyerNameLabel.Location = new System.Drawing.Point(50, 510);
            this.buyerNameLabel.Text = "Nume cumpărător:";
            // 
            // buyerNameField
            // 
            this.buyerNameField.Location = new System.Drawing.Point(180, 510);
            this.buyerNameField.Size = new System.Drawing.Size(150, 20);
            // 
            // numSeatsLabel
            // 
            this.numSeatsLabel.Location = new System.Drawing.Point(50, 540);
            this.numSeatsLabel.Text = "Număr locuri:";
            // 
            // numSeatsField
            // 
            this.numSeatsField.Location = new System.Drawing.Point(180, 540);
            this.numSeatsField.Size = new System.Drawing.Size(50, 20);
            // 
            // buyTicketsButton
            // 
            this.buyTicketsButton.Location = new System.Drawing.Point(300, 540);
            this.buyTicketsButton.Size = new System.Drawing.Size(100, 25);
            this.buyTicketsButton.Text = "Achiziționează";
            this.buyTicketsButton.Click += new System.EventHandler(this.buyTicketsButton_Click);
            // 
            // logoutButton
            // 
            this.logoutButton.Location = new System.Drawing.Point(50, 570);
            this.logoutButton.Size = new System.Drawing.Size(100, 25);
            this.logoutButton.Text = "Logout";
            this.logoutButton.Click += new System.EventHandler(this.logoutButton_Click);
            // 
            // Form2
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(6F, 13F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(800, 600);
            this.Controls.Add(this.logoutButton);
            this.Controls.Add(this.buyTicketsButton);
            this.Controls.Add(this.numSeatsField);
            this.Controls.Add(this.numSeatsLabel);
            this.Controls.Add(this.buyerNameField);
            this.Controls.Add(this.buyerNameLabel);
            this.Controls.Add(this.achizitionareLabel);
            this.Controls.Add(this.refreshButton);
            this.Controls.Add(this.searchButton);
            this.Controls.Add(this.searchDateField);
            this.Controls.Add(this.searchDateLabel);
            this.Controls.Add(this.spectacolTable);
            this.Name = "Form2";
            this.Text = "Form2";
            ((System.ComponentModel.ISupportInitialize)(this.spectacolTable)).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

       
    }
}
