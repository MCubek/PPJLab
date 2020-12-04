package ppj.lab2.utilities;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
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
    private final Integer id;

    public Production(Symbol leftState, LinkedList<Symbol> rightStates) {
        this(leftState, rightStates, - 1);
    }

    public Production(Symbol leftState, LinkedList<Symbol> rightStates, Integer id) {
        if (rightStates.isEmpty())
            throw new IllegalArgumentException("Production must have at least one right element");
        this.leftState = leftState;
        this.rightStates = rightStates;
        this.id = id;
    }

    public Symbol getLeftState() {
        return leftState;
    }

    public List<Symbol> getRightStates() {
        return rightStates;
    }

    public boolean starAtEnd() {
        return rightStates.getLast().equals(Symbol.starSymbol);
    }

    public int indexOfStar() {
        return rightStates.indexOf(Symbol.starSymbol);
    }

    public boolean isEpsilon() {
        return rightStates.size() == 1 && rightStates.getFirst().equals(Symbol.emptySymbol);
    }

    public int rightNumber() {
        return rightStates.size();
    }

    public int getId() {
        return id;
    }

    @Override
    public int compareTo(Production o) {
        return id.compareTo(o.id);
    }

    @Override
    public String toString() {
        return leftState + " -> " + rightStates.stream().map(Symbol::getValue).collect(Collectors.joining(" "));
    }
}
