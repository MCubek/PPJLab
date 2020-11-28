package ppj.utilities;

import java.util.*;

public class ParserGenerator {

    private Map<String, List<List<String>>> LRProductions;
    private List<String> entrySymbols;

    public ParserGenerator(Map<String, List<List<String>>> initialProductions, List<String> terminalSymbols, List<String> nonTerminalSymbols) {
        this.entrySymbols = new ArrayList<>(terminalSymbols);
        this.entrySymbols.addAll(nonTerminalSymbols);
        this.LRProductions = generateAllLRProductions(Objects.requireNonNull(initialProductions), nonTerminalSymbols.get(0));
        generateParser();
    }

    public Map<String, List<List<String>>> getLRProductions() {
        return LRProductions;
    }

    public void setLRProductions(Map<String, List<List<String>>> LRProductions) {
        this.LRProductions = LRProductions;
    }

    public List<String> getEntrySymbols() {
        return entrySymbols;
    }

    public void setEntrySymbols(List<String> entrySymbols) {
        this.entrySymbols = entrySymbols;
    }

    private Pair<String, List<List<String>>> generateLRProduction(Pair<String,List<List<String>>> initialProduction) {
        List<List<String>> result = new ArrayList<>();
        for(List<String> rightSide : initialProduction.getRight()) {
            if(rightSide.get(0).equals("$")) {
                List<String> modifiedRightSide = new ArrayList<>();
                modifiedRightSide.add("*");
                result.add(modifiedRightSide);
            } else {
                for (int i = 0; i < rightSide.size() + 1; i++) {
                    List<String> modifiedRightSide = new ArrayList<>(rightSide);
                    modifiedRightSide.add(i, "*");
                    result.add(modifiedRightSide);
                }
            }
        }
        return new Pair<>(initialProduction.getLeft(),result);
    }

    private Map<String, List<List<String>>> generateAllLRProductions(Map<String, List<List<String>>> initialProductions, String initialSymbol) {
        //dodavanje prve produkcije
        Map<String, List<List<String>>> result = new LinkedHashMap<>();
        List<String> initialProductionList = new ArrayList<>();
        initialProductionList.add(initialSymbol);
        List<List<String>> initialProductionList2 = new ArrayList<>();
        initialProductionList2.add(initialProductionList);
        Pair<String, List<List<String>>> initialProduction = new Pair<>("<S*>", initialProductionList2);
        Pair<String, List<List<String>>> initialLRProduction = generateLRProduction(initialProduction);
        result.put(initialLRProduction.getLeft(),initialLRProduction.getRight());

        for(Map.Entry<String,List<List<String>>> entry : initialProductions.entrySet()) {
            Pair<String, List<List<String>>> productionPair = new Pair<>(entry.getKey(),entry.getValue());
            productionPair = generateLRProduction(productionPair);
            result.put(productionPair.getLeft(),productionPair.getRight());
        }
        return result;
    }

    public void generateParser() {
    }

    public static void main(String[] args) {

    }
}
