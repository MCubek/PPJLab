package ppj.lab2.utilities.actions;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class RejectAction implements Action {
    private static final long serialVersionUID = 6834640807082525541L;
    private String message;

    public String getMessage() {
        return message;
    }

    public RejectAction(String message) {
        this.message = message;
    }

    public RejectAction() {
    }

    @Override
    public String getName() {
        return "Reject";
    }
}
