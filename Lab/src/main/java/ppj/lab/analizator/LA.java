package ppj.lab.analizator;

import ppj.lab.Automaton;
import ppj.lab.Pair;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LA {
    private List<String> states;
    private Set<String> uniformSymbols;
    private Map<Pair<String, Automaton>, List<String>> automatonRules;
    private List<String> resultStringsList;

    public LA(Scanner scanner) throws IOException, ClassNotFoundException {
        readSerialization();

        parseProgram(getInputLinesList(scanner));

        scanner.close();
    }

    public LA(File file) throws IOException, ClassNotFoundException {
        this(new Scanner(file));
    }

    //parsira niz i razdvaja leksicke jedinke
    private void parseProgram(List<String> inputLinesList) {

        //brojac redova
        int counter = 1;
        //pocetno stanje
        String state = this.getStates().get(0);
        //symbol predstavlja niz na koji se dodava znak po znak prilikom parsiranja, tempSymbol je niz za kojeg je pronadena jedinka
        String symbol = "";
        String tempSymbol = "";
        //par stanja i automata za pretragu akcija
        Pair<String, Automaton> symbolPair = null;
        //provjera je li pronađen automat za niz. Ako je, poduzimaju se zapisane akcije, a ako nije, radimo kontrolu pogreške
        boolean foundAutomaton;

        //u jednom loopu se provjeravaju sve procitanje linije
        for (int i = 0; i < inputLinesList.size(); i++) {
            String inputLine = inputLinesList.get(i);
            foundAutomaton = false;
            //za svaku liniju se zasebno citaju znakovi
            for (int j = 0; j < inputLine.length(); j++) {
                symbol = symbol + inputLine.charAt(j);
                //niz znakova provjerava se dok se ne pronađe prvi par stanja i automata koji ga prihvaca
                for (Pair<String, Automaton> auto : this.getAutomatonRules().keySet()) {
                    if (auto.getLeft().equals(state) && auto.getRight().computeInput(symbol)) {
                        //spremaju se vrijednosti potrebne za poduzimanje akcija
                        tempSymbol = symbol;
                        symbolPair = auto;
                        foundAutomaton = true;
                        break;
                    }
                }
            }
            if (foundAutomaton) {
                //reverted je true ako je određena akcija vec smanjila i kako bi se prepravljeni niz ponovo zaobisao, false ako nije
                boolean reverted = false;
                //ako je niz promijenjen, a naknadno se pozove akcija VRATI_SE, akcija se provodi nad starim nizom koji se ne sprema(malo glupo ako se mene pita)
                boolean returnHelp = false;
                String returnLine = "";
                //gledaju se sva pravila koje treba obaviti
                for (String rule : this.automatonRules.get(symbolPair)) {
                    //ako je pravilo ime leksicke jedinke, onda se ona pridodjeljuje nizu i zapisuje u rezultat
                    if (uniformSymbols.contains(rule)) {
                        resultStringsList.add(rule + " " + counter + " " + tempSymbol);
                        symbol = "";
                        //spremanje starog niza, opisano je u nastavku
                        returnHelp = true;
                        returnLine = inputLine;
                        //iz niza se izbacuje obradena jedinka i prepravljeni niz se vraca u listu
                        inputLine = inputLine.replaceFirst(Pattern.quote(tempSymbol), Matcher.quoteReplacement(""));
                        //ovaj dio koda provjerava na koje se mjesto sprema niz i je li ijedna druga akcija vec prepravila indeks i kako bi se niz ponovo zaobisao
                        if (! reverted) {
                            inputLinesList.set(i, inputLine);
                            reverted = true;
                            i--;
                        } else {
                            inputLinesList.set(i + 1, inputLine);
                        }
                        //za oznaku novog reda samo se povecava brojac
                    } else if (rule.equals("NOVI_REDAK")) {
                        counter++;
                        //za akciju UDJI_U_STANJE trenutno stanje se zamjenjuje s navedenim
                    } else if (rule.contains("UDJI_U_STANJE")) {
                        state = rule.replace("UDJI_U_STANJE ", "");
                        //za akciju VRATI_SE niz se cita s odredenog indeksa pa na dalje, treba paziti da se gleda na niz koji nije promijenjen nekim drugim akcijama
                    } else if (rule.contains("VRATI_SE")) {
                        int subIndex = Integer.parseInt(rule.replace("VRATI_SE ", ""));
                        //ako je niz vec promjenjen, uzima se spremljeni stari niz
                        if (returnHelp) {
                            inputLine = returnLine.substring(subIndex);
                            returnHelp = false;
                        } else {
                            inputLine = inputLine.substring(subIndex);
                        }
                        if (! reverted) {
                            inputLinesList.set(i, inputLine);
                            reverted = true;
                            i--;
                        } else {
                            inputLinesList.set(i + 1, inputLine);
                        }
                        //za znak minus odbacuje se trenutni znak, ali stari niz cuvamo radi drugih akcija
                    } else if (rule.equals("-")) {
                        returnHelp = true;
                        returnLine = inputLine;
                        inputLine = inputLine.substring(tempSymbol.length());
                        symbol = "";
                        if (! reverted) {
                            inputLinesList.set(i, inputLine);
                            reverted = true;
                            i--;
                        } else {
                            inputLinesList.set(i + 1, inputLine);
                        }
                    }
                }
                //ako nije pronaden automat, ponavlja se postupak za niz kojem smo izbacili pocetni znak
            } else {
                if (! inputLine.isEmpty()) {
                    symbol = "";
                    //izbaceni znak se ispisuje na stderr
                    char errChar = inputLine.charAt(0);
                    //System.err.println(errChar);
                    inputLine = inputLine.substring(1);
                    inputLinesList.set(i, inputLine);
                    i--;
                }
            }
        }
    }


    private List<String> getInputLinesList(Scanner scanner) {
        if (scanner == null) throw new NullPointerException();

        List<String> characterList = new ArrayList<>();

        while (scanner.hasNextLine()) {
            String stringLine = scanner.nextLine();
            stringLine = stringLine + '\n';
            characterList.add(stringLine);
        }
        return characterList;
    }

    @SuppressWarnings("unchecked")
    private void readSerialization() throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream("src/main/java/ppj/lab/analizator/states.ser");
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.states = (List<String>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab/analizator/symbols.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.uniformSymbols = (Set<String>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        fileInputStream = new FileInputStream("src/main/java/ppj/lab/analizator/rules.ser");
        bufferedInputStream = new BufferedInputStream(fileInputStream);
        objectInputStream = new ObjectInputStream(bufferedInputStream);

        this.automatonRules = (Map<Pair<String, Automaton>, List<String>>) objectInputStream.readObject();
        fileInputStream.close();
        bufferedInputStream.close();
        objectInputStream.close();

        this.resultStringsList = new LinkedList<>();

    }

    public List<String> getStates() {
        return states;
    }

    public Map<Pair<String, Automaton>, List<String>> getAutomatonRules() {
        return automatonRules;
    }

    /**
     * Metoda vraća očekivani izlaz:
     * Tablica znakova i niz uniformih znakova
     *
     * @return izlaz kao string
     */
    public String getOutputAsString() {
        StringBuilder stringBuilder = new StringBuilder();

        resultStringsList.forEach((v) -> stringBuilder.append(v).append("\r\n"));

        return stringBuilder.toString();
    }

    public static void main(String[] args) {
        try {
            LA lexer = new LA(new Scanner(System.in));

            System.out.println(lexer.getOutputAsString());

        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
        }

    }
}
