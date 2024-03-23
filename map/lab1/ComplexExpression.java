public abstract class ComplexExpression {
    private Operations operation;
    protected NrComplex[] args;

    public ComplexExpression(Operations operation, NrComplex[] args) {
        this.operation = operation;
        this.args = args;
    }

    public NrComplex execute() {
        NrComplex rezultat = args[0];

        for (int i = 1; i < args.length; i++) {
            rezultat = executeOneOperation(rezultat, args[i]);
        }

        return rezultat;
    }

    public abstract NrComplex executeOneOperation(NrComplex operand1, NrComplex operand2);
}



        // Metoda abstractă pentru executarea expresiei
       // public abstract NrComplex execute();

        // Metodă Template Method
        //public final NrComplex executeOneOperation() {
            // Implementare pentru execuția unei operații
            //switch (operation) {
                //case add:
                  //  return executeAdunare();
                //case sub:
                 //   return executeScadere();
               // case multipl:
         //           return executeInmultire();
           //     default:
             //       throw new IllegalArgumentException("Operație necunoscută: " + operation);
         //   }
     //   }

        // Metode abstracte pentru operațiile specifice
//    Aste nu trebuie aici, trebuie facute clase separate pentru adunare, scadere, ....
//    care mostenesc din clasa asta. Gen dai override la functia ExecuteOneOperation(), funtia aia de de mai sus
//    nu trebuie sa aiba implementare in clasa asta, trebuie sa fie abstracta.
//    Nu merge fiindca astea de mai jos is declarate abstracte si cand mostenesti din ele trebuie neaparat
//    sa le implemantezi in clasa care mosteneste.
        //protected abstract NrComplex executeAdunare();

        //protected abstract NrComplex executeScadere();

        //protected abstract NrComplex executeInmultire();



