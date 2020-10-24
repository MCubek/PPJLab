package ppj.lab;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;

public class GLAtest {
    @Test
    public void testExample1() {
        try {
            File file = new File("Lab/src/main/resources/lab1_ppjLang[1].txt");
            System.out.println(file.getAbsolutePath());
            GLA gla = new GLA(file);
        } catch (FileNotFoundException exception) {
            System.err.println("File not FOUND!");
        }
    }
}
