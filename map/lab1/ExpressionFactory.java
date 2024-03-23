public class ExpressionFactory {
    private static ExpressionFactory instance;

    private ExpressionFactory() {
        // Constructor privat pentru a asigura singleton
    }

    public static ExpressionFactory getInstance() {
        if (instance == null) {
            instance = new ExpressionFactory();
        }
        return instance;
    }

    public ComplexExpression createExpression(Operations operation, NrComplex[] args) {
        switch (operation) {
            case add:
                return new AdunareExpression(args);
            case sub:
                return new ScadereExpression(args);
            case multipl:
                return new InmultireExpression(args);
            default:
                throw new IllegalArgumentException("Operație necunoscută");
        }
    }
}