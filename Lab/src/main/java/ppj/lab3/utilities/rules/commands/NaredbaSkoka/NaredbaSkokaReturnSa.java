package ppj.lab3.utilities.rules.commands.NaredbaSkoka;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.symbols.NonTerminalSymbol;

import static ppj.lab3.utilities.rules.RuleFactory.implicitCast;

public class NaredbaSkokaReturnSa implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String izrazType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. naredba se nalazi unutar funkcije tipa funkcija(params → pov) i vrijedi
        //<izraz>.tip ∼ pov

        String commandType = production.getLeftStateNode().returnFunctionType();
        if(commandType == null || !commandType.contains("funkcija"))
            throw new SemanticException(production.toString());
        String trimmedType = commandType.substring(commandType.indexOf(">") + 1, commandType.length()-1).trim();
        if(!implicitCast(izrazType,commandType))
            throw new SemanticException(production.toString());
    }
}
