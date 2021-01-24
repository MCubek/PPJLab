import java.util.Objects;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 27/12/2020
 */
public class ScopeElement {
    private final String name;
    private final String type;

    private final boolean isLExpression;
    private final boolean isDefined;

    private int offset;

    public ScopeElement(String name, String type, boolean isLExpression, boolean isDefined) {
        this.name = name;
        this.type = type;
        this.isLExpression = isLExpression;
        this.isDefined = isDefined;
        this.offset = 0;
    }

    public ScopeElement(String name, String type, boolean isLExpression, boolean isDefined, int offset) {
        this.name = name;
        this.type = type;
        this.isLExpression = isLExpression;
        this.isDefined = isDefined;
        this.offset = offset;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public boolean isLExpression() {
        return isLExpression;
    }

    public boolean isDefined() {
        return isDefined;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScopeElement element = (ScopeElement) o;
        return Objects.equals(name, element.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "ScopeElement";
    }
}
