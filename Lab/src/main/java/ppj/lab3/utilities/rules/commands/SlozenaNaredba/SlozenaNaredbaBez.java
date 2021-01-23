package ppj.lab3.utilities.rules.commands.SlozenaNaredba;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;

public class SlozenaNaredbaBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //slozena naredba stvara vlastiti djelokrug
        Scope newScope = new Scope(scope);

        //provjeri parametre u slucaju funkcije
        if(production.getLeftState().getAttributeMap().containsKey("listaParamNames")) {
            String[] names = (String[]) production.getLeftState().getAttributeMap().get("listaParamNames").getAttribute();
            String[] types = (String[]) production.getLeftState().getAttributeMap().get("listaParamTypes").getAttribute();
            int size = types.length;
            for(int i = 0; i < names.length; i++) {

                newScope.addScopeElement(new ScopeElement(names[i], types[i], true, true, 4*(size - i + 1)));
            }
        }

        //1. provjeri(<lista_naredbi>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,newScope);
    }
}
