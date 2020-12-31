package ppj.lab3.utilities.rules.expressions.ImeTipa;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class ImeTipaConst implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<specifikator_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <specifikator_tipa>.tip 6 != void
        if(type.equals("void")) {
            throw new SemanticException(production.toString());
        }

        //tip ← const(<specifikator_tipa>.tip)
        type = "const(" + type + ")";
        production.getLeftState().addAttribute("type",new SimpleAttribute(type));
    }
}
