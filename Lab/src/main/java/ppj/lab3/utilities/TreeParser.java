package ppj.lab3.utilities;

import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.Symbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;
import ppj.utilities.Node;

import java.util.Arrays;

/**
 * Metoda parsiranja stabla pomocu rekurzija
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 23/12/2020
 */
public class TreeParser {
    public static Node<Symbol> generateNodeTree(String[] inputLines) {
        return generateNodeTreeHelper(inputLines, 0);
    }

    private static Node<Symbol> generateNodeTreeHelper(String[] inputLines, int lineNumber) {
        String line = inputLines[lineNumber];
        int thisLevel = line.indexOf(line.trim());

        Node<Symbol> currentNode = new Node<>(parseLine(line));


        while (true) {
            //End of tree
            if (lineNumber + 1 >= inputLines.length)
                break;

            //Dohvati info i iducem
            String nextLine = inputLines[++ lineNumber];
            int nextLevel = nextLine.indexOf(nextLine.trim());

            //Iduci je iznad ili isti sibling
            if (nextLevel <= thisLevel)
                break;

            //U trenutni kao djecu spremi samo jedan nivo ispod
            if (nextLevel == thisLevel + 1) {
                var child = generateNodeTreeHelper(inputLines, lineNumber);
                child.setParent(currentNode);
                currentNode.addChild(child);
            }
        }

        return currentNode;
    }

    private static Symbol parseLine(String line) {
        String trimmed = line.trim();

        if (trimmed.matches("<\\w+>")) {
            return new NonTerminalSymbol(trimmed);
        } else {
            var array = trimmed.split("\\s+");

            String name = array[0];
            int lineNumber = Integer.parseInt(array[1]);
            String[] lexicalUnits = Arrays.stream(array, 2, array.length)
                    .toArray(String[]::new);

            return new TerminalSymbol(name, lineNumber, lexicalUnits);
        }
    }
}
