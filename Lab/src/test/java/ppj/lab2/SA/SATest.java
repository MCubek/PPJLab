package ppj.lab2.SA;

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

    private String readFileAsStringFromResources(String pathName) throws IOException {
        InputStream is = new FileInputStream(pathName);
        byte[] data = is.readAllBytes();
        is.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}