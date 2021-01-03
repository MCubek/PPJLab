package ppj.lab3.utilities.rules.expressions.PostfiksIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class PostfiksIzrazFunction implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<postfiks_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        //pozovi funkciju iz mape
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //2. <postfiks_izraz>.tip = funkcija(void → pov)
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        if(!type.contains("funkcija(void")) {
            throw new SemanticException(production.toString());
        }

        //tip ← pov
        //l-izraz ← 0
        String pov = type.replace("funkcija(void -> ","");
        pov = pov.substring(0,pov.length()-1);
        production.getLeftState().addAttribute("type", new SimpleAttribute(pov));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

    }
}
