using Laborator_1;

namespace sgbd_lab1
{
   
        internal static class Program
        {
            /// <summary>
            ///  The main entry point for the application.
            /// </summary>
            [STAThread]
            static void Main()
            {

                ApplicationConfiguration.Initialize();
                Application.Run(new Bysh());
            }
        }
    }
