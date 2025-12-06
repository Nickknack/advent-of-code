package nickknack.ca.day6;

public enum Operation {
    ADD("+"),
    MULTIPLY("*");

    private final String symbol;

    Operation(String symbol) {
        this.symbol = symbol;
    }

    public static Operation fromSymbol(String symbol) {
        if (ADD.symbol.equals(symbol)) {
            return ADD;
        }

        if (MULTIPLY.symbol.equals(symbol)) {
            return MULTIPLY;
        }

        throw new IllegalArgumentException("No operation with symbol [%s]".formatted(symbol));
    }

    public String getSymbol() {
        return this.symbol;
    }
}
