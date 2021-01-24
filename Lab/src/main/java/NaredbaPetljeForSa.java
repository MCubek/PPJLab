


public class NaredbaPetljeForSa implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<izraz_naredba>1)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        String start = GeneratorKoda.calculateNextLabel();
        String end = GeneratorKoda.calculateNextLabel();

        GeneratorKoda.codeBuilder.addCommandWithLabel(start, "");

        //2. provjeri(<izraz_naredba>2)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(3));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol symbol = (NonTerminalSymbol) production.getRightStates().get(3);
        String type = symbol.getAttributeMap().get("type").getAttribute().toString();

        //3. <izraz_naredba>2.tip ?= int
        if (! RuleFactory.implicitCast(type, "int")) {
            throw new SemanticException(production.toString());
        }

        //4. provjeri(<izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(4));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        GeneratorKoda.codeBuilder.addCommand("CMP R0, 0");
        GeneratorKoda.codeBuilder.addCommand("JP_EQ " + end);

        //5. provjeri(<naredba>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(6));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        GeneratorKoda.codeBuilder.addCommand("JP " + start);

        GeneratorKoda.codeBuilder.addCommandWithLabel(end, "");
    }
}
