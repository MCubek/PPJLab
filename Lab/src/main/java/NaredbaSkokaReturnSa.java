


public class NaredbaSkokaReturnSa implements Action {

    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);
        String izrazType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. naredba se nalazi unutar funkcije tipa funkcija(params -> pov) i vrijedi
        //<izraz>.tip ?= pov

        String commandType = production.getLeftStateNode().returnFunctionType();
        if (commandType == null || ! commandType.contains("funkcija"))
            throw new SemanticException(production.toString());
        String trimmedType = commandType.substring(commandType.indexOf(">") + 1, commandType.length() - 1).trim();
        if (! RuleFactory.implicitCast(izrazType, trimmedType))
            throw new SemanticException(production.toString());

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        GeneratorKoda.codeBuilder.addCommand("MOVE R6, R7");
        GeneratorKoda.codeBuilder.addCommand("MOVE R0, R6");
        if (((NonTerminalSymbol) production.getRightStates().get(1)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            GeneratorKoda.codeBuilder.addCommand("LOAD R6, (R6)");
        GeneratorKoda.codeBuilder.addCommand("RET");
    }
}
