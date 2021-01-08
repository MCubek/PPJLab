package ppj.lab4;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 08/01/2021
 */
class GeneratorKodaTest {

    @Test
    public void testExample1() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/01_ret_broj");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample2() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/02_ret_global");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample3() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/03_veliki_broj");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample4() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/04_neg_broj");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample5() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/05_plus");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample6() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/06_plus_signed");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample7() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/07_minus");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample8() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/08_bitor");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample9() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/09_bitand");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample10() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/10_bitxor");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample11() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/11_fun1");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample12() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/12_fun2");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample13() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/13_fun3");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample13a() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/13_scope1");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample14() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/14_scope2");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample15() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/15_scope3");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample16() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/16_scope4");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample17() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/17_char");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample18() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/18_init_izraz");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample19() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/19_if1");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample20() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/20_if2");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample21() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/21_if3");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample22() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/22_if4");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample23() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/23_niz1");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample24() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/24_niz2");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample25() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/25_niz3");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample26() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/26_niz4");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample27() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/27_rek");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample28() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/28_rek_main");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample29() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/29_for");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample30() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/30_while");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample31() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/31_inc");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample32() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/32_gcd");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample33() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/33_short");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample34() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/34_izraz");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample35() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/35_params");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample36() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/36_params2");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testExample37() throws IOException {
        Path root = Path.of("src/test/resources/lab4/ferDB/37_funcloop");
        GeneratorKoda generatorKoda = new GeneratorKoda(root.resolve("test.in"));

        String result = Files.readString(root.resolve("test.out"));
        assertEquals(result, runFRISC(generatorKoda.getAssembly()));
    }

    @Test
    public void testRunFRISC() throws IOException {
        assertEquals("2", runFRISC("\tMOVE %D 2, R6\n\tHALT\n"));
    }

    public static String runFRISC(String code) throws IOException {
        Path path = Path.of("./frisc.a");
        Files.writeString(path, code);

        final Process p = Runtime.getRuntime().exec("cmd /c node main.js frisc.a");

        final String[] result = new String[1];

        new Thread((new Runnable() {
            @Override
            public void run() {
                BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));

                String line = null;

                try {
                    while ((line = input.readLine()) != null) {
                        result[0] = line;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        })).start();

        while (true) {
            try {
                p.waitFor();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Files.delete(path);

        return result[0];
    }
}