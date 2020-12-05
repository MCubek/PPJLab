package ppj.lab2.utilities.dka;

import ppj.lab2.utilities.Production;
import ppj.lab2.utilities.Transition;
import ppj.lab2.utilities.enka.EnkaAutomata;

import java.util.*;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 05/12/2020
 */
public class DkaAutomata {
    private TreeMap<DkaState, Transition<DkaState>> transitions;

    public DkaAutomata(EnkaAutomata enkaAutomata) {
        Objects.requireNonNull(enkaAutomata);

        transitions = generateFromEnka(enkaAutomata);
    }

    private static TreeMap<DkaState, Transition<DkaState>> generateFromEnka(EnkaAutomata enkaAutomata) {
        var enkaTransitions = enkaAutomata.getTransitions();

        Deque<Production> statesToVisit = new ArrayDeque<>();
        statesToVisit.push(enkaAutomata.getStartingProduction());
        Set<Production> combinedStates = new HashSet<>();

        while (! statesToVisit.isEmpty()) {
            Production current = statesToVisit.pop();
            combinedStates.add(current);
            enkaTransitions.get(current).stream()
                    .filter(Transition::isEpsilonTransition)
                    .map(Transition::getNextState)
                    .forEach(v -> {
                        if (combinedStates.add(v))
                            statesToVisit.push(v);
                    });
        }

        return null;
    }
}
