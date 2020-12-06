package ppj.lab2.utilities.actions;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class PutAction implements Action {
    private static final long serialVersionUID = - 1265586746642464326L;
    private final int state;

    public PutAction(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    @Override
    public String getName() {
        return "Put";
    }
}
