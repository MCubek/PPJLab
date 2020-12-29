package ppj.lab3.utilities.symbols;

import java.util.Objects;

/**
 * Terminirajuci simbol na stogu.
 *
 * @author MatejCubek
 * @project PPJLab
 * @created 23/12/2020
 */
public class TerminalSymbol implements Symbol {
    private final String symbolName;
    private final int lineNumber;
    private final String[] lexicalUnits;

    public TerminalSymbol(String symbolName, int lineNumber, String[] lexicalUnits) {
        if (lineNumber < 0) throw new IllegalArgumentException();
        this.symbolName = Objects.requireNonNull(symbolName);
        this.lineNumber = lineNumber;
        this.lexicalUnits = Objects.requireNonNull(lexicalUnits);
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String[] getLexicalUnits() {
        return lexicalUnits;
    }

    @Override
    public SYMBOL_TYPE getType() {
        return SYMBOL_TYPE.TERMINAL;
    }

    @Override
    public String getSymbolName() {
        return symbolName;
    }

    @Override
    public String toString() {
        return symbolName + "(" + lineNumber + "," + String.join(" ", lexicalUnits) + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TerminalSymbol that = (TerminalSymbol) o;

        return symbolName.equals(that.symbolName);
    }

    @Override
    public int hashCode() {
        return symbolName.hashCode();
    }
}
