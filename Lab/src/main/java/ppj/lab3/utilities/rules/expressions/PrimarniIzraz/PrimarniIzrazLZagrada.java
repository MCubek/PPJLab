package ppj.lab3.utilities.rules.expressions.PrimarniIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class PrimarniIzrazLZagrada implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);

        //tip <- <izraz>.tip
        //l-izraz <- <izraz>.l-izraz
        SimpleAttribute attribute = (SimpleAttribute) expression.getAttributeMap().get("type");
        production.getLeftState().addAttribute("type", new SimpleAttribute(attribute.getAttribute().toString()));
        attribute = (SimpleAttribute) expression.getAttributeMap().get("lExpression");
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute(attribute.getAttribute().toString()));
    }
}
