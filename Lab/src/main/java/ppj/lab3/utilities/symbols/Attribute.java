package ppj.lab3.utilities.symbols;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 27/12/2020
 */
public interface Attribute {
    Object getAttribute();

    AttributeType getType();

    public enum AttributeType {
        SIMPLE_ATTRIBUTE,
        LIST_ATTRIBUTE
    }
}
