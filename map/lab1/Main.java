import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduceți expresia de analizat: ");
        String expresie = scanner.nextLine();

        ExpressionParser parser = new ExpressionParser();

        try {
            ComplexExpression complexExpression = parser.parseExpression(expresie);
            NrComplex rezultat = complexExpression.execute();
            System.out.println("Rezultat: " + rezultat);
        } catch (InvalidExpressionException e) {
            System.err.println("Expresie invalidă: " + e.getMessage());
        }

        scanner.close();
    }
}