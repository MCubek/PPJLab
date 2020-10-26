package ppj.lab;

import ppj.lab.utility.Automaton;
import ppj.lab.utility.Pair;

import java.util.*;

/**
 * Klasa koja iz zadanih regularnih izraza stvara odgovarajuce automate i pridruzuje ih
 * odgovarajucem stanju i akciji
 *
 * @author MatejC FraneB
 */
public class AutomatonGenerator {

    private final LinkedHashMap<RuleRegex, List<String>> initialRules;
    private final Map<Pair<String, Automaton>, List<String>> automatonRules;


    /**
     * Konstuktor generatora automata koji prima listu pripadnih stanja, regularnih izraza i pravila
     *
     * @param initialRules je mapa parova stanja i regularnih izraza te pripadajucih akcija
     * @throws IllegalArgumentException ako je predana nevalidna konfiguracija
     */
    public AutomatonGenerator(LinkedHashMap<RuleRegex, List<String>> initialRules) {
        this.initialRules = Objects.requireNonNull(initialRules);
        this.automatonRules = new LinkedHashMap<>();
        generateAutomatons();
    }

    public Map<Pair<String, Automaton>, List<String>> getAutomatonRules() {
        return automatonRules;
    }

    /**
     * Dodaje novo stanje u automat
     *
     * @return broj novog dodanog stanja
     * @throws IllegalArgumentException ako nije zadan valjan automat
     */
    int newState(Automaton automaton) {
        if (automaton == null) throw new IllegalArgumentException("Automaton cannot be null!");
        automaton.setStateCount(automaton.getStateCount() + 1);
        return automaton.getStateCount() - 1;
    }

    /**
     * Provjerava je li znak u regularnom izrazu operator,
     * tj. sadrzi li paran ili neparan broj znakova \ ispred sebe
     *
     * @return vrijednost true ako je znak operator, false ako nije
     * @throws IllegalArgumentException ako nisu zadani valjani parametri
     */
    boolean isOperator(String regEx, int i) {
        if (regEx == null) throw new IllegalArgumentException("Regex cannot be null!");
        int counter = 0;
        while (i - 1 >= 0 && regEx.charAt(i - 1) == '\\') {
            counter++;
            i--;
        }
        return counter % 2 == 0;
    }


    /**
     * Dodaje funkciju prijelaza iz stanja fromState i znaka transChar u stanje trueState
     *
     * @param automaton automat kojem se dodaje prijelaz
     * @param fromState pocetno stanje
     * @param toState   konacno stanje
     * @param transChar ulazni znak
     */
    void addTransition(Automaton automaton, int fromState, int toState, char transChar) {
        //dohvaca se mapa prijelaznih funkcija
        Map<Pair<Integer, String>, List<Integer>> newTransitions = automaton.getTransitions();
        //Par stanja i ulaznog znaka kojim se provjerava postoje li vec neke funkcije prijelaza
        Pair<Integer, String> checkTransition = new Pair<>(fromState, String.valueOf(transChar));

        //ako ne postoji funkcija prijelaza, stvara se nova lista
        if (! newTransitions.containsKey(checkTransition)) {
            newTransitions.put(checkTransition, new LinkedList<>());
        }
        newTransitions.get(checkTransition).add(toState);
        automaton.setTransitions(newTransitions);
    }

    /**
     * Dodaje automatu epsilon prijelaz za odredena stanja
     *
     * @param automaton automat kojem se dodaju stanja
     * @param fromState stanje iz kojeg se prelazi
     * @param toState   stanje u koje se prelazi
     */
    void addEpsilonTransition(Automaton automaton, int fromState, int toState) {
        //funkcionira na identican nacin kao i funkcija addTransition, epsilon se oznacava praznih Stringom
        Map<Pair<Integer, String>, List<Integer>> newTransitions = automaton.getTransitions();
        Pair<Integer, String> checkTransition = new Pair<>(fromState, "");

        if (! newTransitions.containsKey(checkTransition)) {
            newTransitions.put(checkTransition, new LinkedList<>());
        }
        newTransitions.get(checkTransition).add(toState);
        automaton.setTransitions(newTransitions);
    }

    /**
     * Za dobivenu listu stanja i regularnih izraza stvara novu listu stanja i pripadnih automata
     */
    void generateAutomatons() {
        for (RuleRegex rulReg : initialRules.keySet()) {
            Automaton generatedAutomaton = new Automaton();
            //poziva se funkcija transformRegex za regularni izraz
            Pair<Integer, Integer> result = transformRegex(rulReg.getRegex(), generatedAutomaton);
            //postavljaju se pocetno i prihvatljivo stanje
            generatedAutomaton.setStartState(result.getLeft());
            generatedAutomaton.setAcceptableState(result.getRight());
            Pair<String, Automaton> stateAutomaton = new Pair<>(rulReg.getState(), generatedAutomaton);
            if (! automatonRules.containsKey(stateAutomaton))
                automatonRules.put(stateAutomaton, initialRules.get(rulReg));
        }
    }

