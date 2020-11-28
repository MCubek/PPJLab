package ppj.utilities;

import java.util.*;

public class ParserGenerator {

    private Map<String, List<List<String>>> LRProductions;
    private List<String> entrySymbols;
    private boolean[][] firstTable;

    public ParserGenerator(Map<String, List<List<String>>> initialProductions, List<String> terminalSymbols, List<String> nonTerminalSymbols) {
        this.entrySymbols = new ArrayList<>(nonTerminalSymbols);
        this.entrySymbols.addAll(terminalSymbols);
        this.LRProductions = generateAllLRProductions(Objects.requireNonNull(initialProductions), nonTerminalSymbols.get(0));
        this.firstTable = new boolean[entrySymbols.size()][entrySymbols.size()];
        generateFirstTable(entrySymbols, initialProductions);
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

        //izgradnja ostalih produkcija
        for(Map.Entry<String,List<List<String>>> entry : initialProductions.entrySet()) {
            Pair<String, List<List<String>>> productionPair = new Pair<>(entry.getKey(),entry.getValue());
            productionPair = generateLRProduction(productionPair);
            result.put(productionPair.getLeft(),productionPair.getRight());
        }
        return result;
    }

    public void generateFirstTable(List<String> entrySymbols, Map<String, List<List<String>>> initialProductions) {
        //kodiranje znakova za tablicu ZAPOCINJE
        Map<String,Integer> codedSymbols = new LinkedHashMap<>();
        int counter = 0;
        for(String symbol : this.entrySymbols) {
            codedSymbols.put(symbol,counter++);
        }

        //generiranje praznih znakova
        List<String> emptySymbols = new ArrayList<>();
        boolean continueIteration = true;
        while(continueIteration) {
            continueIteration = false;
            for (Map.Entry<String, List<List<String>>> entry : initialProductions.entrySet()) {
                for (List<String> rightSide : entry.getValue()) {
                    if (rightSide.contains("$") || emptySymbols.containsAll(rightSide)) {
                        emptySymbols.add(entry.getKey());
                        if(!emptySymbols.contains(entry.getKey()))
                            continueIteration = true;
                    }
                }
            }
        }

        //generiranje tablice ZapocinjeIzravnoZnakom
        for (Map.Entry<String, List<List<String>>> entry : initialProductions.entrySet()) {
            for (List<String> rightSide : entry.getValue()) {
                for(String symbol : rightSide) {
                    if(!symbol.equals("$")) {
                        firstTable[codedSymbols.get(entry.getKey())][codedSymbols.get(symbol)] = true;
                        if (!emptySymbols.contains(symbol))
                            break;
                    }
                }
            }
        }

        /*String line = "    ";
        for(String symbol: entrySymbols)
            if(symbol.length() > 1)
                line = line + symbol + " ";
            else
                line = line + symbol + "   ";
        System.out.println(line);
        line = "";
        for(int i = 0; i < firstTable.length; i++) {
            String symbol = codedSymbols.keySet().toArray()[i].toString();
            if(symbol.length() > 1)
                line = line + symbol + "  ";
            else
                line = " " + line + symbol + "   ";
            for (int j = 0; j < firstTable.length; j++) {
                int myInt = firstTable[i][j] ? 1 : 0;
                line = line + myInt + "   ";
            }
            System.out.println(line);
            line = "";
        }*/

        //generiranje tablice ZapočinjeZnakom
        for(int i = 0; i < firstTable.length; i++) {
            for(int j = 0; j < firstTable.length; j++) {
                if(i == j) {
                    firstTable[i][j] = true;
                } else if(firstTable[i][j] == true) {
                    for(int k = 0; k < firstTable.length; k++) {
                        if(firstTable[j][k] == true)
                            firstTable[i][k] = true;
                    }
                }
            }
        }

        /*System.out.println("---------------------------------------------------------------------------");
        line = "    ";
        for(String symbol: entrySymbols)
            if(symbol.length() > 1)
                line = line + symbol + " ";
            else
                line = line + symbol + "   ";
        System.out.println(line);
        line = "";
        for(int i = 0; i < firstTable.length; i++) {
            String symbol = codedSymbols.keySet().toArray()[i].toString();
            if(symbol.length() > 1)
                line = line + symbol + "  ";
            else
                line = " " + line + symbol + "   ";
            for (int j = 0; j < firstTable.length; j++) {
                int myInt = firstTable[i][j] ? 1 : 0;
                line = line + myInt + "   ";
            }
            System.out.println(line);
            line = "";
        }*/
        }

    public static void main(String[] args) {

    }
}
