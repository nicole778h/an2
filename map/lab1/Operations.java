public enum Operations {
    add("+"),
    sub("-"),
    multipl("*");

    private final String symbol;

    Operations(String symbol) {
        this.symbol = symbol;
    }

    public static Operations fromString(String text) {
//        System.out.println(text);
        for (Operations operation : Operations.values()) {
            if (operation.symbol.equalsIgnoreCase(text)) {
                return operation;
            }
        }
        throw new IllegalArgumentException("No enum constant for string: " + text);
    }
}


