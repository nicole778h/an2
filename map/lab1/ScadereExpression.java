public class ScadereExpression extends ComplexExpression {
    public ScadereExpression(NrComplex[] args) {
        super(Operations.sub, args);
    }

    @Override
    public NrComplex executeOneOperation(NrComplex operand1, NrComplex operand2) {
        return operand1.scadere(operand2);
    }
}