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
    public static final Symbol endSymbol = new Symbol("$", true);
    public static final Symbol epsilonSymbol = new Symbol("ɛ", true);

    public static Symbol of(String value, boolean terminal) {
        if (value.equals("*")) return starSymbol;
        if (value.equals("$")) return endSymbol;
        if (value.equals("ɛ")) return epsilonSymbol;

        return new Symbol(value, terminal);
    }

    private Symbol(String value, boolean terminal) {
        this.value = Objects.requireNonNull(value);
        this.terminal = terminal;
    }


    public String getValue() {
        return value;
    }

    public boolean isTerminalProduction() {
        return terminal;
    }

    public boolean isStar() {
        return equals(starSymbol);
    }

    public boolean isEnd() {
        return equals(endSymbol);
    }

    public static LinkedList<Symbol> toListOfSymbols(String... symbolsAsStrings) {
        return Arrays.stream(symbolsAsStrings)
                .map(s -> Symbol.of(s, ! (s.startsWith("<") && s.endsWith(">"))))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static LinkedList<Symbol> toListOfSymbols(Symbol... symbolsAsStrings) {
        return Arrays.stream(symbolsAsStrings)
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
