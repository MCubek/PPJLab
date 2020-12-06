package ppj.lab1;

import ppj.lab1.utilities.Automaton;
import ppj.lab1.utilities.AutomatonGenerator;
import ppj.utilities.Pair;
import ppj.lab1.utilities.RuleRegex;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generator leksickog analizatora
 *
 * @author MatejC FraneB
 */
public class GLA {
    private final List<String> states;
    private final Set<String> uniformSymbols;
    private final LinkedHashMap<RuleRegex, List<String>> rules;

    private Map<Pair<String, Automaton>, List<String>> automatonRules;

    /**
     * Konstuktor generator s datotekom kao izvorom konfiguracije
     *
     * @param inputFile datoteka konfiguracije
     * @throws FileNotFoundException    Ako datoteka nije pronadena
     * @throws IllegalArgumentException ako je predana nevalidna konfiguracija
     */
    public GLA(File inputFile) throws FileNotFoundException {
        this(new Scanner(inputFile));
    }

    /**
     * Konstruktor koji koristi scanner
     *
     * @param scanner scanner konfiguracije
     * @throws IllegalArgumentException ako nije predan scanner ili je predana nevalidna konfiguracija
     */
    public GLA(Scanner scanner) {
        if (scanner == null) throw new IllegalArgumentException("Scanner is null");
        Map<String, String> regex1 = new LinkedHashMap<>();

        String line;
        //Populirati regex
        while ((line = scanner.nextLine()).matches("\\{..*} .*")) {
            //Pronadi index kraja imena
            int index = line.indexOf('}');
            regex1.put(line.substring(1, index), line.substring(index + 2));
        }

        //Pripremi regularne izraze za generiranje konacnog automata
        for (String reg : regex1.keySet()) {
            String regEx = regex1.get(reg);
            //Za svaku regularnu definiciju pronadi u regularnom izrazu druge regularne definicije ako postoje
            for (int indexOfRegDef = regEx.indexOf('{'); indexOfRegDef >= 0; indexOfRegDef = regEx.indexOf('{', indexOfRegDef + 1)) {
                String regRefDef = regEx.substring(indexOfRegDef + 1, regEx.indexOf('}', indexOfRegDef));
                String replaceRegex = regex1.get(regRefDef);
                //Zamjeni regularne definicije regularnim izrazima
                if (replaceRegex != null) {
                    regEx = regEx.replace(regEx.substring(indexOfRegDef, regEx.indexOf('}') + 1), "(" + replaceRegex + ")");
                }

            }
            regex1.replace(reg, regEx);
        }

        //Populiraj stanja
        //Prvo stanje je pocetno
        if (! line.matches("%X .*"))
            throw new IllegalArgumentException("File missing states description");
        states = Arrays.stream(line.substring(3).split(" ")).collect(Collectors.toList());

        //Populiraj leksicke jedinke
        if (! (line = scanner.nextLine()).matches("%L .*"))
            throw new IllegalArgumentException("File missing uniform Symbols");
        uniformSymbols = Arrays.stream(line.substring(3).split(" ")).collect(Collectors.toSet());


        //Pravila
        rules = new LinkedHashMap<>();
        //Dok ima pravila
        while (scanner.hasNextLine() && (line = scanner.nextLine()).matches("<.*>.*")) {
            //Parsiraj ime i regex
            int index = line.indexOf('>');
            String state = line.substring(1, index);
            String regex = line.substring(index + 1);

            for (int indexOfRegDef = regex.indexOf('{'); indexOfRegDef >= 0; indexOfRegDef = regex.indexOf('{', indexOfRegDef + 1)) {
                if (regex.indexOf('}', indexOfRegDef) != - 1) {
                    String regRefDef = regex.substring(indexOfRegDef + 1, regex.indexOf('}', indexOfRegDef));
                    String replaceRegex = regex1.get(regRefDef);
                    //Zamjeni regularne definicije regularnim izrazima
                    if (replaceRegex != null) {
                        regex = regex.replace(regex.substring(indexOfRegDef, regex.indexOf('}') + 1), "(" + replaceRegex + ")");
                    }
                }
            }

            boolean duplicate = false;

            RuleRegex ruleRegex = new RuleRegex(state, regex);
            if (rules.containsKey(ruleRegex))
                duplicate = true;

            if (! duplicate)
                rules.put(ruleRegex, new ArrayList<>());

            //Parsiraj naredbe
            while (! (line = scanner.nextLine()).matches("}")) {
                if (! line.equals("{") && ! line.equals("}") && ! duplicate) {

                    List<String> ruleList = rules.get(ruleRegex);
                    ruleList.add(line);
                    rules.put(ruleRegex, ruleList);
                }
            }
        }

        scanner.close();
        automatonRules = new AutomatonGenerator(rules).getAutomatonRules();
    }

    /**
     * Metoda serializira podatke generatora kako bi ih leksicki analziator mogao korisiti
     *
     * @throws IOException ako datoteke gdje se zapisuje ne postoje
     */
    public void serializeOutput() throws IOException {
        File file = new File("src/main/java/ppj/lab1/analizator/states.ser");
        FileOutputStream fileOutputStream = new FileOutputStream(file, false);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(states);
        fileOutputStream.close();
        objectOutputStream.close();

        file = new File("src/main/java/ppj/lab1/analizator/symbols.ser");
        fileOutputStream = new FileOutputStream(file, false);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(uniformSymbols);
        fileOutputStream.close();
        objectOutputStream.close();

        file = new File("src/main/java/ppj/lab1/analizator/rules.ser");
        fileOutputStream = new FileOutputStream(file, false);
        objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(automatonRules);
        fileOutputStream.close();
        objectOutputStream.close();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GLA gla = new GLA(scanner);

        try {
            gla.serializeOutput();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

