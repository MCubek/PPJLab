package ppj.lab2.utilities;

import ppj.lab2.utilities.actions.Action;
import ppj.lab2.utilities.actions.PutAction;
import ppj.utilities.Pair;

import java.util.*;
import java.util.stream.Collectors;

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
        this.nonTerminalSymbols = Objects.requireNonNull(nonTerminalSymbols);
        this.terminalSymbols = Objects.requireNonNull(terminalSymbols);
        this.productions = Objects.requireNonNull(productions);
        generateTables();
    }

    public Map<Pair<Integer, String>, Action> getActionTable() {
        return actionTable;
    }

    public Map<Pair<Integer, String>, PutAction> getNewStateTable() {
        return newStateTable;
    }

    /**
     * Metoda generiranja tablica
     */
    private void generateTables() {
        List<Production> lrProductions = generateLrProductions(productions, nonTerminalSymbols.get(0));
        Map<Production, Set<Symbol>> startingWithMap = calculateStartingWith(productions, terminalSymbols);
    }

    private static List<Production> generateLrProductions(List<Production> productions, Symbol initialSymbol) {
        List<Production> lrProductionsList = new ArrayList<>();

        Production startProduction = new Production(Symbol.of("<*S*>", false),
                Symbol.toListOfSymbols(initialSymbol));

        addLrProductionsWithStar(lrProductionsList, startProduction);

        productions.forEach(v -> addLrProductionsWithStar(lrProductionsList, v));

        return lrProductionsList;
    }

    /**
     * Stvaranje produkcija s zvjezdicama
     *
     * @param lrProductionsListFinal lista koju metoda mijenja i gdje stavlja finalne produkcije
     * @param productionOriginal     lista s procitanim produkcijama/originalnim
     */
    private static void addLrProductionsWithStar(List<Production> lrProductionsListFinal, Production productionOriginal) {
        if (productionOriginal.isEpsilon()) {
            lrProductionsListFinal.add(productionOriginal);
            return;
        }

        for (int i = 0; i < productionOriginal.numberOfRightStates() + 1; i++) {
            var rightSymbols = new LinkedList<>(productionOriginal.getRightStates());
            rightSymbols.add(i, Symbol.starSymbol);

            lrProductionsListFinal.add(new Production(productionOriginal.getLeftState(), rightSymbols, productionOriginal.getIndex()));
        }
    }

    private static Map<Production, Set<Symbol>> calculateStartingWith(List<Production> productions, List<Symbol> terminalSymbols) {
        //Prazni znakovi
        Set<Symbol> isEmpty = productions.stream()
                .filter(Production::isEpsilon)
                .map(Production::getLeftState)
                .collect(Collectors.toSet());

        //Zapocinje izravno znakom
        Map<Symbol, Set<Symbol>> startsWith = new HashMap<>();
        productions.forEach(prod -> {
            var set = startsWith.getOrDefault(prod.getLeftState(), new HashSet<>());

            var symbols = Production.findFirstSymbolsUpToEmptyIncluding(prod, isEmpty);
            if (symbols != null)
                set.addAll(symbols);

            startsWith.put(prod.getLeftState(), set);
        });

        //Zapocinje znakom
        //Refleksivna
        startsWith.forEach((key, value) -> value.add(key));
        terminalSymbols.forEach(val -> startsWith.put(val, Set.of(val)));
        boolean hasChanged = true;
        while (hasChanged) {
            hasChanged = false;
            for (Map.Entry<Symbol, Set<Symbol>> entry : startsWith.entrySet()) {
                for (Symbol symbol : entry.getValue()) {
                    //Samo za nezavrsne
                    if (! symbol.isTerminalProduction()) {
                        //Uspjesno dodana nova produkcija/je
                        if (entry.getValue().addAll(startsWith.get(symbol)))
                            hasChanged = true;
                    }
                }
            }
        }

        Map<Production, Set<Symbol>> startsMap = new HashMap<>();
        //Racunaj za svaku produkciju
        productions.forEach(prod -> {
            Set<Symbol> startsSymbols = new HashSet<>();

            //Svaki simbol desne strane trazi iz pocinje sa dok ne dodes do zavrsnog
            for (Symbol symbol : prod.getRightStates()) {
                if (symbol.isEnd()) break;

                startsSymbols.addAll(startsWith.get(symbol));

                if (symbol.isTerminalProduction()) break;
            }

            startsMap.put(prod, ! startsSymbols.isEmpty() ? startsSymbols : null);
        });

        return startsMap;
    }


}
