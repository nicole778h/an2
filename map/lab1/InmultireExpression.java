public class InmultireExpression extends ComplexExpression {
    public InmultireExpression(NrComplex[] args) {
        super(Operations.multipl, args);
    }

    @Override
    public NrComplex executeOneOperation(NrComplex operand1, NrComplex operand2) {
        return operand1.inmultire(operand2);
    }
}