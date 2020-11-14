package ppj.utilities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RegEx implements Serializable {
    private static final long serialVersionUID = - 7659733003374818780L;
    private final String name;
    private String expression;

    public RegEx(String name, String expression) {
        this.name = name;
        this.expression = expression;
    }

    public String getName() {
        return name;
    }

    public String getExpression() {
        return expression;
    }

    /**
     * Zamijeni referencu sa definicijom drugog regularnog izraza u kojoj je ta definicija napisana
     *
     * @param other drugi regularni izraz cija je referenca u pitanju
     * @throws NullPointerException ako je predan regex null
     */
    public boolean addExpressionFromAnother(RegEx other) {
        if (other == null) throw new NullPointerException("RegEx can't be null!");
        if (! expression.contains(other.name)) return false;

        expression = expression.replace("{" + other.name + "}", "(" + other.expression + ")");
        return true;
    }

    /**
     * Trazi i vrati listu imena regularnih izraza koji su mu u izrazu kao reference
     *
     * @return lista imena regularnih izraza referenci
     */
    public List<String> getReferences() {
        int indexFront, indexBack;
        String subString = expression;
        List<String> referenceList = new ArrayList<>();

        while (subString.contains("{")) {
            indexFront = subString.indexOf("{");
            indexBack = subString.indexOf("}");
            referenceList.add(subString.substring(indexFront + 1, indexBack));
            subString = subString.substring(indexBack + 1);
        }
        return referenceList;
    }
}
