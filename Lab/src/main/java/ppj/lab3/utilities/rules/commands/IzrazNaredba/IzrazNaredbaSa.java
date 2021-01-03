package ppj.lab3.utilities.rules.commands.IzrazNaredba;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class IzrazNaredbaSa implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol symbol = (NonTerminalSymbol) production.getRightStates().get(0);

        //tip <- <izraz>.tip
        production.getLeftState().addAttribute("type", new SimpleAttribute(symbol.getAttributeMap().get("type").getAttribute().toString()));
    }
}
