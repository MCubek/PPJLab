package ppj.utilities;

import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.actions.*;

import java.util.*;

public class ParserGenerator {

    private Map<String, List<List<String>>> LRProductions;
    private List<String> entrySymbols;
    private List<String> emptySymbols;
    private final Map<String,List<String>> firstRelation;
    private Map<Integer, Pair<Pair<String,List<String>>,String>> productionsToStates;
    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, PutAction> newStateTable;

    public List<String> getEmptySymbols() {
        return emptySymbols;
    }

    public void setEmptySymbols(List<String> emptySymbols) {
        this.emptySymbols = emptySymbols;
    }

    public Map<String, List<String>> getFirstRelation() {
        return firstRelation;
    }

    public Map<Integer, Pair<Pair<String, List<String>>, String>> getProductionsToStates() {
        return productionsToStates;
    }

    public void setProductionsToStates(Map<Integer, Pair<Pair<String, List<String>>, String>> productionsToStates) {
        this.productionsToStates = productionsToStates;
    }

    public Map<Pair<Integer, String>, Action> getActionTable() {
        return actionTable;
    }

    public void setActionTable(Map<Pair<Integer, String>, Action> actionTable) {
        this.actionTable = actionTable;
    }

    public Map<Pair<Integer, String>, PutAction> getNewStateTable() {
        return newStateTable;
    }

