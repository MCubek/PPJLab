package ppj.lab3.utilities.scope;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 02/01/2021
 */
class ScopeTest {
    @Test
    void testChar1() {
        char[] array = {'t', 'n', '0', '\'', '"', '\\'};

        for (char c : array) {
            assertTrue(Scope.charConstValid("\\" + c));
        }

        assertTrue(Scope.charConstValid("d"));

        assertFalse(Scope.charConstValid("\\x"));
    }

    @Test
    void testChar2() {
        assertTrue(Scope.charArrayValid("dsa\\\"aa"));
        assertTrue(Scope.charArrayValid("ds\\\\"));
        assertTrue(Scope.charArrayValid("dddddsssaa"));
        assertTrue(Scope.charArrayValid("ds\\\\\\t"));

        assertFalse(Scope.charArrayValid("aa\\x"));
    }
}