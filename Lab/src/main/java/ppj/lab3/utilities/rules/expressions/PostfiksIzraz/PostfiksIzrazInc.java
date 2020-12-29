package ppj.lab3.utilities.rules.expressions.PostfiksIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class PostfiksIzrazInc implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<postfiks_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        //pozovi funkciju iz mape
        RuleFactory ruleFactory= new RuleFactory();
        Action action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //2. <postfiks_izraz>.l-izraz = 1 i <postfiks_izraz>.tip ∼ int
        String lExpression = expression.getAttributeMap().get("lExpression").getAttribute().toString();
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        if(!lExpression.equals("true") && !implicitCast(type,"int")) {
            System.out.println(production);
            System.exit(1);
        }

        //tip ← int
        //l-izraz ← 0
        production.getLeftState().addAttribute("type",new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
