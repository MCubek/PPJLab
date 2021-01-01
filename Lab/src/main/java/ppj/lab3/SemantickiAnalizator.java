package ppj.lab3;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.TreeParser;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.Symbol;
import ppj.utilities.Node;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Lab03 Semanticki analizator za jezik ppjC
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 13/12/2020
 */
public class SemantickiAnalizator {
    private final Node<Symbol> root;

    /**
     * Konstruktor koji parsira uzaz i stvara odgovarajuce strukture podataka
     *
     * @param bufferedReader input stream
     */
    public SemantickiAnalizator(BufferedReader bufferedReader) {
        this.root = TreeParser.generateNodeTree(bufferedReader.lines()
                .toArray(String[]::new));
    }

    /**
     * Konstruktor koji cita iz datoteke
     *
     * @param filePath path datoteke
     * @throws IOException ako se dogodi IOexception
     */
    public SemantickiAnalizator(Path filePath) throws IOException {
        this(Files.newBufferedReader(filePath));
    }

    public Node<Symbol> getRoot() {
        return root;
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SemantickiAnalizator sa = new SemantickiAnalizator(reader);

        try {
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        SemanticProduction start = new SemanticProduction(sa.root);
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
        }
    }
}
