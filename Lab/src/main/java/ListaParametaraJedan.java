


public class ListaParametaraJedan implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<deklaracija_parametra>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        String name = expression.getAttributeMap().get("name").getAttribute().toString();

        //tipovi <- [ <deklaracija_parametra>.tip ]
        //imena <- [ <deklaracija_parametra>.ime ]
        production.getLeftState().addAttribute("names", new ListAttribute(name));
        production.getLeftState().addAttribute("types", new ListAttribute(type));
    }
}
