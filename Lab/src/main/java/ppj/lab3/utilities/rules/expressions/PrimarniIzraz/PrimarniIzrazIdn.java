package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.Symbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;
import ppj.lab4.GeneratorKoda;
import ppj.utilities.Node;

import java.util.LinkedList;

public class PrimarniIzrazIdn implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(0);
        String name = idn.getLexicalUnits()[0];
        //1. IDN.ime je deklarirano
        ScopeElement foundElement = scope.isDeclared(name);
        if (foundElement != null) {
            //tip <- IDN.tip
            //l-izraz <- IDN.l-izraz
            production.getLeftState().addAttribute("type", new SimpleAttribute(foundElement.getType()));
            production.getLeftState().addAttribute("lExpression", new SimpleAttribute(String.valueOf(foundElement.isLExpression())));
        } else {
            throw new SemanticException(production.toString());
        }

        if (! foundElement.getType().contains("funkcija")) {

            if (scope.getElements().size() == 0) {
                GeneratorKoda.codeBuilder.addCommand("MOVE " + GeneratorKoda.getGlobalLabel(name) + ", R0");
            } else {
                //TODO Što se dogada?
                GeneratorKoda.codeBuilder.addCommand("ADD R6, %D " + 4 * getNumberOfElements(production.getLeftStateNode()) + ", R0");
            }
            GeneratorKoda.codeBuilder.addCommand("PUSH R0");
        }

    }

    private static int getNumberOfElements(Node<Symbol> startingNode) {
        Node<Symbol> curr = startingNode;
        while (curr != null && ! curr.getValue().getSymbolName().equals("<jednakosni_izraz>")) {
            curr = curr.getParent();
        }

        int count = 0;

        LinkedList<Node<Symbol>> stack = new LinkedList<>();
        stack.add(curr);

        while (! stack.isEmpty()) {
            var el = stack.pop();

            if (el.getValue().getSymbolName().equals("<primarni_izraz>"))
                count++;

            if (el.getChildren() != null)
                stack.addAll(el.getChildren());
        }
        return count;
    }
}