    /**
     * Transformira regularni izraz u ENKA automat
     *
     * @param regex     regularni izraz nad kojim se provodi transformacija
     * @param automaton prazan automat koji ce se popuniti pripadnim stanjima
     * @return Par koji predstavlja pocetno i prihvatljivo stanje
     * @throws IllegalArgumentException ako nisu zadani valjani parametri
     */
    Pair<Integer, Integer> transformRegex(String regex, Automaton automaton) {
        //lista podnizova odvojenih operatorom izbora |
        List<String> choices = new ArrayList<>();
        //brojac zagrada
        int parenthCounter = 0;
        //marker pocetka podniza
        int groupMarker = 0;
        //brojac operatora izbora
        int orCounter = 0;
        //za svaki se znak trazi operator izbora, ignoriraju se operatori unutar zagrada i znakovi koji su uistinu operatori
        //Je li znak operator provjerava se funkcijom isOperator
        for (int i = 0; i < regex.length(); i++) {
            if (regex.charAt(i) == '(' && isOperator(regex, i)) {
                parenthCounter++;
            } else if (regex.charAt(i) == ')' && isOperator(regex, i)) {
                parenthCounter--;
            } else if (parenthCounter == 0 && regex.charAt(i) == '|' && isOperator(regex, i)) {
                choices.add(regex.substring(groupMarker, i));
                orCounter++;
                groupMarker = i + 1;
            }
        }
        //ako postoje operatori, u listu izbora dodaje se i posljednji podniz
        if (orCounter != 0) {
            choices.add(regex.substring(groupMarker));
        }

        //stvaranje pocetnog i zavrsnog stanja niza(ili podniza)
        int leftState = newState(automaton);
        int rightState = newState(automaton);
        //za svaki se podniz rekurzivno poziva funkcija transformRegex
        if (orCounter != 0) {
            for (String choice : choices) {
                Pair<Integer, Integer> temporary = transformRegex(choice, automaton);
                //podnizovi se epsilon prijelazima povezuju s ostatkom automata
                addEpsilonTransition(automaton, leftState, temporary.getLeft());
                addEpsilonTransition(automaton, temporary.getRight(), rightState);
            }
        } else {
            //varijabla prefixed predstavlja postojanje znaka \ ispred trazenog znaka
            boolean prefixed = false;
            int lastState = leftState;
            for (int i = 0; i < regex.length(); i++) {
                int a, b;
                //ako postoji znak \, provjeravaju se posebni znakovi tabulatora, novo reda i praznine
                if (prefixed) {
                    prefixed = false;
                    char transChar;
                    if (regex.charAt(i) == 't')
                        transChar = '\t';
                    else if (regex.charAt(i) == 'n')
                        transChar = '\n';
                    else if (regex.charAt(i) == '_')
                        transChar = ' ';
                    else
                        transChar = regex.charAt(i);

                    //dodaju se nova stanja te se za zadani ulazni znak stvara prijelaz automata
                    a = newState(automaton);
                    b = newState(automaton);
                    addTransition(automaton, a, b, transChar);
                } else {
                    //provjera posebnog znaka \
                    if (regex.charAt(i) == '\\') {
                        prefixed = true;
                        continue;
                    }
                    //provjera izraza u zagradama
                    if (regex.charAt(i) != '(') {
                        a = newState(automaton);
                        b = newState(automaton);
                        //provjera posebnog znaka za prazan niz
                        if (regex.charAt(i) == '$')
                            addEpsilonTransition(automaton, a, b);
                        else
                            addTransition(automaton, a, b, regex.charAt(i));
                    } else {
                        //ako je izraz unutar zagrade, trazi se znak zatvaranja zagrade
                        int j = - 1;
                        parenthCounter = 0;
                        for (int k = i; k < regex.length(); k++) {
                            if (regex.charAt(k) == '(' && isOperator(regex, k)) {
                                parenthCounter++;
                            } else if (regex.charAt(k) == ')' && isOperator(regex, k)) {
                                parenthCounter--;
                            }
                            if (parenthCounter == 0) {
                                j = k;
                                break;
                            }
                        }
                        //rekurzivno se poziva funkcija za izraz unutar zagrade
                        Pair<Integer, Integer> temporary = transformRegex(regex.substring(i + 1, j), automaton);
                        a = temporary.getLeft();
                        b = temporary.getRight();
                        i = j;
                    }
                }

                //provjerava se prisutnost Kleenovog operatora i dodaju se odgovarajuci prijelazi
                if (i + 1 < regex.length() && regex.charAt(i + 1) == '*') {
                    int x = a;
                    int y = b;
                    a = newState(automaton);
                    b = newState(automaton);
                    addEpsilonTransition(automaton, a, x);
                    addEpsilonTransition(automaton, y, b);
                    addEpsilonTransition(automaton, a, b);
                    addEpsilonTransition(automaton, y, x);
                    i++;
                }

                //povezivanje s ostatkom automata
                addEpsilonTransition(automaton, lastState, a);
                lastState = b;
            }
            addEpsilonTransition(automaton, lastState, rightState);
        }
        return new Pair<>(leftState, rightState);
    }
}
