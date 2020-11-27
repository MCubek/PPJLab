package ppj.lab2.analizator;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 14/11/2020
 */
public class SA {

    public SA(File inputFile) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(inputFile);

        parseInput(scanner);

        scanner.close();
    }

    public SA(Scanner scanner) throws IOException, ClassNotFoundException {
        parseInput(scanner);
    }

    private void parseInput(Scanner scanner) throws IOException, ClassNotFoundException {
        readSerialization();
    }

    private void readSerialization() throws IOException, ClassNotFoundException {
        // TODO: 15.11.2020. Read serialization
    }

    public String getOutputAsString() {
        // TODO: 15.11.2020. Return string
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            SA sa = new SA(scanner);

            System.out.println(sa.getOutputAsString());

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

        scanner.close();
    }
}
