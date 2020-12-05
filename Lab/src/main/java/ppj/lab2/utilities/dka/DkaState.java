package ppj.lab2.utilities.dka;

import ppj.lab2.utilities.Production;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 05/12/2020
 */
public class DkaState implements Comparable<DkaState> {
    private final int number;
    private final Collection<Production> productionGroup;

    public DkaState(int number, Collection<Production> productionGroup) {
        this.number = number;
        this.productionGroup = Objects.requireNonNull(productionGroup);
    }

    public DkaState(int number, Production singleProduction) {
        this(number, Collections.singletonList(singleProduction));
    }

    public int getNumber() {
        return number;
    }

    public Collection<Production> getProductionGroup() {
        return productionGroup;
    }

    public boolean productionInState(Production production) {
        return productionGroup.contains(production);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DkaState dkaState = (DkaState) o;

        if (number != dkaState.number) return false;
        return productionGroup.equals(dkaState.productionGroup);
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + productionGroup.hashCode();
        return result;
    }

    @Override
    public int compareTo(DkaState o) {
        return Integer.compare(number, o.number);
    }

    @Override
    public String toString() {
        return String.format("(%d) === ", number) +
                productionGroup.stream()
                        .map(Production::toString)
                        .collect(Collectors.joining(" | ", "( ", " )"));
    }
}
