


public class MultiplikativniIzrazBez implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<cast_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);

        //tip <- <cast_izraz>.tip
        //l-izraz <- <cast_izraz>.l-izraz
        production.getLeftState().addAttribute("type", new SimpleAttribute(expression.getAttributeMap().get("type").getAttribute().toString()));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute(expression.getAttributeMap().get("lExpression").getAttribute().toString()));
    }
}
