package ppj.lab2.analizator;

import ppj.lab2.utilities.actions.Action;
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

    private List<String> terminalSymbols;
    //Map<<Par<Stanje,Znak>, akcija>
    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, Action> newStateTable;

    //Format "PRAVILO REDAK ZNAK"
    private List<String> lexicalUnits;

    /**
     * Testni konstruktor
     *
     * @param terminalSymbols sinkromizacijski znakovi
     * @param actionTable     tablica akcije
     * @param newStateTable   tablica novo stanje
     * @param lexicalUnits    lista leksicnih jedinki
     */
    public SA(List<String> terminalSymbols, Map<Pair<Integer, String>, Action> actionTable, Map<Pair<Integer, String>, Action> newStateTable, List<String> lexicalUnits) {
        this.terminalSymbols = terminalSymbols;
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
        Queue<Integer> stateQueue = new LinkedList<>();
        Queue<String> symbolsQueue = new LinkedList<>();
        stateQueue.add(STARTING_STATE);

        for (int position = 0; position < lexicalUnits.size() && ! stateQueue.isEmpty(); ) {
            String[] lexicalUnitElements = lexicalUnits.get(position).split(" ");
            String symbol = lexicalUnitElements[0];
            int lineIndex = Integer.parseInt(lexicalUnitElements[1]);

            Action action = actionTable.get(new Pair<>(stateQueue.peek(), symbol));


        }
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
        FileInputStream fileInputStream = new FileInputStream("src/main/java/ppj/lab2/analizator/terminalSymbols.ser");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.terminalSymbols = (List<String>) objectInputStream.readObject();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab2/analizator/actionTable.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.actionTable = (Map<Pair<Integer, String>, Action>) objectInputStream.readObject();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab2/analizator/newStateTable.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.newStateTable = (Map<Pair<Integer, String>, Action>) objectInputStream.readObject();

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
        // TODO: 15.11.2020. Return string
        return null;
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
