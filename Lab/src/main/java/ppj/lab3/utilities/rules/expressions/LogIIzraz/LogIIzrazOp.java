package ppj.lab3.utilities.rules.expressions.LogIIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class LogIIzrazOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<log_i_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String multiType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <log_i_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(multiType, "int")) {
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<bin_ili_izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String castType = expression.getAttributeMap().get("type").getAttribute().toString();

        //4. <bin_ili_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(castType, "int")) {
            throw new SemanticException(production.toString());
        }

        //tip <- int
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
