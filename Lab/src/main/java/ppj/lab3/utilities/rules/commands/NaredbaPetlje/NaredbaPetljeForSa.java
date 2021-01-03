package ppj.lab3.utilities.rules.commands.NaredbaPetlje;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

public class NaredbaPetljeForSa implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz_naredba>1)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        //2. provjeri(<izraz_naredba>2)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(3));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol symbol = (NonTerminalSymbol) production.getRightStates().get(3);
        String type = symbol.getAttributeMap().get("type").getAttribute().toString();

        //3. <izraz_naredba>2.tip ?= int
        if (! RuleFactory.implicitCast(type, "int")) {
            throw new SemanticException(production.toString());
        }

        //4. provjeri(<izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(4));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        //5. provjeri(<naredba>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(6));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
