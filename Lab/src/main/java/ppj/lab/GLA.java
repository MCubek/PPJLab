package ppj.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Generator leksickog analizatora
 *
 * @author MatejC FraneB
 */
public class GLA {
    private final Set<RegEx> regexList;
    //prvo stanje je pocetno
    private final List<String> states;
    private final Set<String> uniformSymbols;
    private final Map<RuleRegex, List<String>> rules;
    //Key je stanje, value je mapa kojoj je key regex a value lista akcija
    private final Map<String, Map<String, List<String>>> ruleRegAction;

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
     * Konstuktor generatora s Stringom kao izvorom konfiguracije
     *
     * @param inputString String konfiguracije
     * @throws IllegalArgumentException ako je predana nevalidna konfiguracija
     */
    public GLA(String inputString) {
        this(new Scanner(inputString));

    }

    /**
     * Konstruktor koji koristi scanner i nad njime parsira ulaznu konfirguraciju
     *
     * @param scanner scanner konfiguracije
     * @throws IllegalArgumentException ako nije predan scanner ili je predana nevalidna konfiguracija
     */
    private GLA(Scanner scanner) {
        if (scanner == null) throw new IllegalArgumentException("Scanner is null");

        regexList = new HashSet<>();
        String line;
        //Populirati regex
        while ((line = scanner.nextLine()).matches("\\{..*} .*")) {
            //Pronadi index kraja imena
            int index = line.indexOf('}');
            regexList.add(new RegEx(line.substring(1, index), line.substring(index + 1)));
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
        ruleRegAction = new HashMap<>();
        //Dok ima pravila
        while (scanner.hasNextLine() && (line = scanner.nextLine()).matches("<.*>.*")) {
            //Parsiraj ime i regex
            int index = line.indexOf('>');
            String state = line.substring(1, index);
            String regex = line.substring(index + 1);

            //1 nacin s posebnom klasom
            RuleRegex ruleRegex = new RuleRegex(state, regex);
            rules.put(ruleRegex, new ArrayList<>());

            //2 nacin s mapom u mapi
            Map<String, List<String>> regexMap = ruleRegAction.getOrDefault(state, new HashMap<>());
            List<String> actionList = regexMap.getOrDefault(regex, new ArrayList<>());


            //Parsiraj naredbe
            while (! (line = scanner.nextLine()).matches("}")) {
                if (! line.equals("{") && ! line.equals("}")) {
                    //1 Nacin
                    List<String> ruleList = rules.get(ruleRegex);
                    ruleList.add(line);
                    rules.put(ruleRegex, ruleList);
                    //2 Nacin
                    actionList.add(line);
                }
            }
            regexMap.put(regex, actionList);
            ruleRegAction.put(state, regexMap);
        }
        scanner.close();

        replaceRegexReferences();
    }

    /**
     * Popuniti reference regularnih izraza sa njihovim vrijednostima
     */
    private void replaceRegexReferences() {
        regexList.forEach(regEx -> {
            List<String> referenceList = regEx.getReferences();
            if (! referenceList.isEmpty()) {
                for (String reference : referenceList) {
                    for (RegEx regExReplace : regexList) {
                        if (regExReplace.getName().equals(reference)) {
                            regEx.addExpressionFromAnother(regExReplace);
                            break;
                        }
                    }
                }
            }
        });
    }

    public static void main(String[] args) {
        try {
            File file = new File("Lab/src/main/resources/lab1_ppjLang[1].txt");
            System.out.println(file.getAbsolutePath());
            GLA gla = new GLA(file);
        } catch (FileNotFoundException exception) {
            System.err.println("File not FOUND!");
        }

    }
}

