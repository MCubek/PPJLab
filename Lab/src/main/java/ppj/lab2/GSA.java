package ppj.lab2;

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

    public GSA(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        parseInput(scanner);
        scanner.close();
    }

    public GSA(Scanner scanner) {
        Objects.requireNonNull(scanner);
        parseInput(scanner);
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

        productions = new HashMap<>();
        line = scanner.nextLine();
        while (scanner.hasNextLine()) {
            String nonTerminalSymbol = line;
            List<List<String>> symbolProductions = new ArrayList<>();

            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                if (! line.startsWith(" ")) break;
                List<String> symbols = Arrays.asList(line.trim().split("\\s+"));
                symbolProductions.add(symbols);
            }

            if (productions.containsKey(nonTerminalSymbol))
                symbolProductions.addAll(0, productions.get(nonTerminalSymbol));
            productions.put(nonTerminalSymbol, symbolProductions);
        }
        ParserGenerator generator = new ParserGenerator(productions, terminalSymbols, nonTerminalSymbols);
    }

    public void serializeOutput() throws IOException {
        // TODO: 15.11.2020. Serialize output

        Path path = Path.of("src/main/java/ppj/lab2/analizator");

        OutputStream out;
        try (ObjectOutputStream ois = new ObjectOutputStream(Files.newOutputStream(path.resolve("synchronizationSymbols.ser")))) {
            ois.writeObject(synchronizationSymbols);
        }

/*        try (ObjectOutputStream ois = new ObjectOutputStream(Files.newOutputStream(path.resolve("actionTable.ser")))) {
            ois.writeObject();
        }

        try (ObjectOutputStream ois = new ObjectOutputStream(Files.newOutputStream(path.resolve("newStateTable.ser")))) {
            ois.writeObject();
        }*/
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
