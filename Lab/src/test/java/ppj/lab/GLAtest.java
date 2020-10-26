package ppj.lab;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

public class GLAtest {
    @Test
    public void testExample1DoesNotCrash() throws IOException {
        File file = new File("src/test/resources/lab1_ppjLang[1].txt");
        //System.out.println(file.getAbsolutePath());
        GLA gla = new GLA(file);

        gla.serializeOutput();
    }
}
