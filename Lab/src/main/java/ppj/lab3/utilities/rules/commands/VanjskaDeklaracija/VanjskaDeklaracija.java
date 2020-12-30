package ppj.lab3.utilities.rules.commands.VanjskaDeklaracija;

import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;

public class VanjskaDeklaracija implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //Nezavrsni znak <vanjska_deklaracija> generira ili definiciju funkcije (znak <definicija_funkcije>)
        //ili deklaraciju varijable ili funkcije (znak <deklaracija>). Obje produkcije su jedinicne
        //i u obje se provjeravaju pravila u podstablu kojem je znak s desne strane korijen
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = (Action) ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
