package ppj.lab3.utilities.symbols;

import java.util.Objects;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 27/12/2020
 */
public class SimpleAttribute implements Attribute {
    private final String value;

    public SimpleAttribute(String value) {
        this.value = Objects.requireNonNull(value);
    }

    @Override
    public Object getAttribute() {
        return value;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.SIMPLE_ATTRIBUTE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleAttribute that = (SimpleAttribute) o;

        return value.equals(that.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
