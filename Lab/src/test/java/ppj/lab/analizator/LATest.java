package ppj.lab.analizator;

import org.junit.jupiter.api.Test;
import ppj.lab.GLA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LATest {

    @Test
    public void testLAExample0() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/lab1_ppjLang[1].txt");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/lab1_program1[1].c");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        //TODO Fix test0
        //assertEquals(readFileAsStringFromResources("src/main/resources/lab1_izlaz[1].txt"), la.getOutput());
    }

    @Test
    public void testExamProblem1() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/01_nadji_x/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/01_nadji_x/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/01_nadji_x/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem3() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/03_nadji_x_oporavak/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/03_nadji_x_oporavak/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/03_nadji_x_oporavak/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem5() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/05_regex_tezi/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/05_regex_tezi/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/05_regex_tezi/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem7() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/07_regex_regdefs/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/07_regex_regdefs/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/07_regex_regdefs/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem9() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/09_poredak/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/09_poredak/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/09_poredak/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem11() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/11_state_hopper/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/11_state_hopper/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/11_state_hopper/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem13() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/13_vrati_se/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/13_vrati_se/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/13_vrati_se/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem15() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/15_minusLang_laksi/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/15_minusLang_laksi/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/15_minusLang_laksi/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem17() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/17_simplePpjLang_laksi/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/17_simplePpjLang_laksi/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/17_simplePpjLang_laksi/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem19() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/19_ppjLang_laksi/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/19_ppjLang_laksi/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/19_ppjLang_laksi/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem21() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/21_kom4/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/21_kom4/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/21_kom4/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem23() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/23_op4/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/23_op4/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/23_op4/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem25() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/25_prog3/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/25_prog3/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/25_prog3/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem27() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/27_prog5/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/27_prog5/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/27_prog5/test.out"), la.getOutput());
    }

    @Test
    public void testExamProblem29() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/ispitniTestovi/29_veliki3/test.lan");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/ispitniTestovi/29_veliki3/test.in");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/ispitniTestovi/29_veliki3/test.out"), la.getOutput());
    }


    private String readFileAsStringFromResources(String pathName) throws IOException {
        InputStream is = new FileInputStream(pathName);
        byte[] data = is.readAllBytes();
        is.close();
        return new String(data, StandardCharsets.UTF_8);
    }

    @Test
    public void testLAExample2() throws IOException, ClassNotFoundException {
        LA lexer;
        try {
            lexer = new LA(new File("src/main/resources/lab1_program1[1].c"));
            int i = 0;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
