package ppj.lab3.utilities.symbols;

import org.junit.jupiter.api.Test;
import ppj.lab3.utilities.SemanticProduction;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 28/12/2020
 */
class TerminalSymbolTest {
    @Test
    void testToString() {
        var production = SemanticProduction.generateMapKeyProduction(
                new NonTerminalSymbol("<naredba_skoka>"),
                new TerminalSymbol("KR_RETURN", 4, new String[]{"return"}),
                new NonTerminalSymbol("<izraz>"),
                new TerminalSymbol("TOCKAZAREZ", 4, new String[]{";"})
        );
        assertEquals("<naredba_skoka> ::= KR_RETURN(4,return) <izraz> TOCKAZAREZ(4,;)",
                production.toString()
        );
    }
}