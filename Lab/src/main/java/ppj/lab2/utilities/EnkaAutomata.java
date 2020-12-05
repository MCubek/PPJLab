package ppj.lab2.utilities;

import java.util.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 05/12/2020
 */
public class EnkaAutomata {
    private Map<Production, List<EnkaTransition>> transitions;
    private Map<Production, Set<Symbol>> startingWithMap;

    public EnkaAutomata(List<Production> lrProductions, Map<Production, Set<Symbol>> startingWithMap, Map<Symbol, List<Production>> startingStatesMap) {
        this.startingWithMap = startingWithMap;
        this.transitions = generateEnkaTransitions(lrProductions, startingStatesMap);
    }

    private Map<Production, List<EnkaTransition>> generateEnkaTransitions(List<Production> lrProductions, Map<Symbol, List<Production>> startingStatesMap) {
        Map<Production, List<EnkaTransition>> transitions = new HashMap<>();

        lrProductions.forEach(production -> {
            var list = transitions.getOrDefault(production, new ArrayList<>());

            Symbol nextSymbol = production.symbolAfterStar();
            if (nextSymbol != null) {

                if (! nextSymbol.isTerminalProduction()) {
                    startingStatesMap.get(nextSymbol).stream()
                            .map(nextProd -> new EnkaTransition(production, Symbol.epsilonSymbol, nextProd))
                            .forEach(list::add);
                }

                list.add(new EnkaTransition(production, nextSymbol, production.getNextInLine(lrProductions)));

            }

            transitions.put(production, list);
        });
        return transitions;
    }
}
