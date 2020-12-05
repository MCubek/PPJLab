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
        int counter = 0;

        Deque<Production> stateToVisit = new ArrayDeque<>();
        stateToVisit.push(enkaAutomata.getStartingProduction());
        Deque<Production> epsilonStateToVisit = new ArrayDeque<>();
        epsilonStateToVisit.push(enkaAutomata.getStartingProduction());

        do {
            Production headProduction = stateToVisit.pop();

            Set<Production> combinedStates = new HashSet<>();

            for (Transition<Production> currentTransition : enkaTransitions.get(headProduction)) {
                //Sljedece stanje
                if (! currentTransition.isEpsilonTransition()) stateToVisit.add(currentTransition.getNextState());

                //Epsilon ork

            }


        } while (! stateToVisit.isEmpty());


        Set<Production> combinedStates = new HashSet<>();

        while (! epsilonStateToVisit.isEmpty()) {
            Production current = epsilonStateToVisit.pop();
            combinedStates.add(current);
            enkaTransitions.get(current).stream()
                    .filter(Transition::isEpsilonTransition)
                    .map(Transition::getNextState)
                    .forEach(v -> {
                        if (combinedStates.add(v))
                            epsilonStateToVisit.push(v);
                    });
        }
        DkaState state = new DkaState(counter++, combinedStates);


        return null;
    }
}
