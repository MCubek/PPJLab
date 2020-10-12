package ppj.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class GLA {
    private final Map<String, String> regex;
    private final List<String> states;
    private final Set<String> uniformSimbols;
    private final Map<RuleName, List<String>> rules;

    /**
     * Novi Generator s datotekom kao izvorom konfiguracije
     *
     * @param inputFile datoteka konfiguracije
     * @throws FileNotFoundException Ako datoteka nije pronadena
     */
    public GLA(File inputFile) throws FileNotFoundException {
        this(new Scanner(inputFile));
    }

    public GLA(String inputString) {
        this(new Scanner(inputString));

    }

    public GLA(Scanner scanner) {
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
        line = scanner.nextLine();
        if (! line.matches("%L .*"))
            throw new IllegalArgumentException("File missing states description");
        uniformSimbols = Arrays.stream(line.substring(3).split(" ")).collect(Collectors.toSet());

        rules = new HashMap<>();

        while (scanner.hasNextLine() && (line = scanner.nextLine()).matches("<.*>.*")) {
            int index = line.indexOf('>');
            RuleName ruleName = new RuleName(line.substring(1, index), line.substring(index + 1));
            rules.put(ruleName, new ArrayList<>());
            while (! (line = scanner.nextLine()).matches("}")) {
                if (! line.equals("{") && ! line.equals("}")) {
                    List<String> ruleList = rules.get(ruleName);
                    ruleList.add(line);
                    rules.put(ruleName, ruleList);
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

