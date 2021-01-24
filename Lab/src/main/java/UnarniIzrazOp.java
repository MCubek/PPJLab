


public class UnarniIzrazOp implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<cast_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory= RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);

        //2. <cast_izraz>.tip ?= int
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        if (! RuleFactory.implicitCast(type, "int")) {
            throw new SemanticException(production.toString());
        }

        //tip <- int
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

        GeneratorKoda.codeBuilder.addCommand("POP R0");
        if (((NonTerminalSymbol) production.getRightStates().get(1)).getAttributeMap().get("lExpression").getAttribute().equals("true"))
            GeneratorKoda.codeBuilder.addCommand("LOAD R0, (R0)");

        String operator = production.getRightStateNodes().get(0).getChildren().get(0).getValue().getSymbolName();

        if (operator.equals("MINUS")) {
            GeneratorKoda.codeBuilder.addCommand("MOVE 0, R1");
            GeneratorKoda.codeBuilder.addCommand("SUB R1, R0, R0");
        }

        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
    }
}
