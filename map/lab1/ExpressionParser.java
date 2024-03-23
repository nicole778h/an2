public class ExpressionParser {
    private ExpressionFactory factory;

    public ExpressionParser() {
        this.factory = ExpressionFactory.getInstance();
    }

    public ComplexExpression parseExpression(String input) throws InvalidExpressionException {
        // Elimin spațiile albe inutile din șirul de intrare
        input = input.replaceAll("\\s", "");

        // Verific dacă șirul de intrare este gol
        if (input.isEmpty()) {
            throw new InvalidExpressionException("Expresia este goală.");
        }

        // Impart șirul de intrare în funcție de operatori (+, -, *, /)
        String[] tokens = input.split("[+\\-*/]");

        // Verific dacă șirul de intrare conține cel puțin un operator
        if (tokens.length < 2) {
            throw new InvalidExpressionException("Expresia trebuie să conțină cel puțin un operator.");
        }

        // Identific operatorul din șirul de intrare
        Operations operation = null;
        for (char operator : "+-*".toCharArray()) {
            if (input.contains(String.valueOf(operator))) {
                operation = Operations.fromString(String.valueOf(operator));
                break;
            }
        }

        // Verific dacă s-a găsit un operator
        if (operation == null) {
            throw new InvalidExpressionException("Nu s-a găsit niciun operator valid în expresie.");
        }

        // Pregătesc vectorul de argumente (numere complexe)
        NrComplex[] args = new NrComplex[tokens.length];
        for (int i = 0; i < tokens.length; i++) {
            args[i] = parseComplexNumber(tokens[i]);
        }

        // Utilizezfactory pentru a crea expresia corespunzătoare
//        System.out.println(operation);
        return factory.createExpression(operation, args);
    }

    private NrComplex parseComplexNumber(String input) {
        // Implement analiza șirului de caractere pentru a extrage partea reală și partea imaginară și creez un obiect NumarComplex corespunzător
        // Acest lucru depinde de formatul în care sunt specificate numerele complexe în șirul de intrare.
        // exemplu simplificat pentru un format "a + bi":
        if (input.isEmpty() || input.equals("+") || input.equals("-") || input.equals("i")) {
            throw new IllegalArgumentException("Format invalid pentru număr complex: " + input);}
        // Verific dacă șirul conține caracterul 'i'
        if (input.contains("i")) {
            // Elimin caracterul 'i' și apoi divizăm șirul în funcție de semnul '+' sau '-'
            String[] parts = input.replaceAll("i", "").split("[+-]");
            if (parts.length == 1) {
                double parteImaginara = Double.parseDouble(parts[0]);
                return new NrComplex(0.0, parteImaginara);
            } else if (parts.length == 2) {
                double parteReala = Double.parseDouble(parts[0]);
                double parteImaginara = Double.parseDouble(parts[1]);
                return new NrComplex(parteReala, parteImaginara);
            }
        } else {
            // Nu există parte imaginara, deci șirul reprezintă doar partea reală
            double parteReala = Double.parseDouble(input);
            return new NrComplex(parteReala, 0.0);
        }

        //  excepție în cazul unui format invalid
        throw new IllegalArgumentException("Format invalid pentru număr complex: " + input);
    }
}