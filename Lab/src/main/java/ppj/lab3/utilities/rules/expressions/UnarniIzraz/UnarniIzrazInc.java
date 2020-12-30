package ppj.lab3.utilities.rules.expressions.UnarniIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class UnarniIzrazInc implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<unarni_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);

        //2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip ∼ int
        String lExpression = expression.getAttributeMap().get("lExpression").getAttribute().toString();
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        if(!lExpression.equals("true") && !implicitCast(type,"int")) {
            throw new SemanticException(production.toString());
        }

        //tip ← int
        //l-izraz ← 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
