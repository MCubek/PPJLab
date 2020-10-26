package ppj.lab.utility;

import java.io.Serializable;
import java.util.*;

/**
 * Klasa koja predstavlja ENKA automat
 *
 * @author MatejC FraneB
 */
public class Automaton implements Serializable {
    private static final long serialVersionUID = 7442898039424384643L;
    private int stateCount;
    private int startState;
    private int acceptableState;
    private Map<Pair<Integer, String>, List<Integer>> transitions;


    public Automaton() {
        this.stateCount = 0;
        this.startState = 0;
        this.acceptableState = 0;
        this.transitions = new HashMap<>();
    }

    public Automaton(int stateCount, int startState, int acceptableState) {
        this.stateCount = stateCount;
        this.startState = startState;
        this.acceptableState = acceptableState;
    }

    /**
     * Konstuktor automata pomocu drugog automata
     *
     * @param automaton preko kojeg se stvara novi automat
     * @throws IllegalArgumentException ako je predana nevalidna konfiguracija
     */
    public Automaton(Automaton automaton) {
        if (automaton == null) throw new IllegalArgumentException("Automaton is null");
        this.stateCount = automaton.stateCount;
        this.startState = automaton.startState;
        this.acceptableState = automaton.acceptableState;
        this.transitions = Map.copyOf(automaton.transitions);
    }

    public int getStateCount() {
        return stateCount;
    }

    public int getStartState() {
        return startState;
    }

    public int getAcceptableState() {
        return acceptableState;
    }

    public Map<Pair<Integer, String>, List<Integer>> getTransitions() {
        return transitions;
    }

    public void setStartState(int startState) {
        this.startState = startState;
    }

    public void setAcceptableState(int acceptableState) {
        this.acceptableState = acceptableState;
    }

    public void setStateCount(int stateCount) {
        this.stateCount = stateCount;
    }

    public void setTransitions(Map<Pair<Integer, String>, List<Integer>> transitions) {
        this.transitions = transitions;
    }

    /**
     * Provjerava prihvatljivost izraza u zadanom automatu
     *
     * @return vrijednost true ako je izraz prihvatljiv, false ako nije
     * @throws IllegalArgumentException ako nije zadan valjan izraz
     */
    public boolean computeInput(String input) {
        if (input == null) throw new IllegalArgumentException("Input is null");
        //lista trenutnih stanja za koje se gledaju prijelazi
        Set<Integer> currentStates = new HashSet<>();
        //lista stanja nakon provedbe funkcija prijelaza trenutnih stanja
        Set<Integer> nextStates = new HashSet<>();
        currentStates.add(this.startState);
        for (int i = 0; i <= input.toCharArray().length; i++) {
            //svako stanje stavlja se u red stateQueue, vec predena stanja nalaze se u setu removed
            Set<Integer> removed = new HashSet<>();
            Queue<Integer> stateQueue = new LinkedList<>(currentStates);
            //prolazak svakog stanja i gledanje potencijalnih epsilon funkcija
            while (! stateQueue.isEmpty()) {
                Integer checkState = stateQueue.remove();
                removed.add(checkState);
                Pair<Integer, String> checkEpsilon = new Pair<>(checkState, "");
                //u trenutna stanja se dodaju sve epsilon produkcije stanja koje se trenutno obraduje
                if (transitions.containsKey(checkEpsilon)) {
                    currentStates.addAll(transitions.get(checkEpsilon));
                    for (Integer t : transitions.get(checkEpsilon)) {
                        if (! removed.contains(t)) {
                            stateQueue.add(t);
                        }
                    }
                }
            }

            //za sve znakove osim zadnje provjeruju se funkcije prijelaza trenutnih stanja i zadanog znaka
            if (i != input.toCharArray().length) {
                List<Integer> temporary;
                String string = String.valueOf(input.toCharArray()[i]);
                for (Integer state : currentStates) {
                    Pair<Integer, String> checkTransition = new Pair<>(state, string);
                    if (transitions.containsKey(checkTransition)) {
                        temporary = transitions.get(checkTransition);
                        nextStates.addAll(temporary);
                    }
                }
                //cisti se lista trenutnih stanja; lista sljedecih stanja postaje lista trenutnih
                currentStates.clear();
                currentStates.addAll(nextStates);
                nextStates.clear();
            }
        }
        //ako u zadnjoj iteraciji lista trenutnih stanja sadrzava prihvatljivo stanje, vraca se true, inace false
        return currentStates.contains(acceptableState);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Automaton automaton = (Automaton) o;
        return stateCount == automaton.stateCount &&
                startState == automaton.startState &&
                acceptableState == automaton.acceptableState &&
                transitions.equals(automaton.transitions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stateCount, startState, acceptableState, transitions);
    }
}
