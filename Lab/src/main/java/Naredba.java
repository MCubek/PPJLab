


public class Naredba implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //Nezavrsni znak <naredba> generira blokove (<slozena_naredba>) i razlicite vrste jednostavnih naredbi (<izraz_naredba>, <naredba_grananja>, <naredba_petlje> i <naredba_skoka>).
        //Kako su sve produkcije jedinicne (s desne strane imaju jedan nezavrsni znak) i u svim produkcijama se provjeravaju semanticka pravila na znaku s desne strane, produkcije ovdje
        //nisu prikazane.

        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
    }
}
