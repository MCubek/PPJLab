


public class PrijevodnaJedinicaJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<vanjska_deklaracija>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);

        //konacna provjera
        if(production.getLeftStateNode().getParent() == null) {
            //1. u programu postoji funkcija imena main i tipa funkcija(void -> int)
            ScopeElement main = scope.isDeclared("main");
            if (main == null || ! main.getType().equals("funkcija(void -> int)"))
                throw new SemanticException("main");

            //2. svaka funkcija koja je deklarirana bilo gdje u programu (u bilo kojem djelokrugu)
            //mora biti definirana
            if(!scope.checkAllDefined())
                throw new SemanticException("funkcija");
        }
    }
}
