package ppj.lab3.utilities.scope;

import ppj.lab3.utilities.symbols.Symbol;

import java.util.HashSet;
import java.util.Set;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 27/12/2020
 */
public class Scope {
    private final Scope parent;

    private final Set<ScopeElement> elements;

    public Scope(Scope parent) {
        this.parent = parent;
        this.elements = new HashSet<>();
    }

    public void addScopeElement(ScopeElement element) {
        elements.add(element);
    }

    public Set<ScopeElement> getElements() {
        return elements;
    }

    public Scope getParent() {
        return parent;
    }

    public ScopeElement isDeclared(String name) {
        for(ScopeElement element : this.elements) {
            if(element.getName().equals(name))
                return element;
        }
        if(parent != null)
         return parent.isDeclared(name);
        return null;
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

    public boolean isDefined(String name) {
        for(ScopeElement element : this.elements) {
            if (element.getName().equals(name) && element.isDefined())
                return true;
        }
        if(this.parent != null)
            return this.parent.isDefined(name);
        else
            return false;
    }

    public void addDefinition(String idnName, String type, boolean lExpression) {
        ScopeElement scopeElement = new ScopeElement(idnName,type,lExpression,true);
        this.elements.add(scopeElement);
    }

    public ScopeElement isDeclaredLocally(String name) {
        for(ScopeElement element : this.elements) {
            if(element.getName().equals(name))
                return element;
        }
        return null;
    }

    //TODO dovrsiti ovu funkciju
    public boolean checkAllDefined() {
        return true;
    }
}