    public void setNewStateTable(Map<Pair<Integer, String>, PutAction> newStateTable) {
        this.newStateTable = newStateTable;
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

    public ParserGenerator(Map<String, List<List<String>>> initialProductions, List<String> terminalSymbols, List<String> nonTerminalSymbols, Map<Pair<String, String>,Integer> productionPriorites) {
        this.entrySymbols = new ArrayList<>(nonTerminalSymbols);
        this.entrySymbols.addAll(terminalSymbols);
        this.LRProductions = generateAllLRProductions(Objects.requireNonNull(initialProductions), nonTerminalSymbols.get(0));
        this.newStateTable = new LinkedHashMap<>();
        this.actionTable = new LinkedHashMap<>();
        firstRelation = new LinkedHashMap<>();
        generateFirstTable(entrySymbols, initialProductions,nonTerminalSymbols,terminalSymbols);
        generateParserTables(nonTerminalSymbols,terminalSymbols,productionPriorites);
        System.out.println("test");
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

    public void generateParserTables(List<String> nonTerminalSymbols, List<String> terminalSymbols, Map<Pair<String, String>, Integer> productionPriorites) {
        Pair<Map<Integer, Pair<Pair<String,List<String>>,String>>,Map<Pair<Integer,String>,List<Integer>>> statesAndTransitions = generateENKA(nonTerminalSymbols,terminalSymbols);
        Pair<Map<Integer,Set<Integer>>,Map<Pair<Integer,String>,Integer>> dka = enkaToDka(statesAndTransitions);

        Map<Integer,Set<String>> transitionSymbols = new LinkedHashMap<>();
        for(Map.Entry<Pair<Integer,String>,Integer> dkaProduction : dka.getRight().entrySet()) {
            if(nonTerminalSymbols.contains(dkaProduction.getKey().getRight())) {
                this.newStateTable.put(dkaProduction.getKey(),new PutAction(dkaProduction.getValue()));
            } else {
                this.actionTable.put(dkaProduction.getKey(), new MoveAction(dkaProduction.getValue()));
            }
            if(transitionSymbols.containsKey(dkaProduction.getKey().getLeft())) {
                Set<String> symbols = transitionSymbols.get(dkaProduction.getKey().getLeft());
                symbols.add(dkaProduction.getKey().getRight());
                transitionSymbols.replace(dkaProduction.getKey().getLeft(),symbols);
            } else {
                Set<String> symbols = new HashSet<>();
                symbols.add(dkaProduction.getKey().getRight());
                transitionSymbols.put(dkaProduction.getKey().getLeft(),symbols);
            }
        }

        for(Map.Entry<Integer,Set<Integer>> dkaState : dka.getLeft().entrySet()) {
            List<Pair<Pair<String,String>,Set<String>>> reductionProductions = new ArrayList<>();
            for(Integer state : dkaState.getValue()) {
                Pair<Pair<String,List<String>>,String> production = this.productionsToStates.get(state);
                if(production.getLeft().getRight().indexOf("*") == production.getLeft().getRight().size()-1) {
                    String transitionSymbolCombined;
                    transitionSymbolCombined = production.getRight().substring(production.getRight().indexOf("{") + 1, production.getRight().indexOf("}"));
                    String[] transitionSymbolArray = transitionSymbolCombined.split(",");
                    Set<String> reductionSymbols = new HashSet<>();
                    for (String s : transitionSymbolArray) {
                        if (transitionSymbols.containsKey(dkaState.getKey()) && !transitionSymbols.get(dkaState.getKey()).contains(s.trim()))
                            reductionSymbols.add(s.trim());
                        else if(!transitionSymbols.containsKey(dkaState.getKey()))
                            reductionSymbols.add(s.trim());
                    }
                    String productionString = transformToString(production.getLeft().getRight());
                    reductionProductions.add(new Pair<>(new Pair<>(production.getLeft().getLeft(),productionString),reductionSymbols));
                }
            }
            Set<Pair<Pair<String,String>,String>> contains = new HashSet<>();
            for(int i = 0; i < reductionProductions.size(); i++) {
                for(int j = 0; j < reductionProductions.size(); j++) {
                    if(reductionProductions.get(i).getLeft().getRight().equals(reductionProductions.get(j).getLeft().getRight())) {
                        if(reductionProductions.get(i).getLeft().getLeft().equals("<S*>")) {
                            this.actionTable.put(new Pair<>(dkaState.getKey(), "$"),new AcceptAction(new Production(reductionProductions.get(i).getLeft().getLeft(),transformBackToLR(reductionProductions.get(i).getLeft().getRight()))));
                        } else {
                            if (productionPriorites.get(reductionProductions.get(i).getLeft()) < productionPriorites.get(reductionProductions.get(j).getLeft())) {
                                addReduction(dkaState, reductionProductions, contains, i);
                            } else {
                                addReduction(dkaState, reductionProductions, contains, j);
                            }
                        }
                    }

                }
            }
        }
    }

    private void addReduction(Map.Entry<Integer, Set<Integer>> dkaState, List<Pair<Pair<String, String>, Set<String>>> reductionProductions, Set<Pair<Pair<String, String>, String>> contains, int j) {
            for (String s : reductionProductions.get(j).getRight()) {
                if(!contains.contains(new Pair<>(reductionProductions.get(j).getLeft(),s))) {
                    this.actionTable.put(new Pair<>(dkaState.getKey(), s), new ReduceAction(new Production(reductionProductions.get(j).getLeft().getLeft(), transformBackToLR(reductionProductions.get(j).getLeft().getRight()))));
                    contains.add(new Pair<>(reductionProductions.get(j).getLeft(),s));
            }
        }
    }

    private List<String> transformBackToLR(String right) {
        List<String> result = new ArrayList<>();
        StringBuilder s = new StringBuilder();
        boolean dontBreak = false;
        for(char c : right.toCharArray()) {
            if(c == '<') {
                dontBreak = true;
            }
            s.append(c);
            if(c == '>')
                dontBreak = false;
            if(!dontBreak) {
                result.add(s.toString());
                s = new StringBuilder();
            }
        }
        return result;
    }

    private String transformToString(List<String> rightSide) {
        StringBuilder result = new StringBuilder();
        for(String s : rightSide) {
            if(!s.equals("*"))
                result.append(s);
        }
        if(result.toString().equals(""))
            result.append("$");
        return result.toString();
    }


    private Pair<Map<Integer,Set<Integer>>,Map<Pair<Integer,String>,Integer>> enkaToDka(Pair<Map<Integer, Pair<Pair<String, List<String>>, String>>, Map<Pair<Integer, String>, List<Integer>>> statesAndTransitions) {
       Map<Integer,Set<Integer>> dkaStates = new LinkedHashMap<>();
       Map<Pair<Integer,String>,Integer> dkaTransitions = new LinkedHashMap<>();
       List<Set<Integer>> existingGroups = new ArrayList<>();

       Queue<Pair<Set<Integer>,Pair<Integer,String>>> statesToTransform = new LinkedList<>();
       Queue<Set<Integer>> removed = new LinkedList<>();
       Map<Integer, Pair<Pair<String,List<String>>,String>> enkaStates = statesAndTransitions.getLeft();
       Map<Pair<Integer, String>, List<Integer>> enkaTransitions = statesAndTransitions.getRight();
       Set<Integer> initialSet = new HashSet<>();
       initialSet.add((Integer) enkaStates.keySet().toArray()[0]);
       statesToTransform.add(new Pair<>(initialSet, new Pair<>(-1,"")));
       int stateCounter = 0;
       while(!statesToTransform.isEmpty()) {
           Pair<Set<Integer>,Pair<Integer,String>> currentElement = statesToTransform.remove();
           Set<Integer> currentGroup = currentElement.getLeft();
           Pair<Integer,String> fromState = currentElement.getRight();
           Set<Integer> afterEClosure = new HashSet<>();
           for(Integer stateToCalcuate : currentGroup) {
               Set<Integer> eClosure = calculateEClosure(stateToCalcuate,enkaTransitions);
               afterEClosure.addAll(eClosure);
           }

           boolean checkifExists = true;
           for(Set<Integer> group : existingGroups) {
               if (afterEClosure.equals(group)) {
                   checkifExists = false;
                   break;
               }
           }

           if(checkifExists && !afterEClosure.isEmpty()) {
               dkaStates.put(stateCounter++, afterEClosure);
               existingGroups.add(afterEClosure);

               if (fromState.getLeft() != -1) {
                   dkaTransitions.put(new Pair<>(fromState.getLeft(), fromState.getRight()), stateCounter - 1);
               }

               Set<Pair<Pair<Integer, String>, Set<Integer>>> nextStatesToCalculate = new HashSet<>();
               for (String symbol : this.entrySymbols) {
                   Pair<Integer, String> transitionPair = new Pair<>(stateCounter - 1, symbol);
                   Set<Integer> nextStates = new HashSet<>();
                   for (Integer state : afterEClosure) {
                       Pair<Integer, String> checkState = new Pair<>(state, symbol);
                       if (enkaTransitions.containsKey(checkState)) {
                           nextStates.addAll(enkaTransitions.get(checkState));
                       }
                   }
                   nextStatesToCalculate.add(new Pair<>(transitionPair, nextStates));
               }
               for (Pair<Pair<Integer, String>, Set<Integer>> pair : nextStatesToCalculate) {
                   statesToTransform.add(new Pair<>(pair.getRight(), pair.getLeft()));
               }
           } else {
               for(Map.Entry<Integer,Set<Integer>>  entry : dkaStates.entrySet()) {
                   if(afterEClosure.equals(entry.getValue()))
                       dkaTransitions.put(currentElement.getRight(),entry.getKey());
               }
           }
       }
       //printDKA(dkaStates);
       return new Pair<>(dkaStates,dkaTransitions);
    }

    private void printDKA(Map<Integer, Set<Integer>> dkaStates) {
        for(Map.Entry<Integer,Set<Integer>> entry : dkaStates.entrySet()) {
            System.out.println("State: " + entry.getKey());
            System.out.println("Productions:");
            System.out.println("---------------------------------------------------------------");
            for(Integer state : entry.getValue()) {
                Pair<Pair<String,List<String>>,String> stateToPrint = this.productionsToStates.get(state);
                String stateString = "";
                stateString = stateString + stateToPrint.getLeft().getLeft();
                stateString = stateString + " --> ";
                for(String rightSide : stateToPrint.getLeft().getRight())
                    stateString = stateString + rightSide;
                stateString = stateString + " " + stateToPrint.getRight();
                System.out.println(stateString);
            }
            System.out.println("--------------------------------------------------------------");
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
        Queue<Pair<String,String>> transitionQueue = new LinkedList<>();
        //u queue se ubacuje pocetna produkcija i izlazni znak {$}
        Pair<String,String> initialState = new Pair<>(LRProductions.keySet().toArray()[0].toString(),"{$}");
        transitionQueue.add(initialState);
        //brojimo koje smo parove stanja i izlaznih znakova prosli da se izbjegne rekurzija u beskonacnost
        Set<Pair<String,String>> removed = new HashSet<>();
        //obraduje se dok ima nezavrsnih znakova za obraditi
        while(!transitionQueue.isEmpty()) {
            //izvlaci se trenutni par za obradu i oznacuje se kao da je vec proden
            Queue<Pair<String,String>> supportQueue = new LinkedList<>();
            Pair<String,String> currentQueueElement = transitionQueue.remove();
            removed.add(currentQueueElement);
            Integer previousState = null;
            String transitionSymbol = null;
            //gledaju se produkcije nezavrsnog znaka
            for(List<String> currentProductions : LRProductions.get(currentQueueElement.getLeft())) {
                Pair<String,List<String>> beforeProduction = new Pair<>(currentQueueElement.getLeft(),currentProductions);
                Pair<Pair<String,List<String>>,String> afterProduction = new Pair<>(beforeProduction,currentQueueElement.getRight());
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
                        if(!removed.contains(nextPair) && !supportQueue.contains(nextPair)) {
                            transitionQueue.add(nextPair);
                            supportQueue.add(nextPair);
                        }
                    }
                }
                if(currentProductions.indexOf("*") != currentProductions.size() -1) {
                    previousState = stateCounter - 1;
                    transitionSymbol = currentProductions.get(currentProductions.indexOf("*") + 1);
                }
            }
        }

        //dodavanje epsilon produkcija
        for(Map.Entry<Integer, Pair<Pair<String,List<String>>,String>> entry : productionsToStates.entrySet()) {
            Integer fromState = entry.getKey();
            Pair<Pair<String,List<String>>,String> production = entry.getValue();
            String leftSide = production.getLeft().getLeft();
            List<String> rightSide = production.getLeft().getRight();
            String curlySymbols = production.getRight();
            if(rightSide.indexOf("*") != rightSide.size()-1 && nonTerminalSymbols.contains(rightSide.get(rightSide.indexOf("*") + 1))) {
                StringBuilder followSymbolString = new StringBuilder("{");
                String followSymbol = rightSide.get(rightSide.indexOf("*") + 1);
                if(rightSide.indexOf("*") + 1 == rightSide.size()-1) {
                    followSymbolString.append("$}");
                } else {
                    String nextSymbol = rightSide.get(rightSide.indexOf("*") + 2);
                    List<String> checkEmpty = rightSide.subList(rightSide.indexOf("*") + 2, rightSide.size());
                    if (this.emptySymbols.containsAll(checkEmpty)) {
                        followSymbolString.append("$, ");
                    }

                    List<String> firstRelations = firstRelation.get(nextSymbol);
                    for (String s : firstRelations) {
                        if (firstRelations.indexOf(s) == firstRelations.size() - 1)
                            followSymbolString.append(s).append("}");
                        else
                            followSymbolString.append(s).append(", ");
                    }
                }

                for(Map.Entry<Integer, Pair<Pair<String,List<String>>,String>> nextEntry : productionsToStates.entrySet()) {
                    Integer nextState = nextEntry.getKey();
                    Pair<Pair<String,List<String>>,String> nextProduction = nextEntry.getValue();
                    if(nextState != fromState) {
                        if (followSymbol.equals(nextProduction.getLeft().getLeft())) {
                            if (nextProduction.getLeft().getRight().indexOf("*") == 0) {
                                if (nextProduction.getRight().equals(followSymbolString.toString())) {
                                    addTransition(nextState + 1, transitions, new Pair<>(fromState, "$"));
                                }
                            }
                        }
                    }
                }
            }
        }

        //printEnka(productionsToStates,transitions);
        this.productionsToStates = productionsToStates;
        return new Pair<>(productionsToStates,transitions);
    }

    private void printEnka(Map<Integer, Pair<Pair<String, List<String>>, String>> productionsToStates, Map<Pair<Integer, String>, List<Integer>> transitions) {
        for(Map.Entry<Pair<Integer,String>,List<Integer>> entry : transitions.entrySet()) {
            Pair<Pair<String,List<String>>,String> currentState = productionsToStates.get(entry.getKey().getLeft());
            StringBuilder currentStateString = new StringBuilder(entry.getKey().getLeft() + ". ");
            currentStateString.append(currentState.getLeft().getLeft()).append(" -> ");
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
