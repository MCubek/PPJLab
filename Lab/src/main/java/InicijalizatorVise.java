


public class InicijalizatorVise implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<lista_izraza_pridruzivanja>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);

        //br-elem <- <lista_izraza_pridruzivanja>.br-elem
        //tipovi <- <lista_izraza_pridruzivanja>.tipovi
        production.getLeftState().addAttribute("numElem", new SimpleAttribute(expression.getAttributeMap().get("numElem").getAttribute().toString()));
        production.getLeftState().addAttribute("types", new ListAttribute((String[]) expression.getAttributeMap().get("types").getAttribute()));
    }
}
