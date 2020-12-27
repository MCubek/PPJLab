package ppj.lab3.utilities.scope;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 27/12/2020
 */
public class Scope {
    private final Scope parent;

    private final List<ScopeElement> elements;

    public Scope(Scope parent) {
        this.parent = parent;
        this.elements = new ArrayList<>();
    }

    public void addScopeElement(ScopeElement element) {
        elements.add(element);
    }

    public List<ScopeElement> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Scope scope = (Scope) o;

        if (! parent.equals(scope.parent)) return false;
        return elements.equals(scope.elements);
    }

    @Override
    public int hashCode() {
        int result = parent.hashCode();
        result = 31 * result + elements.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Scope{" +
                "parent=" + parent +
                ", elements=" + elements +
                '}';
    }
}
