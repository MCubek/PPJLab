package ppj.utilities;

import java.util.*;

public class ParserGenerator {

    private Map<String, List<List<String>>> LRProductions;
    private List<String> entrySymbols;
    private List<String> emptySymbols;
    private Map<String,List<String>> firstRelation;

    public ParserGenerator(Map<String, List<List<String>>> initialProductions, List<String> terminalSymbols, List<String> nonTerminalSymbols) {
        this.entrySymbols = new ArrayList<>(nonTerminalSymbols);
        this.entrySymbols.addAll(terminalSymbols);
        this.LRProductions = generateAllLRProductions(Objects.requireNonNull(initialProductions), nonTerminalSymbols.get(0));
        firstRelation = new LinkedHashMap<>();
        generateFirstTable(entrySymbols, initialProductions,nonTerminalSymbols,terminalSymbols);
        generateParser(nonTerminalSymbols,terminalSymbols);
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

    public void generateFirstTable(List<String> entrySymbols, Map<String, List<List<String>>> initialProductions, List<String> nonTerminalSymbols, List<String> terminalSymbols) {
        boolean[][] firstTable = new boolean[entrySymbols.size()][entrySymbols.size()];

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

        this.emptySymbols = emptySymbols;
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
                } else if(firstTable[i][j]) {
                    for(int k = 0; k < firstTable.length; k++) {
                        if(firstTable[j][k])
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

        //popunjavanje liste relacija ZAPOCINJE
        for(int i = 0; i < firstTable.length; i++) {
            if(nonTerminalSymbols.contains(codedSymbols.keySet().toArray()[i].toString())) {
                for(int j = 0; j < firstTable.length; j++) {
                    if(terminalSymbols.contains(codedSymbols.keySet().toArray()[j].toString()) && firstTable[i][j]) {
                        if(this.firstRelation.containsKey(codedSymbols.keySet().toArray()[i].toString())) {
                            List<String> relations = firstRelation.get(codedSymbols.keySet().toArray()[i].toString());
                            relations.add(codedSymbols.keySet().toArray()[j].toString());
                            firstRelation.replace(codedSymbols.keySet().toArray()[i].toString(),relations);
                        } else {
                            List<String> relations = new ArrayList<>();
                            relations.add(codedSymbols.keySet().toArray()[j].toString());
                            firstRelation.put(codedSymbols.keySet().toArray()[i].toString(),relations);
                        }
                    }
                }
            }
        }
        }

        public void generateParser(List<String> nonTerminalSymbols, List<String> terminalSymbols) {
            //mapa koja veze stanja uz produkcije
            Map<Integer, Pair<Pair<String,List<String>>,String>> productionsToStates = new LinkedHashMap<>();
            //brojac stanja
            int stateCounter = 0;
            //mapa svih prijelaza
            Map<Pair<Integer,String>,List<Integer>> transitions = new LinkedHashMap<>();
            //queue nezavrsnih znakova koji se trebaju obraditi
            Queue<Pair<String,String>> transitionQueue = new LinkedList<>();
            //u queue se ubacuje pocetna produkcija i izlazni znak {$}
            Pair<String,String> initialState = new Pair<>(LRProductions.keySet().toArray()[0].toString(),"{$}");
            transitionQueue.add(initialState);
            //brojimo koje smo parove stanja i izlaznih znakova prosli da se izbjegne rekurzija u beskonacnost
            Set<Pair<String,String>> removed = new HashSet<>();
            //obraduje se dok ima nezavrsnih znakova za obraditi
            while(!transitionQueue.isEmpty()) {
                //izvlaci se trenutni par za obradu i oznacuje se kao da je vec proden
                Pair<String,String> currentLeftSide = transitionQueue.remove();
                removed.add(currentLeftSide);
                Integer previousState = null;
                String transitionSymbol = null;
                //gledaju se produkcije nezavrsnog znaka
                for(List<String> currentProductions : LRProductions.get(currentLeftSide.getLeft())) {
                    Pair<String,List<String>> beforeProduction = new Pair<>(currentLeftSide.getLeft(),currentProductions);
                    Pair<Pair<String,List<String>>,String> afterProduction = new Pair<>(beforeProduction,currentLeftSide.getRight());
                    productionsToStates.put(stateCounter++,afterProduction);
                    if(currentProductions.indexOf("*") != 0) {
                        Pair<Integer,String> transitionPair = new Pair<>(previousState,transitionSymbol);
                        if(transitions.containsKey(transitionPair)) {
                            List<Integer> previousKnownStates = transitions.get(transitionPair);
                            previousKnownStates.add(stateCounter);
                            transitions.replace(transitionPair,previousKnownStates);
                        } else {
                            List<Integer> newStates = new ArrayList<>();
                            newStates.add(stateCounter - 1);
                            transitions.put(transitionPair, newStates);
                        }
                        if(transitionSymbol != null && nonTerminalSymbols.contains(transitionSymbol)) {
                            StringBuilder followSymbolString = new StringBuilder("{");
                            String followSymbol;
                            if(currentProductions.indexOf("*") != currentProductions.size() - 1) {
                                List<String> checkEmpty = currentProductions.subList(currentProductions.indexOf("*") + 1,currentProductions.size());
                                if(this.emptySymbols.containsAll(checkEmpty)) {
                                    followSymbolString.append("$, ");
                                }
                                followSymbol = currentProductions.get(currentProductions.indexOf("*") + 1);
                                if(nonTerminalSymbols.contains(followSymbol)) {
                                    List<String> firstRelations = firstRelation.get(followSymbol);
                                    for (String s : firstRelations) {
                                        if (firstRelations.indexOf(s) == firstRelations.size() - 1)
                                            followSymbolString.append(s).append("}");
                                        else
                                            followSymbolString.append(s).append(", ");
                                    }
                                } else {
                                    followSymbolString.append(followSymbol).append("}");
                                }
                            } else {
                                followSymbolString.append("$}");
                            }
                            Pair<String,String> nextPair = new Pair<>(transitionSymbol, followSymbolString.toString());
                            if(!removed.contains(nextPair))
                                transitionQueue.add(nextPair);
                        }
                    }
                    if(currentProductions.indexOf("*") != currentProductions.size() -1) {
                        previousState = stateCounter - 1;
                        transitionSymbol = currentProductions.get(currentProductions.indexOf("*") + 1);
                    }
                }
            }
            System.out.println("test");
        }

    public static void main(String[] args) {

    }
}
