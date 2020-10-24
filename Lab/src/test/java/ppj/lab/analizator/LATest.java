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
    private void testLAExample1() throws IOException, ClassNotFoundException {
        File file = new File("src/main/resources/lab1_ppjLang[1].txt");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("src/main/resources/lab1_program1[1].c");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        assertEquals(readFileAsStringFromResources("src/main/resources/lab1_izlaz[1].txt"), la.getOutput());
    }

    private String readFileAsStringFromResources(String pathName) throws IOException {
        InputStream is = new FileInputStream(pathName);
        byte[] data = is.readAllBytes();
        is.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}
