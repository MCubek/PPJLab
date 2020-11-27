package ppj.lab2.analizator;

import org.junit.jupiter.api.Test;
import ppj.lab2.GSA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 15/11/2020
 */
class SATest {
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