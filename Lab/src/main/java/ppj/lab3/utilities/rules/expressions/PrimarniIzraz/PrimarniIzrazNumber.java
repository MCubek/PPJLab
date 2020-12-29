package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.Symbol;


public class PrimarniIzrazNumber implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        Symbol number = production.getRightStates().get(0);
        try {
            //1. vrijednost je u rasponu tipa int
            Integer.parseInt(number.getSymbolName());
            //tip ← int
            //l-izraz ← 0
            production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
            production.getLeftState().addAttribute("lExpression",new SimpleAttribute("false"));
        } catch (NumberFormatException exception) {
            System.out.println(production);
            System.exit(1);
        }
    }
}
