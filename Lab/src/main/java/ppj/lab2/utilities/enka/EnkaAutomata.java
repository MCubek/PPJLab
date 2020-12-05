package ppj.lab2.utilities.enka;

import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.Symbol;
import ppj.lab2.utilities.Transition;

import java.util.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 05/12/2020
 */
public class EnkaAutomata {
    private final Map<Production, List<Transition<Production>>> transitions;
    private final Map<Production, Set<Symbol>> startingWithMap;
    private final Production startingProduction;

    public EnkaAutomata(List<Production> lrProductions, Map<Production, Set<Symbol>> startingWithMap, Map<Symbol, List<Production>> startingStatesMap) {
        this.startingWithMap = startingWithMap;
        this.startingProduction = lrProductions.get(0);
        this.transitions = generateEnkaTransitions(lrProductions, startingStatesMap);
    }

    /**
     * Stvaranje enka prijelaza
     *
     * @param lrProductions     lr produkcije
     * @param startingStatesMap mapa sa lijevim nezavrsnim simbolom i vrijednostima kao prvim lr stavkama
     * @return mapa s produkcijama kao kljucem i vrijednostima svih produkcija iz tog stanja
     */
    private static Map<Production, List<Transition<Production>>> generateEnkaTransitions(List<Production> lrProductions, Map<Symbol, List<Production>> startingStatesMap) {
        Map<Production, List<Transition<Production>>> transitions = new HashMap<>();

        lrProductions.forEach(production -> {
            var list = transitions.getOrDefault(production, new ArrayList<>());

            Symbol nextSymbol = production.symbolAfterStar();
            if (nextSymbol != null) {

                if (! nextSymbol.isTerminalProduction()) {
                    startingStatesMap.get(nextSymbol).stream()
                            .map(nextProd -> new Transition<Production>(production, Symbol.epsilonSymbol, nextProd))
                            .forEach(list::add);
                }

                list.add(new Transition<Production>(production, nextSymbol, production.getNextInLine(lrProductions)));

            }

            transitions.put(production, list);
        });
        return transitions;
    }

    public Map<Production, List<Transition<Production>>> getTransitions() {
        return transitions;
    }

    public Map<Production, Set<Symbol>> getStartingWithMap() {
        return startingWithMap;
    }

    public Production getStartingProduction() {
        return startingProduction;
    }
}
