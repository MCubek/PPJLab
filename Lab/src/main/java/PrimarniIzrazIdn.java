


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

            if (production.getLeftStateNode().isInFunction()) {
                if(scope.isGlobal(name))
                    GeneratorKoda.codeBuilder.addCommand("MOVE " + GeneratorKoda.getGlobalLabel(name) + ", R0");
                else
                    GeneratorKoda.codeBuilder.addCommand("ADD R6, %D " + scope.getStackOffset(name) + ", R0");
            } else {
                GeneratorKoda.codeBuilder.addCommand("ADD R6, %D " + scope.getStackOffset(name) + ", R0");
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
