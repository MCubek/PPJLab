package ppj.lab3.utilities.rules.expressions.CastIzraz;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.attributes.SimpleAttribute;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class CastIzrazSa implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<ime_tipa>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);
        String imeTipa = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. provjeri(<cast_izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(3));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(3);
        String castIzraz = expression.getAttributeMap().get("type").getAttribute().toString();

        //3. <cast_izraz>.tip se moze pretvoriti u <ime_tipa>.tip po poglavlju 4.3.1
        if (! RuleFactory.explicitCast(castIzraz, imeTipa)) {
            throw new SemanticException(production.toString());
        }

        //tip <- <ime_tipa>.tip
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute(imeTipa));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));
    }
}
