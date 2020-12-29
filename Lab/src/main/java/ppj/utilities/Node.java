package ppj.utilities;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class Node<T> {
    private final T value;
    private List<Node<T>> children;
    private Node<T> parent;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public Node<T> getParent() {
        return parent;
    }

    public void setParent(Node<T> parent) {
        this.parent = parent;
    }

    public List<Node<T>> getChildren() {
        return children;
    }

    public void addChild(Node<T> child) {
        if (children == null) children = new ArrayList<>();
        children.add(child);
    }

    public void setChildren(List<Node<T>> children) {
        this.children = children;
    }
}
