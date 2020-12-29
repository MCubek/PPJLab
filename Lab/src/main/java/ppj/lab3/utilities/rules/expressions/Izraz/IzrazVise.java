package ppj.lab3.utilities.rules.expressions.Izraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class IzrazVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= new RuleFactory();
        Action action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);

        //2. provjeri(<izraz_pridruzivanja>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //tip ← <izraz_pridruzivanja>.tip
        //l-izraz ← 0
        production.getLeftState().addAttribute("type", new SimpleAttribute(type));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
