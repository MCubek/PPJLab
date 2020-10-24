package ppj.lab;

import java.io.Serializable;
import java.util.Objects;

/**
 * Klasa koja predstavlja par dva atributa proizvoljnih klasa
 *
 * @author MatejC FraneB
 */
public class Pair<T,V> implements Serializable {

    T left;
    V right;

    public Pair(T left, V right) {
        this.left = left;
        this.right = right;
    }

    public T getLeft() {
        return left;
    }

    public V getRight() {
        return right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(left, pair.left) &&
                Objects.equals(right, pair.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}