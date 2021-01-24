package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.TerminalSymbol;

public class PrimarniIzrazListSign implements Action {
    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. konstantni niz znakova je ispravan po 4.3.2
        TerminalSymbol listSign = (TerminalSymbol) production.getRightStates().get(0);

        if (! Scope.charArrayValid(listSign.getLexicalUnits()[0]))
            throw new SemanticException(production.toString());

        //tip <- niz (const(char))
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("niz(const(char))"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
