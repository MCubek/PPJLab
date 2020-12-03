package ppj.lab2;

import ppj.lab2.utilities.actions.Action;
import ppj.lab2.utilities.actions.PutAction;
import ppj.utilities.Pair;
import ppj.utilities.ParserGenerator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 14/11/2020
 */
public class GSA {
    private List<String> nonTerminalSymbols;
    private List<String> terminalSymbols;
    private List<String> synchronizationSymbols;
    private Map<String, List<List<String>>> productions;
    private Map<Pair<String, String>, Integer> productionPriorites = new LinkedHashMap<>();
    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, PutAction> newStateTable;

    public GSA(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        parseInput(scanner);
        scanner.close();
        generateTables();
    }

    private void generateTables() {
        ParserGenerator generator = new ParserGenerator(productions, terminalSymbols, nonTerminalSymbols, productionPriorites);
        this.actionTable = generator.getActionTable();
        this.newStateTable = generator.getNewStateTable();
    }

    public GSA(Scanner scanner) {
        Objects.requireNonNull(scanner);
        parseInput(scanner);
        generateTables();
    }

    private void parseInput(Scanner scanner) {
        //Prva linija -> nezavrsni znakovi
        String line = scanner.nextLine();
        if (! line.startsWith("%V")) throw new IllegalArgumentException("Line doesn't start with %V");
        nonTerminalSymbols = Arrays.asList(line.substring(3).split("\\s+"));

        //Druga linija -> zavrsni znakovi
        line = scanner.nextLine();
        if (! line.startsWith("%T")) throw new IllegalArgumentException("Line doesn't start with %T");
        terminalSymbols = Arrays.asList(line.substring(3).split("\\s+"));

        line = scanner.nextLine();
        //Treca linija -> sinkronizacijski znakovi
        if (! line.startsWith("%Syn")) throw new IllegalArgumentException("Line doesn't start with %Syn");
        synchronizationSymbols = Arrays.asList(line.substring(5).split("\\s+"));

        //Ostale linije -> produkcije gramatike
        if (! scanner.hasNextLine()) throw new IllegalArgumentException("No productions");

        int counter = 0;
        productions = new HashMap<>();
        line = scanner.nextLine();
        while (scanner.hasNextLine()) {
            String nonTerminalSymbol = line;
            List<List<String>> symbolProductions = new ArrayList<>();
            String priorityRightSide = "";

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (! line.startsWith(" ")) break;
                List<String> symbols = Arrays.asList(line.trim().split("\\s+"));
                symbolProductions.add(symbols);
                for(String s : symbols)
                    priorityRightSide = priorityRightSide + s;
                productionPriorites.put(new Pair<>(nonTerminalSymbol,priorityRightSide),counter++);
                priorityRightSide = "";
            }



            if (productions.containsKey(nonTerminalSymbol))
                symbolProductions.addAll(0, productions.get(nonTerminalSymbol));
            productions.put(nonTerminalSymbol, symbolProductions);
        }
    }

    public void serializeOutput() throws IOException {

        Path path = Path.of("src/main/java/ppj/lab2/analizator");

        OutputStream out;
        try (ObjectOutputStream ois = new ObjectOutputStream(Files.newOutputStream(path.resolve("synchronizationSymbols.ser")))) {
            ois.writeObject(synchronizationSymbols);
        }

        try (ObjectOutputStream ois = new ObjectOutputStream(Files.newOutputStream(path.resolve("actionTable.ser")))) {
            ois.writeObject(actionTable);
        }

        try (ObjectOutputStream ois = new ObjectOutputStream(Files.newOutputStream(path.resolve("newStateTable.ser")))) {
            ois.writeObject(newStateTable);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GSA gsa = new GSA(scanner);
        scanner.close();
        try {
            gsa.serializeOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
