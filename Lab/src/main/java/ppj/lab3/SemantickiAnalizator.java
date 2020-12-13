package ppj.lab3;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Lab03 Semanticki analizator za jezik ppjC
 *
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 13/12/2020
 */
public class SemantickiAnalizator {
    // TODO: 13.12.2020. strukture

    /**
     * Konstruktor koji parsira uzaz i stvara odgovarajuce strukture podataka
     *
     * @param bufferedReader input stream
     */
    public SemantickiAnalizator(BufferedReader bufferedReader) {
        // TODO: 13.12.2020. Parsiranje
    }

    /**
     * Konstruktor koji cita iz datoteke
     *
     * @param filePath path datoteke
     * @throws IOException ako se dogodi IOexception
     */
    public SemantickiAnalizator(Path filePath) throws IOException {
        this(Files.newBufferedReader(filePath));
    }

    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        SemantickiAnalizator sa = new SemantickiAnalizator(reader);

        try {
            reader.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        // TODO: 13.12.2020. Što dalje nakon parsiranja?
    }
}
