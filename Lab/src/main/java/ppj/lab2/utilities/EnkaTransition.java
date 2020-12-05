package ppj.lab2.utilities;

import java.util.Objects;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 05/12/2020
 */
public class EnkaTransition {
    private final Production state;
    private final Symbol transitionSymbol;
    private final Production nextState;

    public Production getState() {
        return state;
    }

    public Symbol getTransitionSymbol() {
        return transitionSymbol;
    }

    public Production getNextState() {
        return nextState;
    }

    public EnkaTransition(Production state, Symbol transitionSymbol, Production nextState) {
        this.state = Objects.requireNonNull(state);
        this.transitionSymbol = Objects.requireNonNull(transitionSymbol);
        this.nextState = Objects.requireNonNull(nextState);
    }

    public boolean isEpsilonTransition() {
        return transitionSymbol.equals(Symbol.epsilonSymbol);
    }

    @Override
    public String toString() {
        return state.toString() + " (" + transitionSymbol + ")=> " + nextState.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EnkaTransition that = (EnkaTransition) o;

        if (! state.equals(that.state)) return false;
        if (! transitionSymbol.equals(that.transitionSymbol)) return false;
        return nextState.equals(that.nextState);
    }

    @Override
    public int hashCode() {
        int result = state.hashCode();
        result = 31 * result + transitionSymbol.hashCode();
        result = 31 * result + nextState.hashCode();
        return result;
    }
}
