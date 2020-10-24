package ppj.lab.analizator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import ppj.lab.GLA;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class LATest {

    @Test
    private void testLAExample1() throws IOException, ClassNotFoundException {
        String result;

        File file = new File("Lab/src/main/resources/lab1_ppjLang[1].txt");
        System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);
        gla.serializeOutput();


        file = new File("Lab/src/main/resources/lab1_program1[1].c");
        System.out.println(file.getAbsolutePath());
        LA la = new LA(file);

        result = la.getOutput();

        assertEquals(result, la.getOutput());
    }

    private String readFileAsStringFromResources(String pathName) throws IOException {
        InputStream is = new FileInputStream(pathName);
        byte[] data = is.readAllBytes();
        is.close();
        return new String(data, StandardCharsets.UTF_8);
    }
}
