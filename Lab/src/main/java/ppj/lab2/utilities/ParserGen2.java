package ppj.lab2.utilities;

import ppj.lab2.utilities.actions.Action;
import ppj.lab2.utilities.actions.PutAction;
import ppj.utilities.Pair;

import java.util.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 04/12/2020
 */
public class ParserGen2 {
    private final List<Symbol> nonTerminalSymbols;
    private final List<Symbol> terminalSymbols;
    private final List<Production> productions;

    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, PutAction> newStateTable;

    public ParserGen2(List<Symbol> nonTerminalSymbols, List<Symbol> terminalSymbols, List<Production> productions) {
        this.nonTerminalSymbols = nonTerminalSymbols;
        this.terminalSymbols = terminalSymbols;
        this.productions = productions;
        generateTables();
    }

    public Map<Pair<Integer, String>, Action> getActionTable() {
        return actionTable;
    }

    public Map<Pair<Integer, String>, PutAction> getNewStateTable() {
        return newStateTable;
    }

    private void generateTables() {
        List<Production> lrProductions = generateLrProductions(productions, nonTerminalSymbols.get(0));
    }

    private Map<Production, Integer> generateLrProductions(Map<Production, Integer> productions, String initialSymbol) {
        var copy = new HashMap<>();
        copy.put(new Production("<S*>", initialSymbol), - 1);
        copy.

        return copy;
    }

}
