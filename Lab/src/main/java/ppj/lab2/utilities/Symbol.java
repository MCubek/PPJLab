package ppj.lab2.utilities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 04/12/2020
 */
public class Symbol implements Serializable {

    private static final long serialVersionUID = 1688093216125961299L;

    private final String value;
    private final boolean terminal;

    public static final Symbol starSymbol = new Symbol("*", true);
    public static final Symbol emptySymbol = new Symbol("$", true);

    public static Symbol of(String value, boolean terminal) {
        if (value.equals("*")) return starSymbol;
        if (value.equals("$")) return emptySymbol;

        return new Symbol(value, terminal);
    }

    private Symbol(String value, boolean terminal) {
        this.value = Objects.requireNonNull(value);
        this.terminal = terminal;
    }


    public String getValue() {
        return value;
    }

    public boolean isTerminal() {
        return terminal;
    }

    public boolean isStar() {
        return equals(starSymbol);
    }

    public boolean isEmpty() {
        return equals(emptySymbol);
    }

    public static LinkedList<Symbol> toListSymbols(String... symbols) {
        return Arrays.stream(symbols)
                .map(s -> Symbol.of(s, ! (s.startsWith("<") && s.endsWith(">"))))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Symbol symbol = (Symbol) o;

        if (terminal != symbol.terminal) return false;
        return value.equals(symbol.value);
    }

    @Override
    public int hashCode() {
        int result = value.hashCode();
        result = 31 * result + (terminal ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return value;
    }
}
