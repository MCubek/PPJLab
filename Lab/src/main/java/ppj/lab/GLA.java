package ppj.lab;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class GLA {
    private Map<String, String> regex;
    private Set<String> states;
    private Set<String> uniformSimbols;

    public GLA(File inputFile) throws FileNotFoundException {
        if (inputFile == null) throw new IllegalArgumentException("File does not exist");

        Scanner scanner = new Scanner(inputFile);
        regex = new HashMap<>();

        String line;
        //Populirati regex
        while ((line = scanner.nextLine()).matches("\\{..*} .*")) {
            //Pronadi index kraja imena
            int index = line.indexOf('}');
            regex.put(line.substring(1, index - 1), line.substring(index + 1));
        }

        //Populiraj stanja
        if (! scanner.hasNextLine() && ! (line = scanner.nextLine()).matches("%X .*"))
            throw new IllegalArgumentException("File missing states description");
        states = Arrays.stream(line.substring(2).split(" ")).collect(Collectors.toSet());

        //Populiraj leksicke jedinke
        if (! scanner.hasNextLine() && ! (line = scanner.nextLine()).matches("%L .*"))
            throw new IllegalArgumentException("File missing states description");
        uniformSimbols = Arrays.stream(line.substring(2).split(" ")).collect(Collectors.toSet());
    }
}
