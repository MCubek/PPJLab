package ppj.lab3.utilities.rules.definitions.DeklaracijaParametra;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;
import ppj.lab3.utilities.symbols.TerminalSymbol;

public class DeklaracijaParametraJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        TerminalSymbol idn = (TerminalSymbol) production.getRightStates().get(1);
        String idnName = idn.getLexicalUnits()[0];
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <ime_tipa>.tip != void
        if (type.equals("void"))
            throw new SemanticException(production.toString());

        //tip <- <ime_tipa>.tip
        //ime <- IDN.ime
        production.getLeftState().addAttribute("type", new SimpleAttribute(type));
        production.getLeftState().addAttribute("name", new SimpleAttribute(idnName));
    }
}
