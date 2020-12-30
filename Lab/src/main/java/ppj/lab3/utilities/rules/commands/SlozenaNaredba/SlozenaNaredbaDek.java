package ppj.lab3.utilities.rules.commands.SlozenaNaredba;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;

public class SlozenaNaredbaDek implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //slozena naredba stvara vlastiti djelokrug
        Scope newScope = new Scope(scope);

        //1. provjeri(<lista_deklaracija>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory= new RuleFactory();
        Action action= (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,newScope);

        //2. provjeri(<lista_naredbi>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, newScope);
    }
}
