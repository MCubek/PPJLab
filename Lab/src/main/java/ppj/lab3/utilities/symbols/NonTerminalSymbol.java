package ppj.lab3.utilities.symbols;

import java.util.Objects;

/**
 * Nezavrsni simbol u stablu.
 *
 * @author MatejCubek
 * @project PPJLab
 * @created 23/12/2020
 */
public class NonTerminalSymbol implements Symbol {
    private final String symbolName;

    public NonTerminalSymbol(String symbolName) {
        this.symbolName = Objects.requireNonNull(symbolName);
    }

    @Override
    public SYMBOL_TYPE getType() {
        return SYMBOL_TYPE.NON_TERMINAL;
    }

    @Override
    public String getSymbolName() {
        return symbolName;
    }

    @Override
    public String toString() {
        return symbolName;
    }
}
