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
        generateParserTables(nonTerminalSymbols,terminalSymbols);
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

        public void generateParserTables(List<String> nonTerminalSymbols, List<String> terminalSymbols) {
        Pair<Map<Integer, Pair<Pair<String,List<String>>,String>>,Map<Pair<Integer,String>,List<Integer>>> statesAndTransitions = generateENKA(nonTerminalSymbols,terminalSymbols);
        enkaToDka(statesAndTransitions);
        }


    private void enkaToDka(Pair<Map<Integer, Pair<Pair<String, List<String>>, String>>, Map<Pair<Integer, String>, List<Integer>>> statesAndTransitions) {
       Map<Integer,Set<Integer>> dkaStates = new LinkedHashMap<>();
       Map<Pair<Integer,String>,Integer> dkaTransitions = new LinkedHashMap<>();
       List<Set<Integer>> existingGroups = new ArrayList<>();

       Queue<Pair<Integer,Pair<Integer,String>>> statesToTransform = new LinkedList<>();
       Queue<Integer> removed = new LinkedList<>();
       Map<Integer, Pair<Pair<String,List<String>>,String>> enkaStates = statesAndTransitions.getLeft();
       Map<Pair<Integer, String>, List<Integer>> enkaTransitions = statesAndTransitions.getRight();
       statesToTransform.add(new Pair<>((Integer) enkaStates.keySet().toArray()[0], new Pair<>(-1,"")));
       int stateCounter = 0;
       while(!statesToTransform.isEmpty()) {
           Pair<Integer,Pair<Integer,String>> currentElement = statesToTransform.remove();
           Integer currentState = currentElement.getLeft();
           Pair<Integer,String> fromState = currentElement.getRight();
           removed.add(currentState);

           Set<Integer> groupedStates = calculateEClosure(currentState,enkaTransitions);
           boolean checkifExists = true;
           for(Set<Integer> group : existingGroups) {
               if (groupedStates.equals(group)) {
                   checkifExists = false;
                   break;
               }
           }
           if(checkifExists) {
               dkaStates.put(stateCounter++, groupedStates);
               existingGroups.add(groupedStates);
           }

           if(fromState.getLeft() != -1) {
               dkaTransitions.put(new Pair<>(fromState.getLeft(), fromState.getRight()),stateCounter-1);
           }

           for(String symbol : this.entrySymbols) {
               for(Integer state : groupedStates) {
                   Pair<Integer,String> checkState = new Pair<>(state,symbol);
                   if(enkaTransitions.containsKey(checkState))
                       for(Integer nextState : enkaTransitions.get(checkState))
                           statesToTransform.add(new Pair<>(nextState,new Pair<>(stateCounter-1,symbol)));
               }
           }
       }
    }

    private Set<Integer> calculateEClosure(Integer currentState, Map<Pair<Integer, String>, List<Integer>> transitions) {
        Set<Integer> groupedStates = new HashSet<>();
        Queue<Integer> findEClosure = new LinkedList<>();
        findEClosure.add(currentState);
        groupedStates.add(currentState);
        while(!findEClosure.isEmpty()) {
            Integer eClosureState = findEClosure.remove();
            Pair<Integer, String> eTransition = new Pair<>(eClosureState, "$");
            if(transitions.containsKey(eTransition)) {
                groupedStates.addAll(transitions.get(eTransition));
                findEClosure.addAll(transitions.get(eTransition));
            }
        }
        return groupedStates;
    }

    private Pair<Map<Integer, Pair<Pair<String,List<String>>,String>>,Map<Pair<Integer,String>,List<Integer>>> generateENKA(List<String> nonTerminalSymbols, List<String> terminalSymbols) {
        //mapa koja veze stanja uz produkcije
        Map<Integer, Pair<Pair<String,List<String>>,String>> productionsToStates = new LinkedHashMap<>();
        //brojac stanja
        int stateCounter = 0;
        //mapa svih prijelaza
        Map<Pair<Integer,String>,List<Integer>> transitions = new LinkedHashMap<>();
        //queue nezavrsnih znakova koji se trebaju obraditi
        Queue<Pair<Pair<String,String>,Integer>> transitionQueue = new LinkedList<>();
        //u queue se ubacuje pocetna produkcija i izlazni znak {$}
        Pair<String,String> initialState = new Pair<>(LRProductions.keySet().toArray()[0].toString(),"{$}");
        Queue<Pair<String,String>> supportQueue = new LinkedList<>();
        supportQueue.add(initialState);
        Pair<Pair<String,String>,Integer> initialQueueElement = new Pair<>(initialState,-1);
        transitionQueue.add(initialQueueElement);
        //brojimo koje smo parove stanja i izlaznih znakova prosli da se izbjegne rekurzija u beskonacnost
        Set<Pair<String,String>> removed = new HashSet<>();
        //obraduje se dok ima nezavrsnih znakova za obraditi
        while(!transitionQueue.isEmpty()) {
            //izvlaci se trenutni par za obradu i oznacuje se kao da je vec proden
            Pair<Pair<String,String>,Integer> currentQueueElement = transitionQueue.remove();
            Pair<String,String> currentLeftSide = currentQueueElement.getLeft();
            supportQueue.remove();
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
                    addTransition(stateCounter, transitions, transitionPair);
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
                        Pair<Pair<String,String>,Integer> nextQueueElement = new Pair<>(nextPair,previousState);
                        if(!removed.contains(nextPair) && !supportQueue.contains(nextPair)) {
                            transitionQueue.add(nextQueueElement);
                            supportQueue.add(nextPair);
                        }
                    }
                } else {
                    if(currentQueueElement.getRight() != -1) {
                        Pair<Integer,String> transitionPair = new Pair<>(currentQueueElement.getRight(), "$");
                        addTransition(stateCounter, transitions, transitionPair);
                    }
                }
                if(currentProductions.indexOf("*") != currentProductions.size() -1) {
                    previousState = stateCounter - 1;
                    transitionSymbol = currentProductions.get(currentProductions.indexOf("*") + 1);
                }
            }
        }
        //printEnka(productionsToStates,transitions);
        return new Pair<>(productionsToStates,transitions);
    }

    private void printEnka(Map<Integer, Pair<Pair<String, List<String>>, String>> productionsToStates, Map<Pair<Integer, String>, List<Integer>> transitions) {
        for(Map.Entry<Pair<Integer,String>,List<Integer>> entry : transitions.entrySet()) {
            Pair<Pair<String,List<String>>,String> currentState = productionsToStates.get(entry.getKey().getLeft());
            StringBuilder currentStateString = new StringBuilder(entry.getKey().getLeft() + ". ");
            currentStateString.append(currentState.getLeft().getLeft() + " -> ");
            for(String s : currentState.getLeft().getRight()) {
                currentStateString.append(s);
            }
            currentStateString.append(" ");
            currentStateString.append(currentState.getRight());
            for(Integer nextState: entry.getValue()) {
                Pair<Pair<String,List<String>>,String> rightSideState = productionsToStates.get(nextState);
                StringBuilder currentRightSideStateString = new StringBuilder(nextState + ". " + rightSideState.getLeft().getLeft() + " -> ");
                for(String s : rightSideState.getLeft().getRight()) {
                    currentRightSideStateString.append(s);
                }
                currentRightSideStateString.append(" ");
                currentRightSideStateString.append(rightSideState.getRight());
                System.out.println(currentStateString + "  +  " + entry.getKey().getRight() + " ===> " + currentRightSideStateString);
            }
        }

    }

    private void addTransition(int stateCounter, Map<Pair<Integer, String>, List<Integer>> transitions, Pair<Integer, String> transitionPair) {
        if(transitions.containsKey(transitionPair)) {
            List<Integer> previousKnownStates = transitions.get(transitionPair);
            previousKnownStates.add(stateCounter-1);
            transitions.replace(transitionPair,previousKnownStates);
        } else {
            List<Integer> newStates = new ArrayList<>();
            newStates.add(stateCounter - 1);
            transitions.put(transitionPair, newStates);
        }
    }

    public static void main(String[] args) {

    }
}
