


public class NaredbaPetljeWhile implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {

        String start = GeneratorKoda.calculateNextLabel();
        String end = GeneratorKoda.calculateNextLabel();

        GeneratorKoda.codeBuilder.addCommandWithLabel(start, "");

        //1. provjeri(<izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol symbol = (NonTerminalSymbol) production.getRightStates().get(2);
        String type = symbol.getAttributeMap().get("type").getAttribute().toString();

        //2. <izraz>.tip ?= int
        if (! RuleFactory.implicitCast(type, "int")) {
            throw new SemanticException(production.toString());
        }

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        GeneratorKoda.codeBuilder.addCommand("CMP R0, 0");
        GeneratorKoda.codeBuilder.addCommand("JP_EQ " + end);

        //3. provjeri(<naredba>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(4));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);

        GeneratorKoda.codeBuilder.addCommand("JP " + start);

        GeneratorKoda.codeBuilder.addCommandWithLabel(end, "");
    }
}
