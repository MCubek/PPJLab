package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.TerminalSymbol;

public class PrimarniIzrazIdn implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(0);
        String name = idn.getLexicalUnits()[0];
        //1. IDN.ime je deklarirano
        ScopeElement foundElement = scope.isDeclared(name);
        if(foundElement != null) {
            //tip <- IDN.tip
            //l-izraz <- IDN.l-izraz
            production.getLeftState().addAttribute("type", new SimpleAttribute(foundElement.getType()));
            production.getLeftState().addAttribute("lExpression", new SimpleAttribute(String.valueOf(foundElement.isLExpression())));
        } else {
            throw new SemanticException(production.toString());
        }
    }
}
