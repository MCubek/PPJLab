package ppj.lab2.analizator;

import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.actions.*;
import ppj.lab2.utilities.Node;
import ppj.lab2.utilities.NodeTreePrinter;
import ppj.utilities.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Sintaksni analizator
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 14/11/2020
 */
public class SA {
    private final int STARTING_STATE = 0;

    private List<String> synchronizationSymbols;
    //Map<<Par<Stanje,Znak>, akcija>
    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, PutAction> newStateTable;

    //Format "PRAVILO REDAK ZNAK"
    private List<String> lexicalUnits;

    private Node<String> rootNode;

    /**
     * Testni konstruktor
     *
     * @param synchronizationSymbols sinkromizacijski znakovi
     * @param actionTable            tablica akcije
     * @param newStateTable          tablica novo stanje
     * @param lexicalUnits           lista leksicnih jedinki
     */
    public SA(List<String> synchronizationSymbols, Map<Pair<Integer, String>, Action> actionTable, Map<Pair<Integer, String>, PutAction> newStateTable, List<String> lexicalUnits) {
        this.synchronizationSymbols = synchronizationSymbols;
        this.actionTable = actionTable;
        this.newStateTable = newStateTable;
        this.lexicalUnits = lexicalUnits;

        generateTree();
    }

    /**
     * Konstuktor koji prima file
     *
     * @param inputFile ulazni file
     * @throws IOException            file exception
     * @throws ClassNotFoundException exception parsiranja serizaliziranih podatka
     */
    public SA(File inputFile) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(inputFile);

        parseInput(scanner);
        readSerialization();
        generateTree();

