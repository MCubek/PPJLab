package ppj.lab2.utilities;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class Production implements Serializable, Comparable<Production> {
    private static final long serialVersionUID = - 162859005435295656L;
    private final Symbol leftState;
    private final LinkedList<Symbol> rightStates;
    private final Integer index;

    public Production(Symbol leftState, LinkedList<Symbol> rightStates) {
        this(leftState, rightStates, - 1);
    }

    public Production(Symbol leftState, LinkedList<Symbol> rightStates, Integer index) {
        if (rightStates.isEmpty())
            throw new IllegalArgumentException("Production must have at least one right element");
        this.leftState = leftState;
        this.rightStates = rightStates;
        this.index = index;
    }

    public Symbol getLeftState() {
        return leftState;
    }

    public List<Symbol> getRightStates() {
        return rightStates;
    }

    public boolean starAtEnd() {
        return rightStates.getLast().equals(Symbol.starSymbol) || isEpsilon();
    }

    public int indexOfStar() {
        return rightStates.indexOf(Symbol.starSymbol);
    }

    public boolean isEpsilon() {
        return rightStates.size() == 1 && rightStates.getFirst().equals(Symbol.endSymbol);
    }

    public int numberOfRightStates() {
        return rightStates.size();
    }

    public int getIndex() {
        return index;
    }

    @Override
    public int compareTo(Production o) {
        return index.compareTo(o.index);
    }

    @Override
    public String toString() {
        return leftState + " -> " + rightStates.stream().map(Symbol::getValue).collect(Collectors.joining(" "));
    }

    public static Set<Symbol> findFirstSymbolsUpToEmptyIncluding(Production production, Set<Symbol> emptySymbols) {
        Set<Symbol> symbols = new HashSet<>();

        if (production.isEpsilon()) return null;

        for (Symbol symbol : production.getRightStates()) {
            symbols.add(symbol);
            if (symbol.isTerminalProduction()) return symbols;
            if (! emptySymbols.contains(symbol)) return symbols;
        }
        return null;
    }

    public Symbol symbolAfterStar() {
        int index = indexOfStar();
        if (index == - 1 || starAtEnd()) {
            return null;
        }

        return rightStates.get(index + 1);
    }

    public boolean isSymbolsStartProduction() {
        return indexOfStar() == 0;
    }

    public static Production findInCollectionByRightStates(Collection<Production> collection, List<Symbol> rightSymbols) {
        TreeSet<Production> foundProductions =
                collection.stream()
                        .filter(prod -> prod.rightStates.equals(rightSymbols))
                        .collect(Collectors.toCollection(TreeSet::new));

        return foundProductions.first();
    }

    public Production getNextInLine(Collection<Production> collection) {
        int index = this.indexOfStar();
        var rightStates = new LinkedList<>(this.rightStates);
        rightStates.remove(index);
        rightStates.add(index + 1, Symbol.starSymbol);

        return findInCollectionByRightStates(collection, rightStates);
    }
}
