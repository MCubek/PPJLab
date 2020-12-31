package ppj.lab3.utilities.attributes;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 27/12/2020
 */
public class ListAttribute implements Attribute {
    private final String[] values;

    public ListAttribute(String... values) {
        this.values = Objects.requireNonNull(values);
    }

    public ListAttribute(List<String> values) { this.values = values.toArray(new String[0]);}

    @Override
    public Object getAttribute() {
        return values;
    }

    @Override
    public AttributeType getType() {
        return AttributeType.LIST_ATTRIBUTE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListAttribute that = (ListAttribute) o;

        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        return Arrays.equals(values, that.values);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(values);
    }
}
