package ppj.lab2.analizator;

import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.actions.*;
import ppj.utilities.Node;
import ppj.utilities.NodeTreePrinter;
import ppj.utilities.Pair;

import java.io.*;
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

    private void generateTree() {
        rootNode = null;

        Queue<Integer> stateQueue = new LinkedList<>();
        Queue<Node<String>> symbolsQueue = new LinkedList<>();
        stateQueue.add(STARTING_STATE);

        for (int position = 0; position < lexicalUnits.size() && ! stateQueue.isEmpty(); ) {
            String lexicalUnit = lexicalUnits.get(position);
            String[] lexicalUnitElements = lexicalUnit.split(" ");
            String symbol = lexicalUnitElements[0];
            int lineIndex = Integer.parseInt(lexicalUnitElements[1]);

            Action action = actionTable.get(new Pair<>(stateQueue.peek(), symbol));
            Node<String> node = new Node<>(lexicalUnit);

            if (action instanceof AcceptAction) {
                rootNode = symbolsQueue.peek();
                break;
            } else if (action instanceof MoveAction) {
                MoveAction moveAction = (MoveAction) action;

                symbolsQueue.add(node);
                stateQueue.add(moveAction.getState());
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
                    //Skini sa stoga i spremi u listu djece
                    LinkedList<Node<String>> linkedList = new LinkedList<>();
                    for (int i = 0; i < reduceProduction.getRightStates().size(); i++) {
                        stateQueue.poll();
                        linkedList.addFirst(symbolsQueue.poll());
                    }
                    //Postavi djecu
                    newParentNode.setChildren(linkedList);
                }

                //Procitaj novo stanje iz tablice novo stanje
                PutAction putAction = newStateTable.get(new Pair<>(stateQueue.peek(), reduceProduction.getLeftState()));

                //Stavi na stog
                stateQueue.add(putAction.getState());
                symbolsQueue.add(newParentNode);

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
                                stateQueue.remove();
                                symbolsQueue.remove();
                            }
                        }
                    }
                }

            } else throw new RuntimeException("Action not defined");
        }
        if (stateQueue.isEmpty()) throw new RuntimeException("State stack shouldn't be empty.");
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
        FileInputStream fileInputStream = new FileInputStream("src/main/java/ppj/lab2/analizator/synchronizationSymbols.ser");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.synchronizationSymbols = (List<String>) objectInputStream.readObject();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab2/analizator/actionTable.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.actionTable = (Map<Pair<Integer, String>, Action>) objectInputStream.readObject();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab2/analizator/newStateTable.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.newStateTable = (Map<Pair<Integer, String>, PutAction>) objectInputStream.readObject();

        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();
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
