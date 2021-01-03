package ppj.utilities;

import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;

import java.util.ArrayList;
import java.util.List;

/**
 * @author MatejCubek
 * @project PPJLab
 * @created 30/11/2020
 */
public class Node<T> {
    private T value;
    private List<Node<T>> children;
    private Node<T> parent;

    public Node(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) { this.value = value; }
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

    public boolean isInLoop() {
        NonTerminalSymbol value = (NonTerminalSymbol) this.getValue();
        if (value.getSymbolName().equals("<naredba_petlje>")) {
            return true;
        } else {
            if(this.parent != null)
                return this.parent.isInLoop();
            else
                return false;
        }
    }

    public String returnFunctionType() {
        NonTerminalSymbol value = (NonTerminalSymbol) this.getValue();
        if (value.getSymbolName().equals("<definicija_funkcije>")) {
            return value.getAttributeMap().get("type").getAttribute().toString();
        } else {
            if(this.parent != null)
                return this.parent.returnFunctionType();
            else
                return null;
        }
    }

    public int canGenerateNizZnakova() {
        if(this.value.equals(new TerminalSymbol("NIZ_ZNAKOVA", 0, new String[0]))) {
            TerminalSymbol symbol = (TerminalSymbol) this.value;
            String lexicalUnit = symbol.getLexicalUnits()[0];
            lexicalUnit = lexicalUnit.substring(1, lexicalUnit.length()-1);
            return lexicalUnit.length();
        }
        if(this.children == null)
            return -1;
        else if(this.children.size() == 1)
            return this.children.get(0).canGenerateNizZnakova();
        else
            return -1;
    }
}
