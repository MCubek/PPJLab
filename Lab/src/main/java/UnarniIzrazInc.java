


public class UnarniIzrazInc implements Action {


    @Override
    public void checkProduction(SemanticProduction production, Scope scope) {
        //1. provjeri(<unarni_izraz>)
        SemanticProduction productionToCheck = new SemanticProduction(production.getRightStateNodes().get(1));
        RuleFactory ruleFactory = RuleFactory.getRuleFactory();
        Action action = ruleFactory.getRuleMap().get(productionToCheck);
        action.checkProduction(productionToCheck, scope);
        NonTerminalSymbol expression = (NonTerminalSymbol) production.getRightStates().get(1);

        //2. <unarni_izraz>.l-izraz = 1 i <unarni_izraz>.tip ?= int
        String lExpression = expression.getAttributeMap().get("lExpression").getAttribute().toString();
        String type = expression.getAttributeMap().get("type").getAttribute().toString();
        if (! lExpression.equals("true") && ! RuleFactory.implicitCast(type, "int")) {
            throw new SemanticException(production.toString());
        }

        //tip <- int
        //l-izraz <- 0
        production.getLeftState().addAttribute("type", new SimpleAttribute("int"));
        production.getLeftState().addAttribute("lExpression", new SimpleAttribute("false"));

        GeneratorKoda.codeBuilder.addCommand("POP R1");
        GeneratorKoda.codeBuilder.addCommand("LOAD R0, (R1)");

        if ("OP_INC".equals(production.getRightStates().get(0).getSymbolName())) {
            GeneratorKoda.codeBuilder.addCommand("ADD R0, 1, R0");
        } else {
            GeneratorKoda.codeBuilder.addCommand("SUB R0, 1, R0");
        }

        GeneratorKoda.codeBuilder.addCommand("STORE R0, (R1)");
        GeneratorKoda.codeBuilder.addCommand("PUSH R0");
    }
}
