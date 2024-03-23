public class AdunareExpression extends ComplexExpression {
    public AdunareExpression(NrComplex[] args) {
        super(Operations.add, args);
    }

    @Override
    public NrComplex executeOneOperation(NrComplex operand1, NrComplex operand2) {
       return operand1.adunare(operand2);
    }
}