package ppj.lab3.utilities.scope;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 27/12/2020
 */
public class ScopeElement {
    private final String name;
    private final String type;

    private final boolean isLExpression;
    private final boolean isDefined;

    public ScopeElement(String name, String type, boolean isLExpression, boolean isDefined) {
        this.name = name;
        this.type = type;
        this.isLExpression = isLExpression;
        this.isDefined = isDefined;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScopeElement that = (ScopeElement) o;

        if (isLExpression != that.isLExpression) return false;
        if (isDefined != that.isDefined) return false;
        if (! name.equals(that.name)) return false;
        return type.equals(that.type);
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + type.hashCode();
        result = 31 * result + (isLExpression ? 1 : 0);
        result = 31 * result + (isDefined ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ScopeElement{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isLExpression=" + isLExpression +
                ", isDefined=" + isDefined +
                '}';
    }
}
