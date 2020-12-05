package ppj.lab2.utilities;

import ppj.lab2.utilities.actions.Action;
import ppj.lab2.utilities.actions.PutAction;
import ppj.lab2.utilities.enka.EnkaAutomata;
import ppj.utilities.Pair;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 04/12/2020
 */
public class ParserGen {
    private final List<Symbol> nonTerminalSymbols;
    private final List<Symbol> terminalSymbols;
    private final List<Production> productions;

    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, PutAction> newStateTable;

    private Set<Symbol> emptySymbols;
    private Map<Symbol, Set<Symbol>> startingWith;

    public ParserGen(List<Symbol> nonTerminalSymbols, List<Symbol> terminalSymbols, List<Production> productions) {
        this.nonTerminalSymbols = Objects.requireNonNull(nonTerminalSymbols);
        this.terminalSymbols = Objects.requireNonNull(terminalSymbols);
        this.productions = Objects.requireNonNull(productions);

        emptySymbols = ParserGen.calculateIsEmpty(productions);
        startingWith = ParserGen.calculateStartingWith(productions, terminalSymbols, emptySymbols);

        generateTables();
    }

    private void generateEmptyAndStartingWith() {
        emptySymbols = ParserGen.calculateIsEmpty(productions);
        startingWith = ParserGen.calculateStartingWith(productions, terminalSymbols, emptySymbols);
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
        List<Production> lrProductions = generateLrProductions(nonTerminalSymbols.get(0));
        Map<Symbol, List<Production>> startingStatesMap = generateStartingStatesMap(lrProductions);
        EnkaAutomata enka = new EnkaAutomata(lrProductions, startingStatesMap);
    }

    /**
     * Metoda koja generira LR stavke
     *
     * @param initialSymbol prvo stanje
     * @return lista LR stavaka
     */
    private List<Production> generateLrProductions(Symbol initialSymbol) {
        List<Production> lrProductionsList = new ArrayList<>();

        Production startProduction = Production.getStartProduction(initialSymbol);

        addLrProductionsWithStar(lrProductionsList, startProduction);

        productions.forEach(v -> addLrProductionsWithStar(lrProductionsList, v));

        return lrProductionsList;
    }

    /**
     * Metoda generira mapu poocetnih LR stavki za lakse pretrazivanje
     *
     * @param lrProductions lrProdukcije s stavkama
     * @return Mapa sa nezavrsnim znakom i prvim produkcijama
     */
    private static Map<Symbol, List<Production>> generateStartingStatesMap(List<Production> lrProductions) {
        Map<Symbol, List<Production>> map = new HashMap<>();

        lrProductions.forEach(prod -> {
            var list = map.getOrDefault(prod.getLeftState(), new ArrayList<>());
            if (prod.isSymbolsStartProduction() || prod.isEpsilon())
                list.add(prod);

            map.put(prod.getLeftState(), list);
        });

        return map;
    }

    /**
     * Stvaranje lr stavci za jednu produkciju
     *
     * @param lrProductionsListFinal lista koju metoda mijenja i gdje stavlja finalne produkcije
     * @param productionOriginal     lista s procitanim produkcijama/originalnim
     */
    private static void addLrProductionsWithStar(List<Production> lrProductionsListFinal, Production productionOriginal) {
        // TODO: 5.12.2020. Skup znakova !!!
        // Koristiti Production.copyOfWithStartsList
        // Takoder ParserGen.startsWithForProduction vraca sve znakove iz skupa zapocinje sa za dani znakove
        // Kod ispod je star



/*        if (productionOriginal.isEpsilon()) {
            lrProductionsListFinal.add(productionOriginal);
            return;
        }

        for (int i = 0; i < productionOriginal.numberOfRightStates() + 1; i++) {
            var rightSymbols = new LinkedList<>(productionOriginal.getRightStates());
            rightSymbols.add(i, Symbol.starSymbol);

            lrProductionsListFinal.add(new Production(productionOriginal.getLeftState(), rightSymbols, productionOriginal.getIndex()));
        }*/

    }

    public static Set<Symbol> calculateIsEmpty(Collection<Production> productions) {
        return productions.stream()
                .filter(Production::isEpsilon)
                .map(Production::getLeftState)
                .collect(Collectors.toSet());
    }

    /**
     * Metoda kooja racuna skup zapocinje sa za svaki znak
     *
     * @param productions     ulazne produkcije
     * @param terminalSymbols zavrsni znakovi
     * @return mapa sa simbolom kao kljucem i setom znakova iz skupa zapocinje sa kao vrijednosti
     */
    public static Map<Symbol, Set<Symbol>> calculateStartingWith(List<Production> productions, List<Symbol> terminalSymbols, Set<Symbol> isEmpty) {
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

                var symbols = new HashSet<>(entry.getValue());
                for (Symbol symbol : symbols) {
                    //Samo za nezavrsne
                    if (! symbol.isTerminalProduction() && ! symbol.equals(entry.getKey())) {
                        //Uspjesno dodana nova produkcija/je
                        if (entry.getValue().addAll(startsWith.get(symbol)))
                            hasChanged = true;
                    }
                }
            }
        }

        return startsWith;
    }

    public static Collection<Symbol> startsWithForProduction(Map<Symbol, Set<Symbol>> startsWith, Collection<Symbol> isEmpty, Production production) {
        if (! production.hasStar()) throw new IllegalArgumentException("Production doesn't have star.");
        var rightStates = production.getRightStates();

        var rightStatesAfterStar = rightStates.subList(production.indexOfStar() + 1, rightStates.size() - 1);

        Set<Symbol> set = new HashSet<>();
        //Svaki simbol desne strane trazi iz pocinje sa dok ne dodes do zavrsnog
        for (Symbol symbol : rightStatesAfterStar) {
            if (symbol.isEnd()) break;

            set.addAll(startsWith.get(symbol).stream()
                    .filter(Symbol::isTerminalProduction)
                    .collect(Collectors.toSet()));

            if (symbol.isTerminalProduction() || ! isEmpty.contains(symbol)) break;
        }

        return set;
    }

    public Collection<Symbol> startsWithForProduction(Production production) {
        return startsWithForProduction(startingWith, emptySymbols, production);
    }


}
