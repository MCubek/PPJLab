package ppj.lab3.utilities.rules.expressions.IzrazPridruzivanja;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class IzrazPridruzivanjaOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<postfiks_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String lExpression = expression.getAttributeMap().get("lExpression").getAttribute().toString();
        String postfiksType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <postfiks_izraz>.l-izraz = 1
        if(!lExpression.equals("true")) {
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<izraz_pridruzivanja>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String pridruzivanjeType = expression.getAttributeMap().get("type").getAttribute().toString();

        //4. <izraz_pridruzivanja>.tip ?= <postfiks_izraz>.tip
        if (! RuleFactory.implicitCast(pridruzivanjeType, postfiksType)) {
            throw new SemanticException(production.toString());
        }

        //tip <- <postfiks_izraz>.tip
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute(postfiksType));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
