package ppj.lab3;

import org.junit.jupiter.api.Test;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 23/12/2020
 */
class SemantickiAnalizatorTest {
    @Test
    void testIspitniPrimjer1() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/01_idn" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer3() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/03_niz_znakova" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer5() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/05_impl_int2char" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer7() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/07_nedef_fun" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer9() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/09_fun_povtip" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer11() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/11_niz" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer13() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/13_lval1" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }
    }

    @Test
    void testIspitniPrimjer15() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/15_cast1" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer17() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/17_log" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer19() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/19_cont_brk" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer21() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/21_ret_nonvoid" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer23() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/23_rek" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer25() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/25_fun_dekl_def" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer27() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/27_dekl_odmah_aktivna" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

    @Test
    void testIspitniPrimjer29() throws IOException {
        Path root = Path.of("src/test/resources/lab3/ispitniPrimjeri/29_for" +
                "");
        SemantickiAnalizator sem = new SemantickiAnalizator(root.resolve("test.in"));

        SemanticProduction start = new SemanticProduction(sem.getRoot());
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(start);
        try {
            action.checkProduction(start, new Scope(null));

        } catch (SemanticException e) {
            System.out.println(e.getMessage());
            assertEquals(Files.readString(root.resolve("test.out")).trim(), e.getMessage());
        }    }

}
