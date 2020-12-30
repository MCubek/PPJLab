package ppj.lab3.utilities.symbols;

import ppj.lab3.utilities.attributes.Attribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Nezavrsni simbol u stablu.
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 23/12/2020
 */
public class NonTerminalSymbol implements Symbol {
    private final String symbolName;
    private final Map<String, Attribute> attributeMap;

    public NonTerminalSymbol(String symbolName) {
        this.symbolName = Objects.requireNonNull(symbolName);
        this.attributeMap = new HashMap<>();
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

    public Map<String, Attribute> getAttributeMap() {
        return attributeMap;
    }

    public void addAttribute(String key, Attribute attribute) {
        attributeMap.put(key, attribute);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NonTerminalSymbol that = (NonTerminalSymbol) o;

        return symbolName.equals(that.symbolName);
    }

    @Override
    public int hashCode() {
        return symbolName.hashCode();
    }

}