        scanner.close();
    }

    /**
     * Konstuktor koji prima scanner
     *
     * @param scanner scanner
     * @throws IOException            file exception
     * @throws ClassNotFoundException exception parsiranja serizaliziranih podatka
     */
    public SA(Scanner scanner) throws IOException, ClassNotFoundException {
        parseInput(scanner);
        readSerialization();
        generateTree();
    }

    /**
     * Metoda generiranja stabla
     */
    private void generateTree() {
        rootNode = null;
        //Oznaka kraja niza dodaj
        lexicalUnits.add("$ 0 $");

        Deque<Integer> stateQueue = new LinkedList<>();
        Deque<Node<String>> symbolsQueue = new LinkedList<>();
        stateQueue.push(STARTING_STATE);

        for (int position = 0; position < lexicalUnits.size() && ! stateQueue.isEmpty(); ) {
            String lexicalUnit = lexicalUnits.get(position);
            String[] lexicalUnitElements = lexicalUnit.split(" ");
            String symbol = lexicalUnitElements[0];
            int lineIndex = Integer.parseInt(lexicalUnitElements[1]);

            Action action = actionTable.get(new Pair<>(stateQueue.peek(), symbol));
            Node<String> node = new Node<>(lexicalUnit);

            if (action instanceof AcceptAction) {
/*                AcceptAction acceptAction = (AcceptAction) action;
                Production startProduction = acceptAction.getStartProduction();

                if (startProduction != null) {
                    Node<String> newParentNode = new Node<>(startProduction.getLeftState());

                    removeFromStackAndAddToParent(newParentNode, startProduction.getRightStates().size(), stateQueue, symbolsQueue);

                    symbolsQueue.push(newParentNode);
                }*/

                rootNode = symbolsQueue.peek();
                break;
            } else if (action instanceof MoveAction) {
                MoveAction moveAction = (MoveAction) action;

                symbolsQueue.push(node);
                stateQueue.push(moveAction.getState());
                position++;
            } else if (action instanceof ReduceAction) {
                ReduceAction reduceAction = (ReduceAction) action;
                Production reduceProduction = reduceAction.getProduction();

                Node<String> newParentNode = new Node<>(reduceProduction.getLeftState());

                if (reduceProduction.getRightStates().contains("$")) {
                    //Epsilon produkcija
                    newParentNode.addChild(new Node<>("$"));
                } else {
                    //Nije epsilon produkcija
                    removeFromStackAndAddToParent(newParentNode, reduceProduction.getRightStates().size(), stateQueue, symbolsQueue);
                }

                //Procitaj novo stanje iz tablice novo stanje
                PutAction putAction = newStateTable.get(new Pair<>(stateQueue.peek(), reduceProduction.getLeftState()));

                //Stavi na stog
                stateQueue.push(putAction.getState());
                symbolsQueue.push(newParentNode);

            } else if (action == null || action instanceof RejectAction) {
                System.err.println("ERROR on line " + lineIndex);
                if (action != null && (((RejectAction) action).getMessage() != null))
                    System.err.println("Message: " + ((RejectAction) action).getMessage());

                printRejectOnSystemError(stateQueue, lexicalUnit, lineIndex);

                //Trazi sinkronizacijski znak
                Recover:
                for (; position < lexicalUnits.size(); position++) {
                    lexicalUnit = lexicalUnits.get(position);
                    lexicalUnitElements = lexicalUnit.split(" ");
                    symbol = lexicalUnitElements[0];

                    if (synchronizationSymbols.contains(symbol)) {
                        while (! stateQueue.isEmpty()) {
                            Pair<Integer, String> pair = new Pair<>(stateQueue.peek(), symbol);
                            Action testAction = actionTable.get(pair);
                            if (testAction != null && ! (testAction instanceof RejectAction)) {
                                break Recover;
                            } else {
                                stateQueue.pop();
                                symbolsQueue.pop();
                            }
                        }
                    }
                }

            } else throw new RuntimeException("Action not defined");
        }
        if (stateQueue.isEmpty()) throw new RuntimeException("State stack shouldn't be empty.");
    }

    /**
     * Metoda koja mice sa stoga stanja i roditelju ih upisuje kao djecu kod akcije redukcije
     *
     * @param parent         roditelj ili lijeva strana produkcije
     * @param numberOfStates broj elemenata desne strane koji se skida sa stoga
     * @param stateQueue     stog stanja
     * @param symbolsQueue   stog znakova
     */
    private static void removeFromStackAndAddToParent(Node<String> parent, int numberOfStates, Deque<Integer> stateQueue, Deque<Node<String>> symbolsQueue) {
        LinkedList<Node<String>> linkedList = new LinkedList<>();
        for (int i = 0; i < numberOfStates; i++) {
            stateQueue.pop();
            linkedList.addFirst(symbolsQueue.pop());
        }
        //Postavi djecu
        parent.setChildren(linkedList);
    }

    private void printRejectOnSystemError(Queue<Integer> stateQueue, String lexicalUnit, int lineIndex) {
        System.err.println("Expected symbols:");
        System.err.println(String.join(", ", getExpected(stateQueue.peek())));

        System.err.println("Read symbol and program example:");
        //Makni broj retka iz zadnjeg ispisa
        System.err.println(lexicalUnit.replace(" " + lineIndex, ""));
    }

    private List<String> getExpected(Integer state) {
        List<String> expectedList = new ArrayList<>();
        for (Map.Entry<Pair<Integer, String>, Action> entry : actionTable.entrySet()) {
            if (entry.getKey().getLeft().equals(state) && ! (entry.getValue() instanceof RejectAction)) {
                expectedList.add(entry.getKey().getRight());
            }
        }
        return expectedList;
    }

    /**
     * Metoda parisra ulazne leksicke jedinke
     *
     * @param scanner scanner iz kojeg uzima jednike
     */
    private void parseInput(Scanner scanner) {
        lexicalUnits = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (! line.isBlank())
                lexicalUnits.add(line);
        }
    }


    /**
     * Metoda citanja serializiranih podataka iz generatora sinktaksnog analizatora
     *
     * @throws IOException            file exception
     * @throws ClassNotFoundException exception parsiranja serizaliziranih podatka
     */
    @SuppressWarnings("unchecked")
    private void readSerialization() throws IOException, ClassNotFoundException {
        Path path = Path.of("src/main/java/ppj/lab2/analizator");

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path.resolve("synchronizationSymbols.ser")))) {
            this.synchronizationSymbols = (List<String>) ois.readObject();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path.resolve("actionTable.ser")))) {
            this.actionTable = (Map<Pair<Integer, String>, Action>) ois.readObject();
        }

        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(path.resolve("newStateTable.ser")))) {
            this.newStateTable = (Map<Pair<Integer, String>, PutAction>) ois.readObject();
        }
    }

    /**
     * Metoda rezultat sintaksne analite vraca u jednom stringu
     * formata zadanog u zadatku.
     *
     * @return output as String
     */
    public String getOutputAsString() {
        return NodeTreePrinter.walkNodeTree(rootNode);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            SA sa = new SA(scanner);

            System.out.println(sa.getOutputAsString());

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        scanner.close();
    }
}
