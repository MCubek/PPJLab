package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;
import ppj.lab3.utilities.symbols.Symbol;

public class PrimarniIzrazIdn implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        Symbol idn = production.getRightStates().get(0);

        //1. IDN.ime je deklarirano
        ScopeElement foundElement = scope.isDeclared(idn);
        if(foundElement != null) {
            //tip ← IDN.tip
            //l-izraz ← IDN.l-izraz
            production.getLeftState().addAttribute("type", new SimpleAttribute(foundElement.getType()));
            production.getLeftState().addAttribute("lExpression", new SimpleAttribute(String.valueOf(foundElement.isLExpression())));
        } else {
            System.out.println(production);
            System.exit(1);
        }
    }
}
