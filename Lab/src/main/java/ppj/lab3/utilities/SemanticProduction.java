package ppj.lab3.utilities;

import ppj.lab3.utilities.attributes.Attribute;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.Symbol;
import ppj.utilities.Node;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 27/12/2020
 */
public class SemanticProduction {
    private final Node<Symbol> leftState;
    private final List<Node<Symbol>> rightStates;

    public SemanticProduction(Node<Symbol> leftStateNode) {
        if (! (leftStateNode.getValue() instanceof NonTerminalSymbol))
            throw new IllegalArgumentException("Left state must be nonTerminal.");

        this.leftState = leftStateNode;
        this.rightStates = generateRightStates(leftStateNode);
    }

    private SemanticProduction(Node<Symbol> leftState, List<Node<Symbol>> rightStatesNode) {
        this.leftState = leftState;
        this.rightStates = rightStatesNode;
    }

    public String getLeftStateValue() {
        return leftState.getValue().getSymbolName();
    }

    public NonTerminalSymbol getLeftState() {
        return (NonTerminalSymbol) leftState.getValue();
    }

    public List<String> getRightStateValues() {
        return rightStates.stream()
                .map(v -> v.getValue().getSymbolName())
                .collect(Collectors.toList());
    }

    public List<Symbol> getRightStates() {
        return rightStates.stream()
                .map(Node::getValue)
                .collect(Collectors.toList());
    }

    public Node<Symbol> getLeftStateNode() {
        return leftState;
    }

    public List<Node<Symbol>> getRightStateNodes() {
        return rightStates;
    }

    public static List<Node<Symbol>> generateRightStates(Node<Symbol> leftState) {
        return List.copyOf(leftState.getChildren());
    }

    public Map<String, Attribute> getAttributesFromRightStatesWhenAllFunctionsFinished() {
        Map<String, Attribute> map = new HashMap<>();

        rightStates.stream()
                .map(Node::getValue)
                .filter(symbol -> symbol instanceof NonTerminalSymbol)
                .map(symbol -> ((NonTerminalSymbol) symbol).getAttributeMap())
                .forEach(map::putAll);

        return map;
    }

    public static SemanticProduction generateMapKeyProduction(Symbol leftState, Symbol... rightStates) {
        Node<Symbol> leftStateNode = new Node<>(leftState);
        var rightStateNodes = Arrays.stream(rightStates)
                .map(Node::new)
                .collect(Collectors.toList());
        return new SemanticProduction(leftStateNode, rightStateNodes);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SemanticProduction that = (SemanticProduction) o;

        if (! getLeftStateValue().equals(that.getLeftStateValue())) return false;
        return getRightStateValues().equals(that.getRightStateValues());
    }

    @Override
    public int hashCode() {
        int result = getLeftStateValue().hashCode();
        result = 31 * result + getRightStateValues().hashCode();
        return result;
    }

    @Override
    public String toString() {
        return String.format("%s -> ", getLeftStateValue()) + String.join(" ", getRightStateValues());
    }
}
