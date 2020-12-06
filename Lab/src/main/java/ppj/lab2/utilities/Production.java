package ppj.lab2.utilities;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class Production implements Serializable {
    private static final long serialVersionUID = - 162859005435295656L;
    private final String leftState;
    private final List<String> rightStates;

    public Production(String leftState, String... rightStates) {
        this.leftState = leftState;
        this.rightStates = Arrays.asList(rightStates.clone());
    }

    public Production(String leftState, List<String> rightStates) {
        this.leftState = leftState;
        this.rightStates = rightStates;
    }

    public String getLeftState() {
        return leftState;
    }

    public List<String> getRightStates() {
        return rightStates;
    }
}
