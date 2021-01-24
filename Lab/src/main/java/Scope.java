import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author MatejCubek, FraneB
 * @project PPJLab
 * @created 27/12/2020
 */
public class Scope {
    private final Scope parent;
    private final Set<Scope> children;

    private final Set<ScopeElement> elements;

    public Scope() {
        this(null);
    }

    public Scope(Scope parent, Scope... children) {
        this.parent = parent;

        this.elements = new HashSet<>();
        this.children = new HashSet<>();
        if (children.length > 0)
            this.children.addAll(Arrays.asList(children));

        addChildrenToParent();
    }

    private void addChildrenToParent() {
        if (parent == null) return;

        parent.addChildren(this);
    }

    public void addChildren(Scope... children) {
        if (children != null)
            this.children.addAll(Arrays.asList(children));
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
        for (ScopeElement element : this.elements) {
            if (element.getName().equals(name))
                return element;
        }
        if (parent != null)
            return parent.isDeclared(name);
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scope scope = (Scope) o;
        return Objects.equals(children, scope.children) && Objects.equals(elements, scope.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(children, elements);
    }

    @Override
    public String toString() {
        return "Scope";
    }

    public boolean isDefined(String name) {
        for (ScopeElement element : this.elements) {
            if (element.getName().equals(name) && element.isDefined())
                return true;
        }
        if (this.parent != null)
            return this.parent.isDefined(name);
        else
            return false;
    }

    public void addDefinition(String idnName, String type, boolean lExpression) {
        ScopeElement scopeElement = new ScopeElement(idnName, type, lExpression, true);
        this.elements.add(scopeElement);
    }

    public ScopeElement isDeclaredLocally(String name) {
        for (ScopeElement element : this.elements) {
            if (element.getName().equals(name))
                return element;
        }
        return null;
    }

    public boolean checkAllDefined() {
        for (ScopeElement element : elements) {
            if (element.getType().startsWith("funkcija")) {
                if (!element.isDefined())
                    return false;
            }
        }
        boolean childResult;
        if(this.children != null) {
            for(Scope child : this.children) {
                childResult = child.checkAllDefined();
                if (!childResult)
                    return false;
            }
        }
        return true;
    }

    public static boolean charConstValid(String charConst) {
        if (charConst == null || charConst.length() > 4) return false;
        charConst = charConst.substring(1, charConst.length()-1);

        if (charConst.startsWith("\\")) {
            switch (charConst.charAt(1)) {
                case 't':
                case 'n':
                case '0':
                case '\'':
                case '\"':
                case '\\':
                    break;
                default:
                    return false;

            }
        }
        return StandardCharsets.US_ASCII.newEncoder().canEncode(charConst);
    }

    public static boolean charArrayValid(String charArray) {
        Matcher matcher = Pattern.compile(".*\\\\[^tn0'\"\\\\].*").matcher(charArray);

        return ! matcher.find() && StandardCharsets.US_ASCII.newEncoder().canEncode(charArray);
    }

    public int getStackOffset(String name) {
        Scope current = this;
        do {
            for(ScopeElement element : current.getElements()) {
                if(element.getName().equals(name))
                    return element.getOffset();
            }
            current = current.getParent();
        } while(!(current == null) && !(current.getParent() == null));
        return -1;
    }

    public int lastStackOffset() {
        int min = 0;
        Scope current = this;
        do {
            for (ScopeElement element : current.getElements()) {
                if (element.getOffset() < min) {
                    min = element.getOffset();
                }
            }
            current = current.getParent();
        } while (!(current == null) && !(current.getParent() == null));
        return min;
    }

    public boolean isGlobal(String name) {
        Scope current = this;
        if(current.getParent() == null)
            return true;
        for(ScopeElement element : current.getElements()) {
            if(element.getName().equals(name))
                return false;
        }
        return current.getParent().isGlobal(name);
    }
}
