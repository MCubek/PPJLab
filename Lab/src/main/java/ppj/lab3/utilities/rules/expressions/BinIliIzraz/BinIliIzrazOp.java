package ppj.lab3.utilities.rules.expressions.BinIliIzraz;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class BinIliIzrazOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<bin_ili_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= new RuleFactory();
        Action action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String multiType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <bin_ili_izraz>.tip ∼ int
        if(!implicitCast(multiType,"int")) {
            System.out.println(production);
            System.exit(1);
        }

        //3. provjeri(<bin_xili_izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String castType = expression.getAttributeMap().get("type").getAttribute().toString();

        //4. <bin_xili_izraz>.tip ∼ int
        if(!implicitCast(castType,"int")) {
            System.out.println(production);
            System.exit(1);
        }

        //tip ← int
        //l-izraz ← 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
