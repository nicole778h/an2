using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Drawing;
using System.Linq;
using System.Windows.Forms;

namespace lab2_sgbd
{
    public partial class Form1 : Form
    {
        private DataGridView dataGrid_Parent;
        private Label tableName_Parent;
        private DataGridView dataGrid_Child;
        private Label tableName_Child;
        private Panel panel_Parent;
        private Panel panel_Child;
        private Button addButton;
        private Button updateButton;
        private Button deleteButton;
        private Label messageToUser;

        // Alte variabile și metode...

        public Form1()
        {
            InitializeComponent();
        }

        // Metodele și evenimentele rămân la fel...

        private void InitializeComponent()
        {
            this.dataGrid_Parent = new DataGridView();
            this.tableName_Parent = new Label();
            this.dataGrid_Child = new DataGridView();
            this.tableName_Child = new Label();
            this.panel_Parent = new Panel();
            this.panel_Child = new Panel();
            this.addButton = new Button();
            this.updateButton = new Button();
            this.deleteButton = new Button();
            this.messageToUser = new Label();

            // Inițializarea componentelor
            this.SuspendLayout();
            // 
            // dataGrid_Parent
            // 
            this.dataGrid_Parent.BackgroundColor = Color.WhiteSmoke;
            this.dataGrid_Parent.BorderStyle = BorderStyle.Fixed3D;
            this.dataGrid_Parent.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGrid_Parent.Location = new Point(41, 74);
            this.dataGrid_Parent.Name = "dataGrid_Parent";
            this.dataGrid_Parent.RowHeadersWidth = 51;
            this.dataGrid_Parent.RowTemplate.Height = 24;
            this.dataGrid_Parent.Size = new Size(361, 197);
            this.dataGrid_Parent.TabIndex = 0;
            this.dataGrid_Parent.CellClick += dataGrid_Parent_CellClick;
            // 
            // tableName_Parent
            // 
            this.tableName_Parent.AutoSize = true;
            this.tableName_Parent.Font = new Font("Microsoft Sans Serif", 12F, FontStyle.Bold, GraphicsUnit.Point);
            this.tableName_Parent.Location = new Point(41, 25);
            this.tableName_Parent.Name = "tableName_Parent";
            this.tableName_Parent.Size = new Size(75, 25);
            this.tableName_Parent.TabIndex = 1;
            this.tableName_Parent.Text = "Parent";
            // 
            // dataGrid_Child
            // 
            this.dataGrid_Child.BackgroundColor = Color.WhiteSmoke;
            this.dataGrid_Child.BorderStyle = BorderStyle.Fixed3D;
            this.dataGrid_Child.ColumnHeadersHeightSizeMode = DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGrid_Child.Location = new Point(464, 65);
            this.dataGrid_Child.Name = "dataGrid_Child";
            this.dataGrid_Child.RowHeadersWidth = 51;
            this.dataGrid_Child.RowTemplate.Height = 24;
            this.dataGrid_Child.Size = new Size(383, 206);
            this.dataGrid_Child.TabIndex = 2;
            this.dataGrid_Child.CellClick += dataGrid_Child_CellClick;
            // 
            // tableName_Child
            // 
            this.tableName_Child.AutoSize = true;
            this.tableName_Child.Font = new Font("Microsoft Sans Serif", 12F, FontStyle.Bold, GraphicsUnit.Point);
            this.tableName_Child.Location = new Point(459, 25);
            this.tableName_Child.Name = "tableName_Child";
            this.tableName_Child.Size = new Size(62, 25);
            this.tableName_Child.TabIndex = 3;
            this.tableName_Child.Text = "Child";
            // 
            // panel_Parent
            // 
            this.panel_Parent.BackColor = Color.WhiteSmoke;
            this.panel_Parent.Location = new Point(46, 392);
            this.panel_Parent.Name = "panel_Parent";
            this.panel_Parent.Size = new Size(356, 216);
            this.panel_Parent.TabIndex = 4;
            // 
            // panel_Child
            // 
            this.panel_Child.BackColor = Color.WhiteSmoke;
            this.panel_Child.Location = new Point(464, 392);
            this.panel_Child.Name = "panel_Child";
            this.panel_Child.Size = new Size(383, 216);
            this.panel_Child.TabIndex = 0;
            // 
            // addButton
            // 
            this.addButton.FlatStyle = FlatStyle.Popup;
            this.addButton.Location = new Point(464, 316);
            this.addButton.Name = "addButton";
            this.addButton.Size = new Size(113, 40);
            this.addButton.TabIndex = 5;
            this.addButton.Text = "Adaugă";
            this.addButton.UseVisualStyleBackColor = true;
            this.addButton.Click += addButton_Click;
            // 
            // updateButton
            // 
            this.updateButton.FlatStyle = FlatStyle.Popup;
            this.updateButton.Location = new Point(598, 316);
            this.updateButton.Name = "updateButton";
            this.updateButton.Size = new Size(113, 40);
            this.updateButton.TabIndex = 6;
            this.updateButton.Text = "Actualizează";
            this.updateButton.UseVisualStyleBackColor = true;
            this.updateButton.Click += updateButton_Click;
            // 
            // deleteButton
            // 
            this.deleteButton.FlatStyle = FlatStyle.Popup;
            this.deleteButton.Location = new Point(734, 316);
            this.deleteButton.Name = "deleteButton";
            this.deleteButton.Size = new Size(113, 40);
            this.deleteButton.TabIndex = 7;
            this.deleteButton.Text = "Șterge";
            this.deleteButton.UseVisualStyleBackColor = true;
            this.deleteButton.Click += deleteButton_Click;
            // 
            // messageToUser
            // 
            this.messageToUser.AutoSize = true;
            this.messageToUser.Location = new Point(12, 503);
            this.messageToUser.Name = "messageToUser";
            this.messageToUser.Size = new Size(0, 20);
            this.messageToUser.TabIndex = 8;
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new SizeF(120F, 120F);
            this.AutoScaleMode = AutoScaleMode.Dpi;
            this.ClientSize = new Size(902, 643);
            this.Controls.Add(messageToUser);
            this.Controls.Add(deleteButton);
            this.Controls.Add(updateButton);
            this.Controls.Add(addButton);
            this.Controls.Add(panel_Child);
            this.Controls.Add(panel_Parent);
            this.Controls.Add(tableName_Child);
            this.Controls.Add(dataGrid_Child);
            this.Controls.Add(tableName_Parent);
            this.Controls.Add(dataGrid_Parent);
            this.Name = "Form1";
            this.Text = "Cabinet Oftalmologic";
            this.Load += Form1_Load;
            ((System.ComponentModel.ISupportInitialize)dataGrid_Parent).EndInit();
            ((System.ComponentModel.ISupportInitialize)dataGrid_Child).EndInit();
            this.ResumeLayout(false);
            this.PerformLayout();
        }
    }
}
