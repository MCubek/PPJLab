package ppj.lab3.utilities.rules.commands.PrijevodnaJedinica;

import ppj.lab3.SemanticException;
import ppj.lab3.utilities.SemanticProduction;
import ppj.lab3.utilities.rules.Action;
import ppj.lab3.utilities.rules.RuleFactory;
import ppj.lab3.utilities.scope.Scope;
import ppj.lab3.utilities.scope.ScopeElement;

public class PrijevodnaJedinicaVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<prijevodna_jedinica>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);

        //2. provjeri(<vanjska_deklaracija>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);

        //konacna provjera
        if(production.getLeftStateNode().getParent() == null) {
            //1. u programu postoji funkcija imena main i tipa funkcija(void → int)
            ScopeElement main = scope.isDeclared("main");
            if (main == null || !main.getType().equals("funkcija(void -> int)"))
                throw new SemanticException("main");

            //2. svaka funkcija koja je deklarirana bilo gdje u programu (u bilo kojem djelokrugu)
            //mora biti definirana
            if(!scope.checkAllDefined())
                throw new SemanticException("funkcija");
        }
    }
}
