package ppj.lab1.analizator;

import org.junit.jupiter.api.Test;
import ppj.lab1.GLA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LATest {

    @Test
    public void testLAExample0() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/lab1_ppjLang[1].txt");
        //System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/lab1_program1[1].c");
        //System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        String result = la.getOutputAsString();

        assertTrue(true);
    }

    @Test
    public void testOrginalProblem1() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/orignalPrimjeri/minusLang.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/orignalPrimjeri/minusLang.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/orignalPrimjeri/minusLang.out"), la.getOutputAsString());
    }

    @Test
    public void testOrginalProblem2() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/orignalPrimjeri/nadji_a1.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/orignalPrimjeri/nadji_a1.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/orignalPrimjeri/nadji_a1.out"), la.getOutputAsString());
    }

    @Test
    public void testOrginalProblem3() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/orignalPrimjeri/nadji_a2.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/orignalPrimjeri/nadji_a2.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/orignalPrimjeri/nadji_a2.out"), la.getOutputAsString());
    }

    @Test
    public void testOrginalProblem4() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/orignalPrimjeri/simplePpjLang.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/orignalPrimjeri/simplePpjLang.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/orignalPrimjeri/simplePpjLang.out"), la.getOutputAsString());
    }

    @Test
    public void testOrginalProblem5() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/orignalPrimjeri/svaki_drugi_a1.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/orignalPrimjeri/svaki_drugi_a1.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/orignalPrimjeri/svaki_drugi_a1.out"), la.getOutputAsString());
    }

    @Test
    public void testOrginalProblem6() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/orignalPrimjeri/svaki_drugi_a2.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/orignalPrimjeri/svaki_drugi_a2.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/orignalPrimjeri/svaki_drugi_a2.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem1() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/01_nadji_x/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/01_nadji_x/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/01_nadji_x/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem3() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/03_nadji_x_oporavak/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/03_nadji_x_oporavak/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/03_nadji_x_oporavak/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem5() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/05_regex_tezi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/05_regex_tezi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/05_regex_tezi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem7() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/07_regex_regdefs/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/07_regex_regdefs/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/07_regex_regdefs/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem9() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/09_poredak/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/09_poredak/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/09_poredak/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem11() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/11_state_hopper/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/11_state_hopper/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/11_state_hopper/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem13() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/13_vrati_se/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/13_vrati_se/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/13_vrati_se/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem15() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/15_minusLang_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/15_minusLang_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/15_minusLang_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem17() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/17_simplePpjLang_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/17_simplePpjLang_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/17_simplePpjLang_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem19() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/19_ppjLang_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/19_ppjLang_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/19_ppjLang_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem21() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/21_kom4/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/21_kom4/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/21_kom4/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem23() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/23_op4/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/23_op4/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/23_op4/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem25() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/25_prog3/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/25_prog3/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/25_prog3/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem27() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/27_prog5/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/27_prog5/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/27_prog5/test.out"), la.getOutputAsString());
    }

    @Test
    public void testExamProblem29() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/ispitniTestovi/29_veliki3/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/ispitniTestovi/29_veliki3/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/ispitniTestovi/29_veliki3/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem1() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/duljina/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/duljina/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/duljina/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem2() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/greske_u_stanju/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/greske_u_stanju/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/greske_u_stanju/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem3() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/minusLang_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/minusLang_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/minusLang_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem4() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/minusLang_tezi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/minusLang_tezi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/minusLang_tezi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem5() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/nadji_x/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/nadji_x/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/nadji_x/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem6() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/nadji_x_oporavak/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/nadji_x_oporavak/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/nadji_x_oporavak/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem7() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/nadji_x_retci/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/nadji_x_retci/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/nadji_x_retci/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem8() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/poredak/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/poredak/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/poredak/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem9() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/ppjLang_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/ppjLang_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/ppjLang_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem10() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/ppjLang_tezi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/ppjLang_tezi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/ppjLang_tezi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem11() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/regex_escapes/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/regex_escapes/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/regex_escapes/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem12() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/regex_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/regex_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/regex_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem13() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/regex_regdefs/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/regex_regdefs/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/regex_regdefs/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem14() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/regex_tezi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/regex_tezi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/regex_tezi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem15() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/simplePpjLang_laksi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/simplePpjLang_laksi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/simplePpjLang_laksi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem16() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/simplePpjLang_tezi/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/simplePpjLang_tezi/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/simplePpjLang_tezi/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem17() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/state_hopper/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/state_hopper/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/state_hopper/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem18() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/svaki_treci_x/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/svaki_treci_x/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/svaki_treci_x/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem19() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/vrati_se/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/vrati_se/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/vrati_se/test.out"), la.getOutputAsString());
    }

    @Test
    public void testGitProblem20() throws IOException, ClassNotFoundException {
        File file = new File("src/test/resources/lab1/gitTestovi/vrati_se_prioritet/test.lan");
        GLA gla = new GLA(file);
        gla.serializeOutput();

        file = new File("src/test/resources/lab1/gitTestovi/vrati_se_prioritet/test.in");
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/test/resources/lab1/gitTestovi/vrati_se_prioritet/test.out"), la.getOutputAsString());
    }

    private String readFileAsStringFromResources(String pathName) throws IOException {
        InputStream is = new FileInputStream(pathName);
        byte[] data = is.readAllBytes();
        is.close();
        return new String(data, StandardCharsets.UTF_8);
    }

}
