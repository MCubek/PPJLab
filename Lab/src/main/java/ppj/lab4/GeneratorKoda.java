package ppj.lab4;

import ppj.lab3.SemanticException;
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
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 08/01/2021
 */
public class GeneratorKoda {
    private final Node<Symbol> root;

    public static CodeBuilder codeBuilder;
    public static String MUL_LABEL = "F_MUL";
    public static String DIV_LABEL = "F_DIV";
    public static String MOD_LABEL = "F_MOD";
    public static String MAIN_LABEL = "F_MAIN";
    public static Map<String, Integer> constants = new HashMap<>();
    public static LinkedList<String> returnLabels = new LinkedList<>();
    public static LinkedList<String> breakLabels = new LinkedList<>();

    private static int labelCounter = 0;

    public GeneratorKoda(BufferedReader bufferedReader) {
        this.root = TreeParser.generateNodeTree(bufferedReader.lines()
                .toArray(String[]::new));
        codeBuilder = new CodeBuilder();
    }

    public GeneratorKoda(Path inputPath) throws IOException {
        this(Files.newBufferedReader(inputPath));
    }

    public String getAssembly() {
        programStart();

        SemanticProduction start = new SemanticProduction(root);
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);

        try {
            action.checkProduction(start, new Scope(null));
        } catch (SemanticException e) {
            return e.getMessage();
        }

        addDefaultFunctions();
        addConstantsAndGlobalVars();

        return codeBuilder.toString();
    }

    private void programStart() {
        codeBuilder.addCommand("MOVE 40000, R7");
        codeBuilder.addCommand("CALL " + MAIN_LABEL);
        codeBuilder.addCommand("HALT");
        codeBuilder.addEmptyLine();
    }

    private void addDefaultFunctions() {
        multiplyFunction();
        divideFunction();
        moduloFunction();
    }

    private void multiplyFunction() {
        codeBuilder.addEmptyLine();

        codeBuilder.addCommandWithLabel(MUL_LABEL, "PUSH R0");
        codeBuilder.addCommand("PUSH R1");

        codeBuilder.addCommand("LOAD R0, (R7+0C)");
        codeBuilder.addCommand("LOAD R1, (R7+10)");

        codeBuilder.addCommand("MOVE 0, R6");

        String label = getNextLabel();

        codeBuilder.addCommandWithLabel(label, "ADD R0, R6, R6");
        codeBuilder.addCommand("SUB R1, 1 , R1");
        codeBuilder.addCommand("JP_NZ " + label);

        codeBuilder.addCommand("POP R1");
        codeBuilder.addCommand("POP R0");
        codeBuilder.addCommand("RET");
    }

    private void divideFunction() {
        codeBuilder.addEmptyLine();

        codeBuilder.addCommandWithLabel(DIV_LABEL, "PUSH R0");
        codeBuilder.addCommand("PUSH R1");
        codeBuilder.addCommand("PUSH R2");

        //Dijeljenik
        codeBuilder.addCommand("LOAD R1, (R7+14)");
        //Dijelitelj
        codeBuilder.addCommand("LOAD R2, (R7+10)");

        //R0 je boolean za negativno
        codeBuilder.addCommand("MOVE 0, R0");

        codeBuilder.addCommand("MOVE 0, R6");

        String firstPositive = getNextLabel();
        String allPositive = getNextLabel();
        String countFinished = getNextLabel();
        String end = getNextLabel();

        codeBuilder.addCommand("CMP R1, 0");
        codeBuilder.addCommand("JP_SGT " + firstPositive);

        codeBuilder.addCommand("XOR R0, 1 , R0");
        codeBuilder.addCommand("XOR R1, -1, R1");
        codeBuilder.addCommand("ADD R1, 1, R1");

        codeBuilder.addCommandWithLabel(firstPositive, "CMP R2, 0");
        codeBuilder.addCommand("JP_SGT " + allPositive);

        codeBuilder.addCommand("XOR R0, 1 , R0");
        codeBuilder.addCommand("XOR R2, -1, R2");
        codeBuilder.addCommand("ADD R2, 1, R2");

        codeBuilder.addCommandWithLabel(allPositive, "SUB R1, R2, R1");
        codeBuilder.addCommand("JP_ULT " + countFinished);
        codeBuilder.addCommand("ADD R6, 1, R6");
        codeBuilder.addCommand("JP " + allPositive);

        codeBuilder.addCommandWithLabel(countFinished, "CMP R0, 0");
        codeBuilder.addCommand("JP_EQ " + end);

        codeBuilder.addCommand("XOR R6, -1, R6");
        codeBuilder.addCommand("ADD R6, 1, R6");

        codeBuilder.addCommandWithLabel(end, "POP R2");
        codeBuilder.addCommand("POP R1");
        codeBuilder.addCommand("POP R0");
        codeBuilder.addCommand("RET");
    }

    private void moduloFunction() {
        codeBuilder.addEmptyLine();

        codeBuilder.addCommandWithLabel(MOD_LABEL, "PUSH R1");
        codeBuilder.addCommand("PUSH R2");

        //Dijeljenik
        codeBuilder.addCommand("LOAD R1, (R7+10)");
        //Dijelitelj
        codeBuilder.addCommand("LOAD R2, (R7+0C)");

        codeBuilder.addCommand("PUSH R1");
        codeBuilder.addCommand("PUSH R2");
        codeBuilder.addCommand("CALL " + DIV_LABEL);
        codeBuilder.addCommand("ADD R7, 8, R7");

        codeBuilder.addCommand("PUSH R6");
        codeBuilder.addCommand("PUSH R2");
        codeBuilder.addCommand("CALL " + MUL_LABEL);
        codeBuilder.addCommand("ADD R7, 8, R7");

        codeBuilder.addCommand("SUB R1, R6, R6");

        codeBuilder.addCommand("POP R2");
        codeBuilder.addCommand("POP R1");
        codeBuilder.addCommand("RET");
    }

    private void addConstantsAndGlobalVars() {
        for (Map.Entry<String, Integer> entry : constants.entrySet()) {
            codeBuilder.addCommandWithLabel(getConstantLabel(entry.getKey()),
                    "DW %D " + entry.getValue());
        }
    }

    public static String getGlobalLabel(String name) {
        return String.format("G_%S", name);
    }

    public static String getFunctionLabel(String name) {
        return String.format("F_%S", name.toUpperCase());
    }

    public static String getConstantLabel(String name) {
        return String.format("C_%S", name.toUpperCase());
    }

    public static String getNextLabel() {
        return "L_" + labelCounter++;
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        GeneratorKoda generatorKoda = new GeneratorKoda(reader);

        try {
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            Files.write(Path.of("./frisc.a"), generatorKoda.getAssembly().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
