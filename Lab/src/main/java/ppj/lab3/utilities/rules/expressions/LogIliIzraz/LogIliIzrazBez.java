package ppj.lab3.utilities.rules.expressions.LogIliIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class LogIliIzrazBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<log_i_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //tip ← <log_i_izraz>.tip
        //l-izraz ← <log_i_izraz>.l-izraz
        production.getLeftState().addAttribute("type", new SimpleAttribute(expression.getAttributeMap().get("type").getAttribute().toString()));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute(expression.getAttributeMap().get("lExpression").getAttribute().toString()));
    }
}
