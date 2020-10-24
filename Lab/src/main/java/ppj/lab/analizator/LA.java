package ppj.lab.analizator;

import ppj.lab.Automaton;
import ppj.lab.Pair;

import java.io.*;
import java.util.*;

public class LA {
    private List<String> states;
    private Set<String> uniformSymbols;
    private Map<Pair<String, Automaton>, List<String>> automatonRules;
    private Map<Pair<String, Integer>, String> result;

    public LA(Scanner scanner) throws IOException, ClassNotFoundException {
        readSerialization();
        parseProgram(scanner);
    }

    public LA(File file) throws IOException, ClassNotFoundException {
        this(new Scanner(file));
    }

    //nije gotovo
    private void parseProgram(Scanner scanner) {
        char line;
        List<Character> characterList = getCharacterList(scanner);

        int counter = 0;
        int beginIndex = 1;
        int endIndex = 0;
        int lastIndex = 1;
        int symbolIndex = 0;
        String state = this.getStates().get(0);
        String symbol = "";
        for (int i = 0; i < characterList.size(); i++) {
            symbol = symbol + characterList.get(i);
            for (Pair<String, Automaton> auto : this.getAutomatonRules().keySet()) {
                if (auto.getLeft().equals(state) && auto.getRight().computeInput(symbol)) {
                    for (String action : this.getAutomatonRules().get(auto)) {
                        if (uniformSymbols.contains(action)) {
                            Pair<String, Integer> symbolPair = new Pair<>(action, counter);
                            this.result.put(symbolPair, symbol);
                        } else if (action.equals("-")) {
                            continue;
                        } else if (action.equals("NOVI_REDAK")) {
                            counter++;
                        } else if (action.contains("UDJI_U_STANJE")) {
                            state = action.replace("UDJI_U_STANJE ", "");
                        } else {
                            System.err.println("Pogrešna akcija");
                        }
                    }
                }
            }
        }
        scanner.close();
    }


    private List<Character> getCharacterList(Scanner scanner) {
        if (scanner == null) throw new NullPointerException();

        List<Character> characterList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String stringLine = scanner.nextLine();
            for (int i = 0; i < stringLine.length(); i++) {
                characterList.add(stringLine.charAt(i));
            }
        }
        return characterList;
    }

    @SuppressWarnings("unchecked")
    private void readSerialization() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/ppj/lab/analizator/states.ser");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.states = (List<String>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab/analizator/symbols.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.uniformSymbols = (Set<String>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab/analizator/rules.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.automatonRules = (Map<Pair<String, Automaton>, List<String>>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        this.result = new LinkedHashMap<>();
    }

    public List<String> getStates() {
        return states;
    }

    public Set<String> getUniformSymbols() {
        return uniformSymbols;
    }

    public Map<Pair<String, Automaton>, List<String>> getAutomatonRules() {
        return automatonRules;
    }

    public Map<Pair<String, Integer>, String> getResult() {
        return result;
    }

    /**
     * Metoda vraća očekivani izlaz:
     * Tablica znakova i niz uniformih znakova
     *
     * @return izlaz kao string
     */
    public String getOutput() {
        //TODO
        return null;
    }

    public static void main(String[] args) {
        try {
            LA lexer = new LA(new Scanner(System.in));

/*            for(Pair<String,Integer> sym : lexer.getResult().keySet()){
                System.out.println(sym.getLeft() + " " + sym.getRight() + " " + lexer.getResult().get(sym) + "\n");
            }*/
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

    }
}
