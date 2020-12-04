package ppj.lab2;

import ppj.lab2.utilities.ParserGen2;
import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.Symbol;
import ppj.lab2.utilities.actions.Action;
import ppj.lab2.utilities.actions.PutAction;
import ppj.utilities.Pair;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 14/11/2020
 */
public class GSA {
    private List<Symbol> nonTerminalSymbols;
    private List<Symbol> terminalSymbols;
    private List<Symbol> synchronizationSymbols;
    private List<Production> productions;
    private Map<Pair<Integer, String>, Action> actionTable;
    private Map<Pair<Integer, String>, PutAction> newStateTable;

    public GSA(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        parseInput(scanner);
        scanner.close();
        generateTables();
    }

    private void generateTables() {
        ParserGen2 generator = new ParserGen2(nonTerminalSymbols, terminalSymbols, productions);

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
        nonTerminalSymbols = Arrays.stream(line.substring(3).split("\\s+"))
                .map(v -> Symbol.of(v, false))
                .collect(Collectors.toList());

        //Druga linija -> zavrsni znakovi
        line = scanner.nextLine();
        if (! line.startsWith("%T")) throw new IllegalArgumentException("Line doesn't start with %T");
        terminalSymbols = Arrays.stream(line.substring(3).split("\\s+"))
                .map(v -> Symbol.of(v, true))
                .collect(Collectors.toList());

        line = scanner.nextLine();
        //Treca linija -> sinkronizacijski znakovi
        if (! line.startsWith("%Syn")) throw new IllegalArgumentException("Line doesn't start with %Syn");
        synchronizationSymbols = Arrays.stream(line.substring(3).split("\\s+"))
                .map(v -> Symbol.of(v, true))
                .collect(Collectors.toList());

        //Ostale linije -> produkcije gramatike
        if (! scanner.hasNextLine()) throw new IllegalArgumentException("No productions");

        int counter = 0;
        productions = new ArrayList<>();

        line = scanner.nextLine();
        while (scanner.hasNextLine()) {
            Symbol nonTerminalSymbol = Symbol.of(line, false);

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (! line.startsWith(" ")) break;

                Production production = new Production(nonTerminalSymbol, Symbol.toListOfSymbols(line.trim().split("\\s+")), counter);

                productions.add(production);
            }

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
