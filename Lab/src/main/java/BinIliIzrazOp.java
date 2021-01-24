


public class BinIliIzrazOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<bin_ili_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(0));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(0);
        String multiType = expression.getAttributeMap().get("type").getAttribute().toString();

        //2. <bin_ili_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(multiType, "int")) {
            throw new SemanticException(production.toString());
        }

        //3. provjeri(<bin_xili_izraz>)
        productionToCheck = new SemanticProduction(production.getRightStateNodes().get(2));
        action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        expression = (NonTerminalSymbol) production.getRightStates().get(2);
        String castType = expression.getAttributeMap().get("type").getAttribute().toString();

        //4. <bin_xili_izraz>.tip ?= int
        if (! RuleFactory.implicitCast(castType, "int")) {
            throw new SemanticException(production.toString());
        }

        //tip <- int
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        if (((NonTerminalSymbol) production.getRightStates().get(2)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            GeneratorKoda.codeBuilder.addCommand("LOAD R0, (R0)");

        GeneratorKoda.codeBuilder.addCommand("POP R1");
        if (((NonTerminalSymbol) production.getRightStates().get(0)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            GeneratorKoda.codeBuilder.addCommand("LOAD R1, (R1)");

        GeneratorKoda.codeBuilder.addCommand("OR R0, R1, R0");
        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
    }
}
