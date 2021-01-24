


public class ListaArgumenataJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz_pridruzivanja>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action= ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck,scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //tipovi <- [ <izraz_pridruzivanja>.tip ]
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        production.getLeftState().addAttribute("types", new ListAttribute(type));
    }
}
