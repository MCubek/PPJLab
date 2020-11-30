package ppj.lab2.analizator;

import org.junit.jupiter.api.Test;
import ppj.lab2.GSA;
import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.actions.*;
import ppj.utilities.Pair;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 15/11/2020
 */
class SATest {

    @Test
    void testDemoProblem() {
        Map<Pair<Integer, String>, Action> actionTable;
        Map<Pair<Integer, String>, PutAction> newStateTable;

        actionTable = new HashMap<>();
        newStateTable = new HashMap<>();

        actionTable.put(new Pair<>(0, "a"), new MoveAction(3));
        actionTable.put(new Pair<>(1, "a"), new MoveAction(3));
        actionTable.put(new Pair<>(2, "a"), new ReduceAction(new Production("S", "A")));
        actionTable.put(new Pair<>(3, "a"), new MoveAction(3));
        actionTable.put(new Pair<>(5, "a"), new ReduceAction(new Production("S", "S", "A")));
        actionTable.put(new Pair<>(6, "a"), new MoveAction(3));
        actionTable.put(new Pair<>(7, "a"), new ReduceAction(new Production("A", "a", "b")));
        actionTable.put(new Pair<>(8, "a"), new ReduceAction(new Production("A", "a", "S", "b")));

        actionTable.put(new Pair<>(2, "b"), new ReduceAction(new Production("S", "A")));
        actionTable.put(new Pair<>(3, "b"), new MoveAction(7));
        actionTable.put(new Pair<>(5, "b"), new ReduceAction(new Production("S", "S", "A")));
        actionTable.put(new Pair<>(6, "b"), new MoveAction(8));
        actionTable.put(new Pair<>(7, "b"), new ReduceAction(new Production("A", "a", "b")));
        actionTable.put(new Pair<>(8, "b"), new ReduceAction(new Production("A", "a", "S", "b")));

        actionTable.put(new Pair<>(1, "c"), new MoveAction(4));
        actionTable.put(new Pair<>(2, "c"), new ReduceAction(new Production("S", "A")));
        actionTable.put(new Pair<>(5, "c"), new ReduceAction(new Production("S", "S", "A")));
        actionTable.put(new Pair<>(7, "c"), new ReduceAction(new Production("A", "a", "b")));
        actionTable.put(new Pair<>(8, "c"), new ReduceAction(new Production("A", "a", "S", "b")));

        actionTable.put(new Pair<>(4, "EOL"), new AcceptAction(new Production("S'", "S", "c")));

        newStateTable.put(new Pair<>(0, "S"), new PutAction(1));
        newStateTable.put(new Pair<>(3, "S"), new PutAction(6));
        newStateTable.put(new Pair<>(0, "A"), new PutAction(2));
        newStateTable.put(new Pair<>(1, "A"), new PutAction(5));
        newStateTable.put(new Pair<>(3, "A"), new PutAction(2));
        newStateTable.put(new Pair<>(6, "A"), new PutAction(5));

        List<String> lexicalUnits = new ArrayList<>(Arrays.asList("a 1 a", "a 1 a", "b 1 b", "b 1 b", "c 1 c"));

        SA sa = new SA(null, actionTable, newStateTable, lexicalUnits);

        System.out.println(sa.getOutputAsString());
    }

    @Test
    public void testExamProblem1() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/00aab_1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/00aab_1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/00aab_1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem2() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/01aab_2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/01aab_2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/01aab_2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem3() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/03gram100_1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/03gram100_1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/03gram100_1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem4() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/05gram100_3/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/05gram100_3/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/05gram100_3/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem5() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/07oporavak2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/07oporavak2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/07oporavak2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem6() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/09redred/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/09redred/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/09redred/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem7() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/11minusLang_2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/11minusLang_2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/11minusLang_2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem8() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/13ppjLang/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/13ppjLang/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/13ppjLang/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem9() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/15jed/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/15jed/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/15jed/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem10() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/17lr1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/17lr1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/17lr1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem11() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/19lr1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/19lr1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/19lr1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem12() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/21lr1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/21lr1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/21lr1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testExamProblem13() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/ispitniPrimjeri/23ppjC_4/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/ispitniPrimjeri/23ppjC_4/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/ispitniPrimjeri/23ppjC_4/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem1() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/00aab_1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/00aab_1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/00aab_1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem2() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/01aab_2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/01aab_2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/01aab_2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem3() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/02aab_3/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/02aab_3/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/02aab_3/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem4() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/03gram100_1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/03gram100_1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/03gram100_1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem5() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/04gram100_2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/04gram100_2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/04gram100_2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem6() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/05gram100_3/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/05gram100_3/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/05gram100_3/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem7() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/06oporavak1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/06oporavak1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/06oporavak1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem8() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/07oporavak2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/07oporavak2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/07oporavak2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem9() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/08pomred/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/08pomred/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/08pomred/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem10() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/09redred/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/09redred/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/09redred/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem11() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/10minusLang_1/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/10minusLang_1/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/10minusLang_1/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem12() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/11minusLang_2/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/11minusLang_2/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/11minusLang_2/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem13() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/12ppjC/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/12ppjC/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/12ppjC/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem14() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/13ppjLang/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/13ppjLang/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/13ppjLang/test.out"), sa.getOutputAsString());
    }

    @Test
    public void testGitProblem15() throws IOException, ClassNotFoundException {
        File input = new File("src/test/resources/lab2/gitTestovi/14simplePpjLang/test.san");
        GSA gsa = new GSA(input);
        gsa.serializeOutput();

        input = new File("src/test/resources/lab2/gitTestovi/14simplePpjLang/test.in");
        SA sa = new SA(input);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab2/gitTestovi/14simplePpjLang/test.out"), sa.getOutputAsString());
    }

    private String readFileAsStringFromResources(String pathName) throws IOException {
        InputStream is = new FileInputStream(pathName);
        byte[] data = is.readAllBytes();
        is.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}