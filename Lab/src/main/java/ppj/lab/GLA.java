package ppj.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class GLA {
    private final Map<String, String> regex;
    //prvo stanje je pocetno
    private final List<String> states;
    private final Set<String> uniformSymbols;
    private final Map<RuleRegex, List<String>> rules;

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
     * Konstuktor generaora s Stringom kao izborom konfiguracije
     *
     * @param inputString String konfiguracije
     * @throws IllegalArgumentException ako je predana nevalidna konfiguracija
     */
    public GLA(String inputString) {
        this(new Scanner(inputString));

    }

    /**
     * Konstruktor koji koristi scanner
     *
     * @param scanner scanner konfiguracije
     * @throws IllegalArgumentException ako nije predan scanner ili je predana nevalidna konfiguracija
     */
    private GLA(Scanner scanner) {
        if (scanner == null) throw new IllegalArgumentException("Scanner is null");
        regex = new HashMap<>();

        String line;
        //Populirati regex
        while ((line = scanner.nextLine()).matches("\\{..*} .*")) {
            //Pronadi index kraja imena
            int index = line.indexOf('}');
            regex.put(line.substring(1, index), line.substring(index + 1));
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

        rules = new HashMap<>();
        //Dok ima pravila
        while (scanner.hasNextLine() && (line = scanner.nextLine()).matches("<.*>.*")) {
            //Parsiraj ime i regex
            int index = line.indexOf('>');
            String state = line.substring(1, index);
            String regex = line.substring(index + 1);
            RuleRegex ruleRegex = new RuleRegex(state, regex);
            rules.put(ruleRegex, new ArrayList<>());
            //Parsiraj naredbe
            while (! (line = scanner.nextLine()).matches("}")) {
                if (! line.equals("{") && ! line.equals("}")) {
                    List<String> ruleList = rules.get(ruleRegex);
                    ruleList.add(line);
                    rules.put(ruleRegex, ruleList);
                }
            }
        }

        scanner.close();
    }

    public static void main(String[] args) {
        try {
            File file = new File("Lab/src/main/resources/lab1_ppjLang.txt");
            System.out.println(file.getAbsolutePath());
            GLA gla = new GLA(file);
        } catch (FileNotFoundException exception) {
            System.err.println("File not FOUND!");
        }

    }
}

