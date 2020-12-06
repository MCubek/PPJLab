package ppj.lab2.utilities.actions;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class MoveAction implements Action {
    private static final long serialVersionUID = 7824351695874440762L;
    private final int state;

    public MoveAction(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    @Override
    public String getName() {
        return "Move";
    }
}
