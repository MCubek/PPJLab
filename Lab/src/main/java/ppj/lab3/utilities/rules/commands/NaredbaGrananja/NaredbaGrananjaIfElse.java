package ppj.lab3.utilities.rules.commands.NaredbaGrananja;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class NaredbaGrananjaIfElse implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        RuleFactory ruleFactory= new RuleFactory();
        Action action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol symbol = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = symbol.getAttributeMap().get("type").getAttribute().toString();

        //2. <izraz>.tip ∼ int
        if(!implicitCast(type,"int")) {
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<naredba>1)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(4));
        action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);

        //4. provjeri(<naredba>2)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(6));
        action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
