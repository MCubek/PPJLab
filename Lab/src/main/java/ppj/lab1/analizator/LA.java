package ppj.lab1.analizator;

import ppj.utilities.Automaton;
import ppj.utilities.Pair;

import java.io.*;
import java.util.*;

/**
 * Metoda koja predstavlja leksicki analizator
 * Stvara leksicke jedinke
 *
 * @author FraneB, MatejC
 */
public class LA {
    private static final int MAX_SINGLE_SYMBOL_LENGTH = 20;
    private List<String> states;
    private Set<String> uniformSymbols;
    private Map<Pair<String, Automaton>, List<String>> automatonRules;
    private LinkedList<String> resultStringsList;

    public LA(Scanner scanner) throws IOException, ClassNotFoundException {
        readSerialization();

        parseProgramAsString(getInputString(scanner));

        scanner.close();
    }

    public LA(File file) throws IOException, ClassNotFoundException {
        this(new Scanner(file));
    }

    /**
     * Meotda koja na drugi nacin parsira ulazni niz i stvara leksicke jedinke
     *
     * @param program kao veliki string
     * @author Matej
     */
    private void parseProgramAsString(String program) {
        int lineCount = 1;
        //Pocetno stanje
        String currentState = states.get(0);
        StringBuilder symbol = new StringBuilder();
        String foundSymbol = "";
        Pair<String, Automaton> symbolAutom = null;

        //Symbol je niz koji se trenutno ispituje
        //foundSymbol je zadnji niz koji je automat prihvaio

        int currentPosition = 0;

        //Dok ima znakova koji nisu procitani
        while (currentPosition < program.length()) {
            //Barem jedan automat pronaden
            boolean foundAutom = false;

            //Brojac koliko se puta povecao simbol nakon zadnjeg pronadenog automata
            int notFoundCounter = 0;
            for (int i = currentPosition; i < program.length(); i++) {
                //Dodaja znaka u simbol
                symbol.append(program.charAt(i));

                boolean automatonFoundForCurrentSymbol = false;

                //Ispit automata
                for (var symbolAutomTestPair : automatonRules.keySet()) {
                    if (symbolAutomTestPair.getLeft().equals(currentState) && symbolAutomTestPair.getRight().computeInput(symbol.toString())) {
                        foundSymbol = symbol.toString();
                        symbolAutom = symbolAutomTestPair;

                        foundAutom = true;
                        automatonFoundForCurrentSymbol = true;
                        notFoundCounter = 0;
                        break;
                    }
                }
                //Nije pronaden automat za trenutni simbol
                if (! automatonFoundForCurrentSymbol)
                    notFoundCounter++;

                //Isprobano je const iteracije simbola bez pronalaska automata
                //Izadi iz petlje
                if (notFoundCounter >= MAX_SINGLE_SYMBOL_LENGTH) break;
            }

            if (foundAutom) {
                boolean uniformSymbolAdded = false;

                for (var rule : automatonRules.get(symbolAutom)) {
                    if (uniformSymbols.contains(rule)) {
                        uniformSymbolAdded = true;

                        currentPosition += foundSymbol.length();
                        resultStringsList.add(rule + " " + lineCount + " " + foundSymbol);
                    } else if (rule.equals("NOVI_REDAK")) {
                        lineCount++;
                    } else if (rule.contains("UDJI_U_STANJE")) {
                        currentState = rule.split(" ")[1];
                    } else if (rule.contains("VRATI_SE")) {
                        int backIndex = Integer.parseInt(rule.split(" ")[1]);
                        int backPos = foundSymbol.length() - backIndex;

                        //Zamijeni zadnji element ako je VRATI_SE, a on je za isti znak dodan
                        if (uniformSymbolAdded) {
                            String operand = resultStringsList.removeLast().split(" ")[0];
                            resultStringsList.addLast(operand + " " + lineCount + " " + foundSymbol.substring(0, backIndex));
                        }

                        currentPosition -= backPos;
                    } else if (rule.equals("-")) {
                        currentPosition += foundSymbol.length();
                    }

                    symbol = new StringBuilder();
                }
            } else {
                //Odbaci znak ako nije pronaden automat
                currentPosition++;
                symbol = new StringBuilder();
            }
        }
    }

    /**
     * Metoda koja iz scannera cita ulaz i zapisuje ga u veliki string
     *
     * @param scanner scanner s ulaznim podacima
     * @return cijeli program kao string
     */
    private String getInputString(Scanner scanner) {
        StringBuilder stringBuilder = new StringBuilder();
        while (scanner.hasNextLine()) {
            stringBuilder.append(scanner.nextLine()).append("\n");
        }
        return stringBuilder.toString();
    }

    /**
     * Metoda cita datoteke generirane od generatora i sprema ih
     *
     * @throws IOException            ako je problem s datotekom
     * @throws ClassNotFoundException ako je problem s klasom
     */
    @SuppressWarnings("unchecked")
    private void readSerialization() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/ppj/lab1/analizator/states.ser");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.states = (List<String>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab1/analizator/symbols.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.uniformSymbols = (Set<String>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab1/analizator/rules.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.automatonRules = (Map<Pair<String, Automaton>, List<String>>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        this.resultStringsList = new LinkedList<>();

    }

    public LinkedList<String> getResultStringsList() {
        return resultStringsList;
    }

    /**
     * Metoda vraca ocekivani izlaz:
     * Tablica znakova i niz uniformih znakova
     *
     * @return izlaz kao string
     */
    public String getOutputAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        resultStringsList.forEach((v) -> stringBuilder.append(v).append("\r\n"));

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        try {
            LA lexer = new LA(new Scanner(System.in));

            System.out.println(lexer.getOutputAsString());

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }
}
