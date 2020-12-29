package ppj.lab3.utilities;

import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 23/12/2020
 */
class TreeParserTest {
    private static String[] lines;
    private static String file;

    @BeforeAll
    static void beforeAll() throws IOException {
        lines = Files.readAllLines(Path.of("src/test/resources/lab3/ispitniPrimjeri/09_fun_povtip/test.in")).toArray(String[]::new);
        file = String.join("\r\n", lines) + "\r\n";


    }

/*    @Test
    void testTreeParser() throws IOException {
        var root = TreeParser.generateNodeTree(lines);

        String output = NodeTreePrinter.walkNodeTree(root);

        assertEquals(file, output);
    }*/

}