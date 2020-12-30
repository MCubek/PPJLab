package ppj.lab3;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 24/12/2020
 */
public class SemanticException extends RuntimeException {
    private static final long serialVersionUID = - 5398476488074110611L;

    public SemanticException() {
    }

    public SemanticException(String message) {
        super(message);
    }
}
