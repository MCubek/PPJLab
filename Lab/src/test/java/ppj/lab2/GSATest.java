package ppj.lab2;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 14/11/2020
 */
class GSATest {
    @Test
    public void testParsing() {
        try {
            GSA gsa = new GSA(new File("src/test/resources/lab2/gramatikaPrimjer[1].txt"));
            gsa.serializeOutput();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
