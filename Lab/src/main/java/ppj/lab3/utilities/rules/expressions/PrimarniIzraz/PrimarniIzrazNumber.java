package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.TerminalSymbol;


public class PrimarniIzrazNumber implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        TerminalSymbol number = (TerminalSymbol) production.getRightStates().get(0);
        try {
            //1. vrijednost je u rasponu tipa int
            Integer.parseInt(number.getLexicalUnits()[0]);
            //tip <- int
            //l-izraz <- 0
            production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
            production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
        } catch (NumberFormatException exception) {
            throw new SemanticException(production.toString());
        }
    }
}
